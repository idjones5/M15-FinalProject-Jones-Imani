package com.company.Final.Project.iss;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssResponse {

    @JsonProperty("iss_position")
    private IssPosition iss_position;
    private String timestamp;


    public IssPosition getIssPosition() {
        return iss_position;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
