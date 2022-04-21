package com.company.Final.Project;

import com.company.Final.Project.weather.WeatherResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static void catchException(WebClientResponseException exception) {

        // function to handle the webclient exceptions

        int statusCode = exception.getRawStatusCode();

        if (statusCode >= 400 && statusCode <500) {
            System.out.println("A Client Error has occurred");
        }
        else if (statusCode >= 500 && statusCode <600){
            System.out.println("A Server Error has occurred");
        }
    }

    public static void main(String[] args) {

        Set<Integer> menuChoicesSet = new HashSet<Integer>(Arrays.asList(1,2,3,4,5));
        Scanner scan = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("Welcome. Please select from the menu below");
        System.out.println("===========================================");

        System.out.println("1 - Weather in a city");
        System.out.println("2 - Location of the International Space Station (ISS)");
        System.out.println("3 - Weather in the Location of the ISS");
        System.out.println("4 - Current Cryptocurrency Prices");
        System.out.println("5 - Exit");

        try {

            String userInput = scan.nextLine();
            int userChoice = Integer.parseInt(userInput);

            if (menuChoicesSet.contains(userChoice)) {

                switch ((userChoice)) {

                    case 1:
                        System.out.println(" Weather in a city");
                        System.out.println("===================");
                        System.out.println("What city would you like to find the weather for?");

                        while(userChoice == 1) {
                            try {

                                userInput = scan.nextLine();
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

                                System.out.println("Press 1 to enter another city. Press 5 to exit.");
                                userInput = scan.nextLine();
                                userChoice = Integer.parseInt(userInput);
                            } catch (WebClientResponseException.NotFound notFound) {
                                System.out.println("Could not locate the city. Please enter a valid city.");
                            }
                            catch (WebClientResponseException we) {

                                catchException(we);
                                System.out.println("Message " + we.getMessage());

                            }
                            catch (Exception exception) {
                                System.out.println("An error has occured. " + exception.getMessage());
                            }

                        }
                    case 2:
                        System.out.println("location of ISS");
                        break;
                    case 3:
                        System.out.println("Weather in location of ISS");
                        break;
                    case 4:
                        System.out.println("Current crypto prices");
                        break;
                    case 5:
                        System.out.println("Goodbye.");
                        break;
                    default:
                        System.out.println("Please enter a valid option.");
                        break;

                }


            } else {
                System.out.println("Error. Please enter a valid menu option.");
            }
        } catch (NumberFormatException ne) {
            System.out.println("Please enter a valid number.");
        }

    }
}
