package com.company.Final.Project.apiCalls;

import com.company.Final.Project.exceptions.WebExceptions;
import com.company.Final.Project.fileWriter.ISSWeatherFile;
import com.company.Final.Project.iss.SpaceResponse;
import com.company.Final.Project.weather.WeatherResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;


public class IssOpenWeatherAPIs {

    // method to call the ISS and open weather APIs
    // also controls the response of the calls
    // returns the coordinates, the weather, country, and city if applicable

    public static void issWeatherConditions(HashMap<Integer, ISSWeatherFile> map) {

        try {
            String issLat;
            String issLong;
            String mainWeather;
            String weatherDescription;
            String issCountry;
            String issCity;

            ISSWeatherFile iw = new ISSWeatherFile();

            WebClient client = WebClient.create("http://api.open-notify.org/iss-now.json?callback");

            Mono<SpaceResponse> issResponse = client
                    .get()
                    .retrieve()
                    .bodyToMono(SpaceResponse.class);

            SpaceResponse iss = issResponse.share().block();

            issLat = iss.getIssPosition().getLatitude();
            issLong = iss.getIssPosition().getLongitude();

            WebClient client1 = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=" + issLat + "&" +
                    "lon=" + issLong + "&appid=fd238fddcae5fb3172123f01221a835d&units=imperial");

            Mono<WeatherResponse> weatherResponse = client1
                    .get()
                    .retrieve()
                    .bodyToMono(WeatherResponse.class);

            WeatherResponse weatherAtLocation = weatherResponse.share().block();

            mainWeather = weatherAtLocation.getWeather().get(0).getMain();
            weatherDescription = weatherAtLocation.getWeather().get(0).getDescription();

            if (weatherAtLocation.getSys().getCountry() == null) {

                SpaceResponse.issSummary();

                System.out.println("===========================");
                System.out.println("Latitude: " + issLat + "°");
                System.out.println("Longitude: " + issLong + "°");
                System.out.println("Country: The Space Station is not currently in a country.");
                System.out.println("City: The Space Station is not currently in a city.");
                System.out.println("\n" + "Weather");
                System.out.println("===========================");
                System.out.println("Main Weather: " + mainWeather);
                System.out.println("Detail: " + weatherDescription);
                System.out.println("Temperature: " + weatherAtLocation.getMain().getTemp());

                iw.setLatitude(issLat);
                iw.setLongitude(issLong);
                iw.setCountry("null");
                iw.setCity("null");
                iw.setMainWeather(mainWeather);
                iw.setDetail(weatherDescription);
                iw.setTemp(weatherAtLocation.getMain().getTemp());


                System.out.println("===========================");
            } else  {

                issCountry = weatherAtLocation.getSys().getCountry();
                issCity = weatherAtLocation.getName();

                SpaceResponse.issSummary();

                System.out.println("===========================");
                System.out.println("Latitude: " + issLat + "°");
                System.out.println("Longitude: " + issLong + "°");
                System.out.println("Country: " + issCountry);
                System.out.println("City: " + issCity);
                System.out.println("\n" + "Weather");
                System.out.println("===========================");
                System.out.println("Main Weather: " + mainWeather);
                System.out.println("Detail: " + weatherDescription);
                System.out.println("Temperature: " + weatherAtLocation.getMain().getTemp());
                System.out.println("===========================");

                iw.setLatitude(issLat);
                iw.setLongitude(issLong);
                iw.setCountry(issCountry);
                iw.setCity(issCity);
                iw.setMainWeather(mainWeather);
                iw.setDetail(weatherDescription);
                iw.setTemp(weatherAtLocation.getMain().getTemp());
            }
            map.put(1,iw);
        }
        catch (WebClientResponseException we) {
            WebExceptions.catchException(we);
        }
        catch (Exception exception) {
            System.out.println("An error has occurred " + exception.getMessage());
        }
    }


}
