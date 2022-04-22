package com.company.Final.Project.apiCalls;

import com.company.Final.Project.weather.WeatherResponse;
import com.company.Final.Project.exceptions.WebExceptions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class OpenWeatherAPI {


    // method to call the open weather API


    public static void openWeather(String userInput) {

       try {
           String userLocation = userInput;

           WebClient client = WebClient.create("http://api.openweathermap.org/data/2.5/weather?q=" + userLocation + "&APPID=fd238fddcae5fb3172123f01221a835d");

           Mono<WeatherResponse> weatherResponse = client
                   .get()
                   .retrieve()
                   .bodyToMono(WeatherResponse.class);

           WeatherResponse weatherAtLocation = weatherResponse.share().block();

           weatherAtLocation
                   .getWeather()
                   .stream()
                   .forEach(weather -> {
                       System.out.println("The weather for " + userLocation + " right now looks like " + weather.getMain()
                               + " with " + weather.getDescription());
                   });


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
