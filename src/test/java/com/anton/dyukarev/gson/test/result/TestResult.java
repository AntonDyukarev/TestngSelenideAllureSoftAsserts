package com.anton.dyukarev.gson.test.result;

import java.util.ArrayList;
import java.util.List;

public class TestResult {

    private String uuid;

    private String historyId;

    private String testCaseId;

    private String rerunOf;

    private String fullName;

    private List<Label> labels;

    private List<Link> links;

    private String name;

    private String status;

    private StatusDetails statusDetails;

    private String stage;

    private String description;

    private String descriptionHtml;

    private final List<Step> steps;

    private List<Attachment> attachments;

    private List<Parameter> parameters;

    private long start;

    private long stop;

    public TestResult() {
        steps = new ArrayList<>();
    }

    public void updateStepStatus(String uuid, String status) {
        for (Step step : steps) {
            boolean isUpdate = step.updateStepStatus(uuid, status);
            if (isUpdate) {
                setStatus(status);
                break;
            }
        }
    }

    public void setStatus(String status) {
        if (status.equals(Status.FAILED.getName()) && !getStatus().equals(Status.FAILED.getName())
                || status.equals(Status.BROKEN.getName()) && getStatus().equals(Status.PASSED.getName())
                || status.equals(Status.PASSED.getName()) && !getStatus().equals(Status.PASSED.getName()))
            this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
