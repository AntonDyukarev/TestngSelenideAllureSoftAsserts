package com.anton.dyukarev.config.assertion;

import com.anton.dyukarev.config.enums.Property;
import com.anton.dyukarev.config.PropertyManager;
import com.anton.dyukarev.gson.JsonConverter;
import com.anton.dyukarev.gson.test.result.TestResult;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Attachment;
import io.qameta.allure.model.Status;
import org.testng.Assert;

import java.io.*;
import java.util.*;

public class SoftAssert {

    /**
     * Текущий статус тест кейса.
     */
    private static Status status;

    /**
     * uuid текущего тест кейса. Необходим для получения json файла с результатами теста,
     * имя которого строится из двух частей: 1 - uuid тест кейса, 2 - "-result.json".
     */
    private static String uuidTestCase;

    /**
     * Мэпа для хранения статусов степов
     * key - uuid степа
     * value - status
     */
    private final static Map<String, String> STEP_STATUS_MAP;

    static {
        status = Status.PASSED;
        STEP_STATUS_MAP = new HashMap<>();
        uuidTestCase = "";
    }

    /**
     * Метод ассерта, который сравнивает два значения.
     * В случае их неравинства, для текущего степа будет записан статус FAILED.
     *
     * @param expected Object ожидаемое значние.
     * @param actual Object актуальное значение.
     * @param errorMessage String сообщение об ошибке.
     *                     Прикрепляется к степу в отчете в случае ошибки,
     *                     дополняясь ожидаемыми и актуальными значениями.
     */
    public static void assertEquals(Object expected, Object actual, String errorMessage) {
        try {
            Assert.assertEquals(actual, expected, errorMessage);
        } catch (Throwable exception) {
            updateStep(exception, Status.FAILED);
            failTestCase(Status.FAILED);
        }
    }

    /**
     * Метод обновляет текущий степ. Записывает uuid в descriprion и прикрепляет вложения
     * в виде скриншота и текста с описанием ошибки.
     *
     * @param throwable Throwable, данные об ошибке.
     * @param status Status, состояние степа, которое должно быть в отчете.
     */
    private static void updateStep(Throwable throwable, Status status) {
        Optional<String> step = Allure.getLifecycle().getCurrentTestCaseOrStep();
        if (step.isPresent()) {
            String uuid = step.get();
            Allure.getLifecycle().updateStep(uuid, item -> {
                /*
                * Сохранение uuid в description необходимо для того, чтобы текущий степ
                * можно было найти в json файле с общим результатом теста, так как в нем
                * отсутствуют uuid степов.
                */
                item.setDescription(uuid);
                item.setAttachments(getAttachments(getStackTrace(status, throwable.getMessage())));
            });
            STEP_STATUS_MAP.put(uuid, status.value());
        } else {
            throw new NoSuchElementException("Не удалось получить UUID степа для установки статуса FAILED");
        }
    }

    /**
     * Метод получает скриншот и текстовое описание ошибки
     * в виде списка из объектов класса Attachment, и возвращает его.
     *
     * @param errorMessage String, сообщение об ошибке.
     * @return List<Attachment>
     */
    private static List<Attachment> getAttachments(String errorMessage) {
        List<Attachment> list = new ArrayList<>();
        list.add(Attachments.getScreenshot());
        list.add(Attachments.getTextAttach(errorMessage));
        return list;
    }

    /**
     * Метод возвращает готовое сообщение об ошибке.
     *
     * @param status Status, статус степа. Необходим для формирования заголовка описания ошибки.
     * @param failDetails String, подробности произошедшей ошибки.
     * @return String
     */
    private static String getStackTrace(Status status, String failDetails) {
        return "#STEP " + status.value().toUpperCase() + ":" + "\n\n" + failDetails;
    }

    /**
     * Метод сохраняет uuid тест кейса и устанавливает isTestFailed - true.
     * Эти значения будут говорить о том, что тест кейс подлежит обновлению статусов
     * после завершения его выполнения.
     */
    private static void failTestCase(Status status) {
        Status actualStatus = SoftAssert.status;
        if (status.equals(Status.FAILED) && !actualStatus.equals(Status.FAILED)
                || status.equals(Status.BROKEN) && actualStatus.equals(Status.PASSED)
                || status.equals(Status.PASSED) && !actualStatus.equals(Status.PASSED)) {
            SoftAssert.status = status;
            saveUuidOfTestCase();
        }
    }

    /**
     * Метод сохраняет uuid тест кейса.
     */
    private static void saveUuidOfTestCase() {
        Optional<String> testCase = Allure.getLifecycle().getCurrentTestCase();
        testCase.ifPresent(uuid -> uuidTestCase = uuid);
    }

    /**
     * Данный метод необходимо вызывать в случае возникновения любого непредвиденного исключения
     * в процессе выполнения степа.
     * Такой степ будет отмечен в отчете статусом BROKEN
     *
     * @param throwable Throwable, причина ошибки.
     */
    public static void unexpectedError(Throwable throwable) {
        updateStep(throwable, Status.BROKEN);
        failTestCase(Status.BROKEN);
    }

    /**
     * Данный метод необходимо вызывать после полного завершения выполнения метода с тэгом @Test.
     * Он запускает проверку наличия найденных ошибок, и если они есть, обновляет статусы в json файле
     * с результатами тест кейса.
     */
    public static void updateTestResult() {
        if (!SoftAssert.status.equals(Status.PASSED)) {
            TestResult testResult = getTestResult();
            STEP_STATUS_MAP.forEach(testResult::updateStepStatus);
            writeTestResult(testResult);
            setDefaultParams();
        }
    }

    public static void assertAll() {
        if (SoftAssert.status.equals(Status.FAILED))
            throw new AssertionError("В процессе выполнения тест кейса некоторые проверки обнаружили ошибки!");
        else if (SoftAssert.status.equals(Status.BROKEN))
            throw new RuntimeException("В процессе выполнения тест кейса возникли непредвиденные ошибки!");
    }

    /**
     * Метод записывает структуру классов, описывающий json с результатом выполнения тест кейса,
     * в json файл.
     *
     * @param testResult TestResult, основной класс из структуры, описывающей json с результатами выполнения тест кейса.
     */
    private static void writeTestResult(TestResult testResult) {
        try {
            Writer writer = new FileWriter(getTestResultFile());
            new JsonConverter(TestResult.class).saveJson(testResult, writer);
        } catch (IOException e) {
            throw new AssertionError("Не удалось произвести запись json файла с результатом тест кейса", e);
        }
    }

    /**
     * Метод возвращает json файл с результатом выполнения тест кейса.
     *
     * @return File
     */
    private static File getTestResultFile() {
        String path = PropertyManager.getPropertyValue(Property.ALLURE_RESULTS_DIRECTORY)
                + "/" + uuidTestCase + "-result.json";
        return new File(path);
    }

    /**
     * Метод преобразовывает json в основной класс из структуры,
     * описывающей json файл с результатом выполнения теста.
     *
     * @return TestResult
     */
    private static TestResult getTestResult() {
        Reader reader;
        TestResult testResult;
        try {
            reader = new FileReader(getTestResultFile());
            testResult = new JsonConverter(TestResult.class).get(reader);
            reader.close();
        } catch (IOException e) {
            throw new AssertionError("Не удалось произвести чтение json файла с результатом тест кейса", e);
        }
        return testResult;
    }

    /**
     * Метод возвращает дефолтные значения свойствам класса.
     */
    private static void setDefaultParams() {
        status = Status.PASSED;
        STEP_STATUS_MAP.clear();
    }
}