package com.anton.dyukarev.steps;

import io.qameta.allure.*;

public class Steps {

    SubSteps subSteps;

    public Steps() {
        subSteps = new SubSteps();
    }

    @Step("Parent step 01")
    public void stepWithFailAndError() {
        subSteps.errorStep_01();
        subSteps.failedStep_02();
        subSteps.passedStep_03();
        subSteps.failedStep_04();
        subSteps.failedStep_05();
        subSteps.errorStep_06();
        subSteps.errorStep_07();
        subSteps.passedStep_08();
        subSteps.passedStep_09();

    }

    @Step("Parent step 01")
    public void passedStep_01() {
        subSteps.passedStep_01();
        subSteps.passedStep_02();
        subSteps.passedStep_03();
    }

    @Step("Parent step 02")
    public void passedStep_02() {
        subSteps.passedStep_01();
        subSteps.passedStep_02();
        subSteps.passedStep_03();
    }

    @Step("Parent step 03")
    public void passedStep_03() {
        subSteps.passedStep_01();
        subSteps.passedStep_02();
        subSteps.passedStep_03();
    }

    @Step("Parent step 01")
    public void stepWithFails() {
        subSteps.passedStep_01();
        subSteps.failedStep_02();
        subSteps.passedStep_03();
        subSteps.failedStep_04();
        subSteps.failedStep_05();
        subSteps.passedStep_06();
        subSteps.passedStep_07();
    }

    @Step("Parent step 01")
    public void stepWithErrors() {
        subSteps.errorStep_01();
        subSteps.passedStep_02();
        subSteps.passedStep_03();
        subSteps.errorStep_04();
        subSteps.errorStep_05();
        subSteps.passedStep_06();
        subSteps.passedStep_07();
    }
}
