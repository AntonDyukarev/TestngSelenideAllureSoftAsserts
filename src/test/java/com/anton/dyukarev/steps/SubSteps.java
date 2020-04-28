package com.anton.dyukarev.steps;

import com.codeborne.selenide.Selenide;
import com.anton.dyukarev.config.assertion.SoftAssert;
import io.qameta.allure.Step;

public class SubSteps {

    @Step("Child step 01")
    public void errorStep_01() {
        try {
            Selenide.$x("asd").click();
        } catch (Throwable throwable) {
            SoftAssert.unexpectedError(throwable);
        }
    }

    @Step("Child step 04")
    public void errorStep_04() {
        try {
            Selenide.$x("asd").click();
        } catch (Throwable throwable) {
            SoftAssert.unexpectedError(throwable);
        }
    }

    @Step("Child step 05")
    public void errorStep_05() {
        try {
            Selenide.$x("asd").click();
        } catch (Throwable throwable) {
            SoftAssert.unexpectedError(throwable);
        }
    }

    @Step("Child step 06")
    public void errorStep_06() {
        try {
            Selenide.$x("asd").click();
        } catch (Throwable throwable) {
            SoftAssert.unexpectedError(throwable);
        }
    }

    @Step("Child step 07")
    public void errorStep_07() {
        try {
            Selenide.$x("asd").click();
        } catch (Throwable throwable) {
            SoftAssert.unexpectedError(throwable);
        }
    }

    @Step("Child step 02")
    public void failedStep_02() {
        SoftAssert.assertEquals("a", "b", "ERROR MESSAGE!");
    }

    @Step("Child step 04")
    public void failedStep_04() {
        SoftAssert.assertEquals("a", "b", "ERROR MESSAGE!");
    }

    @Step("Child step 05")
    public void failedStep_05() {
        SoftAssert.assertEquals("a", "b", "ERROR MESSAGE!");
    }

    @Step("Child step 03")
    public void passedStep_03() {

    }

    @Step("Child step 06")
    public void passedStep_06() {

    }

    @Step("Child step 07")
    public void passedStep_07() {

    }

    @Step("Child step 01")
    public void passedStep_01() {

    }

    @Step("Child step 02")
    public void passedStep_02() {

    }

    @Step("Child step 08")
    public void passedStep_08() {

    }

    @Step("Child step 09")
    public void passedStep_09() {

    }
}
