package com.anton.dyukarev;

import com.anton.dyukarev.config.assertion.SoftAssert;
import com.anton.dyukarev.steps.Steps;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.open;

public class TestRunner {

    private Steps steps;

    @BeforeMethod(alwaysRun = true)
    public void startClass() {
        Configuration.reportsFolder = "target/selenide-results";
        steps = new Steps();
        open("https://google.ru");
    }

    @AfterMethod(alwaysRun = true)
    public void after() {
        SoftAssert.updateTestResult();
    }

    @Test(description = "Demo test with fail and error")
    @Severity(SeverityLevel.NORMAL)
    @Story("Demo tests")
    public void test_01() {
        steps.stepWithFailAndError();
        steps.passedStep_02();
        steps.passedStep_03();
        SoftAssert.assertAll();
    }

    @Test(description = "Demo test with fails")
    @Severity(SeverityLevel.NORMAL)
    @Story("Demo tests")
    public void test_02() {
        steps.stepWithFails();
        steps.passedStep_02();
        steps.passedStep_03();
        SoftAssert.assertAll();
    }

    @Test(description = "Demo test with errors")
    @Severity(SeverityLevel.NORMAL)
    @Story("Demo tests")
    public void test_03() {
        steps.stepWithErrors();
        steps.passedStep_02();
        steps.passedStep_03();
        SoftAssert.assertAll();
    }

    @Test(description = "Demo passed test")
    @Severity(SeverityLevel.NORMAL)
    @Story("Demo tests")
    public void test_04() {
        steps.passedStep_01();
        steps.passedStep_02();
        steps.passedStep_03();
        SoftAssert.assertAll();
    }
}
