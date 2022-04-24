package com.company.Final.Project.iss;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpaceResponse {

    @JsonProperty("iss_position")
    private IssPosition iss_position;
    private String timestamp;
    private String message;


    public IssPosition getIssPosition() {
        return iss_position;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
