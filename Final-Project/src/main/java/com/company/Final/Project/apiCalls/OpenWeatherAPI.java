package com.company.Final.Project.apiCalls;

import com.company.Final.Project.caching.CachedWeather;
import com.company.Final.Project.weather.WeatherResponse;
import com.company.Final.Project.exceptions.WebExceptions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.Map;

public class OpenWeatherAPI {

    // method to call the open weather API
    // also controls the response of a call
    // returns the weather at a location (userInput from Scanner)
    // stores the data in the map provided for caching

    public static void openWeather(String userInput, Map<String, CachedWeather> map) {

       try {
           String userLocation = userInput;
           CachedWeather userWeather = new CachedWeather();

           WebClient client = WebClient.create("http://api.openweathermap.org/data/2.5/weather?q=" + userLocation + "&APPID=fd238fddcae5fb3172123f01221a835d"
           + "&units=imperial");

           Mono<WeatherResponse> weatherResponse = client
                   .get()
                   .retrieve()
                   .bodyToMono(WeatherResponse.class);

           WeatherResponse weatherAtLocation = weatherResponse.share().block();

           weatherAtLocation
                   .getWeather()
                   .stream()
                   .forEach(weather -> {
                       System.out.println("\n" + "Weather ");
                       System.out.println("===================");
                       System.out.println("Location: " + userLocation);
                       System.out.println("Main Weather: " + weather.getMain());
                       System.out.println("Detail: " + weather.getDescription());
                       System.out.println("Temperature: " + weatherAtLocation.getMain().getTemp() + " °F");
                       System.out.println("Feels like: " + weatherAtLocation.getMain().getFeels_like() + " °F");
                       System.out.println("===================");

                       userWeather.setLocation(userLocation);
                       userWeather.setMain(weather.getMain());
                       userWeather.setDescription(weather.getDescription());
                       userWeather.setTemperature(weatherAtLocation.getMain().getTemp());
                       userWeather.setFeelsLike(weatherAtLocation.getMain().getFeels_like());
                   });

           map.put(userLocation, userWeather);
       }
       catch (WebClientResponseException.NotFound notFound) {
           System.out.println("Could not locate the city. Please enter a valid city.");
       }
       catch (WebClientResponseException we) {
           WebExceptions.catchException(we);
       }
       catch (Exception exception) {
           System.out.println("An error occurred. " + exception.getMessage());
       }
    }
}
