package com.company.Final.Project.caching;

// response of the weather data that is going to be stored

public class CachedWeather {

    private String main;
    private String Location;
    private String description;
    private String temperature;
    private String feelsLike;


    // overriding the toString method to return a similar response to the OpenWeatherAPI call
    @Override
    public String toString() {
        return "\n" + "Weather " + "\n" + "===================" + "\nLocation: " + getLocation() + " \nMain weather: "
                + getMain() + " \nDetail: " + getDescription() + "\nTemperature: " + getTemperature() + " °F" + "\nFeels Like: "
                + getFeelsLike() + " °F" + "\n" + "===================" + "\n";
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return Location;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }
}
