package com.company.Final.Project.apiCalls;

import com.company.Final.Project.exceptions.WebExceptions;
import com.company.Final.Project.iss.IssResponse;
import com.company.Final.Project.weather.WeatherResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class IssOpenWeatherAPIs {

    public static void issWeatherConditions() {

        try {
            String issLat;
            String issLong;
            String mainWeather;
            String weatherDescription;

            WebClient client = WebClient.create("http://api.open-notify.org/iss-now.json?callback");

            Mono<IssResponse> issResponse = client
                    .get()
                    .retrieve()
                    .bodyToMono(IssResponse.class);

            IssResponse iss = issResponse.share().block();

            issLat = iss.getIssPosition().getLatitude();
            issLong = iss.getIssPosition().getLongitude();

            WebClient client1 = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=" + issLat + "&" +
                    "lon=" + issLong + "&appid=fd238fddcae5fb3172123f01221a835d");

            Mono<WeatherResponse> weatherResponse = client1
                    .get()
                    .retrieve()
                    .bodyToMono(WeatherResponse.class);

            WeatherResponse weatherAtLocation = weatherResponse.share().block();

            mainWeather = weatherAtLocation.getWeather().get(0).getMain();
            weatherDescription = weatherAtLocation.getWeather().get(0).getDescription();

            System.out.println("The International Space Station is mainly experiencing " + mainWeather + " weather and further, " + weatherDescription + "!");
        }
        catch (WebClientResponseException we) {
            WebExceptions.catchException(we);
        }
        catch (Exception exception) {
            System.out.println("An error has occurred " + exception.getMessage());
        }
    }


}
