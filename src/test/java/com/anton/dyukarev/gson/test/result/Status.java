package com.anton.dyukarev.gson.test.result;

public enum Status {

    FAILED,
    BROKEN,
    PASSED;

    public String getName() {
        return name().toLowerCase();
    }
}
