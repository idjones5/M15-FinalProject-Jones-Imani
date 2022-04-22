package com.company.Final.Project;

import com.company.Final.Project.coin.Coin;
import com.company.Final.Project.iss.IssResponse;
import com.company.Final.Project.weather.Weather;
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

    public static void menuOptionMessage() {
        System.out.println("Enter a menu selection or press 5 to exit.");
    }

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
        int userChoice;


            System.out.println("===========================================");
            System.out.println("Welcome. Please select from the menu below");
            System.out.println("===========================================");

            System.out.println("1 - Weather in a city");
            System.out.println("2 - Location of the International Space Station (ISS)");
            System.out.println("3 - Weather in the Location of the ISS");
            System.out.println("4 - Current Cryptocurrency Prices");
            System.out.println("5 - Exit");

            try {

                do {
                    String userInput = scan.nextLine();
                    userChoice = Integer.parseInt(userInput);

                if (menuChoicesSet.contains(userChoice)) {

                    switch ((userChoice)) {

                        case 1:
                            System.out.println(" Weather in a city");
                            System.out.println("===================");
                            System.out.println("What city would you like to find the weather for?");

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

                                    menuOptionMessage();

                                } catch (WebClientResponseException.NotFound notFound) {
                                    System.out.println("Could not locate the city. Please enter a valid city.");
                                    menuOptionMessage();
                                } catch (WebClientResponseException we) {
                                    catchException(we);
                                    System.out.println("Message " + we.getMessage());
                                    menuOptionMessage();
                                } catch (Exception exception) {
                                    System.out.println("An error has occurred. " + exception.getMessage());
                                    menuOptionMessage();
                                }

                            break;
                        case 2:
                            System.out.println("Location of ISS");
                            System.out.println("===================");

                            try {

                                String issLat;
                                String issLong;
                                String issCountry;

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

                                if (weatherAtLocation.getSys().getCountry() == null) {

                                    System.out.println("The Space Station is not currently in a country.");
                                } else  {

                                    issCountry = weatherAtLocation.getSys().getCountry();

                                    System.out.println("The International Space Station currently has a latitude of "
                                            + issLat + " and a longitude of " + issLong +
                                            " and is currently in the country of " + issCountry);
                                }

                                menuOptionMessage();
                            }
                            catch (WebClientResponseException we) {
                                catchException(we);
                            }
                            catch (Exception e) {
                                System.out.println("An error has occurred." + e.getMessage());
                            }

                            break;
                        case 3:
                            System.out.println("Weather in location of ISS");
                            System.out.println("===========================");

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

                                System.out.println("The International Space Station is mainly experiencing " + mainWeather + " and further, " + weatherDescription + "!");
                                menuOptionMessage();
                            }
                            catch (WebClientResponseException we) {
                                catchException(we);
                            }
                            catch (Exception exception) {
                                System.out.println("An error has occurred " + exception.getMessage());
                            }

                            break;
                        case 4:
                            System.out.println("Current crypto prices");
                            System.out.println("======================");
                            System.out.println("Enter the symbol of a cryptocurrency (ex: BTC or ETH)");

                            try {

                                userInput = scan.nextLine();
                                String userCryptoSymbol = userInput;

                                String cryptoSymbol;
                                String cryptoName;
                                Double cryptoCurrentPrice;

                                WebClient client = WebClient.create("https://rest.coinapi.io/v1/assets/"
                                        + userCryptoSymbol + "?apikey=A59F836C-9CE3-4105-9DFB-3D489691575B");

                                Mono<Coin[]> response = client
                                        .get()
                                        .retrieve()
                                        .bodyToMono(Coin[].class);

                                Coin[] coin = response.share().block();

                                cryptoSymbol = coin[0].getAsset_id();
                                cryptoName = coin[0].getName();
                                cryptoCurrentPrice = Double.valueOf(coin[0].getPrice_usd());

                                System.out.println(cryptoSymbol);
                                System.out.println("=============");
                                System.out.println("Name: " + cryptoName);
                                System.out.println("Symbol: " + cryptoName);
                                System.out.printf("The current price of " + cryptoName + " in USD is %,.2f\n", cryptoCurrentPrice);
                                System.out.println("=============");
                                System.out.println();
                                menuOptionMessage();
                            }
                            catch (WebClientResponseException we) {
                                catchException(we);
                            }
                            catch (Exception exception) {
                                System.out.println("An Error has occurred " + exception.getMessage());
                            }
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

                } while (userChoice !=5);
            }
            catch (NumberFormatException ne) {
                System.out.println("Please enter a valid number.");
            }
            catch (Exception exception) {
                System.out.println("An error has occurred.");
            }
    }
}
