package com.company.Final.Project.iss;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpaceResponse {

    @JsonProperty("iss_position")
    private IssPosition iss_position;
    private String timestamp;
    private String message;

    // method to describe a summary of the ISS response

    public static void issSummary() {
        System.out.println("Below is a summary of the latitude, longitude" +
                ", \ncountry, and city at the current location of the" +
                " \nInternational Space Station.");
    }

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
