package com.company.Final.Project;

import com.company.Final.Project.coin.Coin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class FinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);

		//Scanner scan = new Scanner(System.in); // take user input from the scanner to search for the location etc
		//String userInput = scan.nextLine();

		//String userLocation = userInput; // location needs to be in the format London,uk

		//WebClient client = WebClient.create("http://api.openweathermap.org/data/2.5/weather?q=" + userLocation + "&APPID=fd238fddcae5fb3172123f01221a835d"); // city location
		WebClient client1 = WebClient.create("https://rest.coinapi.io/v1/assets/BTC?apikey=A59F836C-9CE3-4105-9DFB-3D489691575B"); // coin price - logged at BTC rn
		WebClient client2 = WebClient.create("http://api.open-notify.org/iss-now.json?callback"); // iss location

		WebClient client3 = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=22.0554&lon=-28.8485&appid=fd238fddcae5fb3172123f01221a835d"); // lat and long


		Mono<Coin[]> response = client1
				.get()
				.retrieve()
				.bodyToMono(Coin[].class);

		Coin[] coin = response.share().block();


		System.out.println(coin[0].getAsset_id());

		System.out.println("===========================================");
		System.out.println("Welcome. Please select from the menu below");
		System.out.println("===========================================");

		System.out.println("1 - Weather in a city");
		System.out.println("2 - Location of the International Space Station (ISS)");
		System.out.println("3 - Weather in the Location of the ISS");
		System.out.println("4 - Current Cryptocurrency Prices");
		System.out.println("5 - Exit");






	}

}
