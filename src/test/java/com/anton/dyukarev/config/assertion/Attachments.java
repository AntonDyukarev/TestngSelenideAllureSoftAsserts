package com.anton.dyukarev.config.assertion;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Attachments {

    private final static String SCREENSHOT_NAME = "Screenshot";

    private final static String SCREENSHOT_TYPE = "image/png";

    private final static String SCREENSHOT_EXTENSION = "png";

    private final static String TEXT_NAME = "Error message";

    private final static String TEXT_TYPE = "text/plain";

    private final static String TEXT_EXTENSION = "txt";

    /**
     * Метод возвращает готовое вложение со скриншотом текущей вкладки браузера
     *
     * @return Attachment
     */
    public static Attachment getScreenshot() {
        byte[] bytes = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return getAttachment(SCREENSHOT_NAME, SCREENSHOT_TYPE, SCREENSHOT_EXTENSION, inputStream);
    }

    /**
     * Метод возвращает готовое вложение с передаваемым текстом
     *
     * @param text String, текст, из которого будет сформировано вложение
     * @return Attachment
     */
    public static Attachment getTextAttach(String text) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes());
        return getAttachment(TEXT_NAME, TEXT_TYPE, TEXT_EXTENSION, inputStream);
    }

    /**
     * Метод возвращает вложение исходя из передаваемых параметров.
     *
     * @param name String, наименование вложение. Оно будет отображаться в отчете, в виде заголовка вложения.
     * @param type String, тип вложения. Так же отображается в отчете в виде всплывающей подсказки, если навести
     *             указатель на значек вложения.
     *             Например: "image/png" или "text/plain"
     * @param extension String, расширение файла, в который будет сохранено вложение.
     * @param inputStream InputStream, с данными вложения.
     * @return Attachment
     */
    private static Attachment getAttachment(String name, String type, String extension, InputStream inputStream) {
        /*
         * Чтобы добавить вложение в степ отчета аллюра, необходимо сохранить его в папке алюр-резалтс.
         * Для этого необходимо получить наименование файла с помощью метода prepareAttachment,
         * а затем записать файл в папку алюр резалтс методом writeAttachment
         */
        String source = Allure.getLifecycle().prepareAttachment(name, type, extension);
        Allure.getLifecycle().writeAttachment(source, inputStream);

        return new Attachment()
                .setName(name)
                .setType(type)
                .setSource(source);
    }
}
