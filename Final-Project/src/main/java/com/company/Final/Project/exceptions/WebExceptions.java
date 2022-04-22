package com.company.Final.Project.exceptions;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class WebExceptions {

    // method to handle the webclient exceptions

    public static void catchException(WebClientResponseException exception) {

        int statusCode = exception.getRawStatusCode();

        if (statusCode >= 400 && statusCode <500) {
            System.out.println("A Client Error has occurred");
        }
        else if (statusCode >= 500 && statusCode <600){
            System.out.println("A Server Error has occurred");
        }
    }

}
