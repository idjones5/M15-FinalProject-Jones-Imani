package com.company.Final.Project;

import java.sql.SQLOutput;
import java.util.Scanner;

import com.company.Final.Project.coin.CoinResponse;
import com.company.Final.Project.iss.IssResponse;
import com.company.Final.Project.weather.Weather;
import com.company.Final.Project.weather.WeatherResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);

		Scanner scan = new Scanner(System.in); // take user input from the scanner to search for the location etc
		String userInput = scan.nextLine();

		String userLocation = userInput; // location needs to be in the format London,uk

		//WebClient client = WebClient.create("http://api.openweathermap.org/data/2.5/weather?q=" + userLocation + "&APPID=fd238fddcae5fb3172123f01221a835d"); // city location
		WebClient client1 = WebClient.create("https://rest.coinapi.io/v1/assets/BTC?apikey=A59F836C-9CE3-4105-9DFB-3D489691575B"); // coin price - logged at BTC rn
		WebClient client2 = WebClient.create("http://api.open-notify.org/iss-now.json?callback"); // iss location

		WebClient client3 = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=22.0554&lon=-28.8485&appid=fd238fddcae5fb3172123f01221a835d"); // lat and long


		try {

			WebClient client = WebClient.create("http://api.openweathermap.org/data/2.5/weather?q=" + userLocation + "&APPID=fd238fddcae5fb3172123f01221a835d");

			Mono<WeatherResponse> weatherResponse = client
					.get()
					.retrieve()
					.bodyToMono(WeatherResponse.class);
			WeatherResponse json = weatherResponse.share().block();

			json
					.getWeather()
					.stream()
					.forEach(weather -> {
						System.out.println("The weather for " + userLocation + " right now looks like " + weather.getMain()
						+ " with " + weather.getDescription());
					});

		} catch(WebClientResponseException.NotFound notFound) {
			System.out.println("Could not locate the city. Please enter a valid city.");
		}









	}

}
