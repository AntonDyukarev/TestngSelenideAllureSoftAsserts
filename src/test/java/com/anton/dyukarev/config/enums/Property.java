package com.anton.dyukarev.config.enums;

public enum Property {

    ALLURE_RESULTS_DIRECTORY;

    public String getName() {
        return name().toLowerCase().replaceAll("_", ".");
    }
}
