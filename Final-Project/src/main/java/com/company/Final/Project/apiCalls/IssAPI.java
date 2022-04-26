package com.company.Final.Project.apiCalls;

import com.company.Final.Project.exceptions.WebExceptions;
import com.company.Final.Project.iss.SpaceResponse;
import com.company.Final.Project.weather.WeatherResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class IssAPI {

    // method to call the ISS API
    // also controls the response of a call
    // returns the coordinates, country, and city if applicable

    public static void issAPI() {

        try {

            String issLat;
            String issLong;
            String issCountry;
            String issCity;

            WebClient client = WebClient.create("http://api.open-notify.org/iss-now.json?callback");

            Mono<SpaceResponse> issResponse = client
                    .get()
                    .retrieve()
                    .bodyToMono(SpaceResponse.class);
            SpaceResponse iss = issResponse.share().block();

            issLat = iss.getIssPosition().getLatitude();
            issLong = iss.getIssPosition().getLongitude();

            WebClient client1 = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=" + issLat + "&" +
                    "lon=" + issLong + "&appid=fd238fddcae5fb3172123f01221a835d");

            Mono<WeatherResponse> weatherResponse = client1
                    .get()
                    .retrieve()
                    .bodyToMono(WeatherResponse.class);
            WeatherResponse weatherAtLocation = weatherResponse.share().block();

            if (weatherAtLocation.getSys().getCountry() == null) {

                System.out.println("Below is a summary of the latitude, longitude" +
                        ", and country at the current location of the" +
                        " International Space Station.");

                System.out.println("===================");
                System.out.println("Latitude: " + issLat + "°");
                System.out.println("Longitude: " + issLong + "°");
                System.out.println("Country: The Space Station is not currently in a country.");
                System.out.println("===================");
            } else  {

                issCountry = weatherAtLocation.getSys().getCountry();
                issCity = weatherAtLocation.getName();

                System.out.println("Below is a summary of the latitude, longitude" +
                        ", country, and city at the current location of the" +
                        " International Space Station.");

                System.out.println("===================");
                System.out.println("Latitude: " + issLat + "°");
                System.out.println("Longitude: " + issLong + "°");
                System.out.println("Country: " + issCountry);
                System.out.println("City: " + issCity);
                System.out.println("===================");
            }
        }
        catch (WebClientResponseException we) {
            WebExceptions.catchException(we);
        }
        catch (Exception e) {
            System.out.println("An error has occurred." + e.getMessage());
        }
    }

}
