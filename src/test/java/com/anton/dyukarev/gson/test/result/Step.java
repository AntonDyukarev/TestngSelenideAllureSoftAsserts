package com.anton.dyukarev.gson.test.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Step {

    private String name;

    private String status;

    private StatusDetails statusDetails;

    private String stage;

    private String description;

    private String descriptionHtml;

    private final List<Step> steps;

    private List<Attachment> attachments;

    private List<Parameter> parameters;

    private Long start;

    private Long stop;

    public Step() {
        steps = new ArrayList<>();
    }

    public boolean updateStepStatus(String uuid, String status) {
        boolean isUpdate = false;
        if (Objects.nonNull(description) && description.equals(uuid)) {
            isUpdate = true;
            setStatus(status);
        }
        if (!isUpdate) {
            for (Step step : steps) {
                isUpdate = step.updateStepStatus(uuid, status);
                if (isUpdate) {
                    setStatus(status);
                    break;
                }
            }
        }
        return isUpdate;
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

    public String getDescription() {
        return description;
    }
}
