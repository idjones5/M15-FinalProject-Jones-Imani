package com.company.Final.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.company.Final.Project.apiCalls.OpenWeatherAPI.openWeather;
import static com.company.Final.Project.apiCalls.IssAPI.issAPI;
import static com.company.Final.Project.apiCalls.IssOpenWeatherAPIs.issWeatherConditions;
import static com.company.Final.Project.apiCalls.CoinAPI.cryptoPrices;

@SpringBootApplication
public class FinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);

		Set<Integer> menuChoicesSet = new HashSet<Integer>(Arrays.asList(1,2,3,4,5));
		Scanner scan = new Scanner(System.in);
		String userInput;
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
					userInput = scan.nextLine();
					userChoice = Integer.parseInt(userInput);

					if (menuChoicesSet.contains(userChoice)) {

						switch (userChoice) {
							case 1:
								System.out.println(" Weather in a city");
								System.out.println("===================");
								System.out.println("What city would you like to find the weather for?");
								userInput = scan.nextLine();
								openWeather(userInput);

								menuOptionMessage();
								break;
							case 2:

								System.out.println("Location of ISS");
								System.out.println("===================");
								issAPI();

								menuOptionMessage();
								break;
							case 3:
								System.out.println("Weather in location of ISS");
								System.out.println("===========================");
								issWeatherConditions();

								menuOptionMessage();
								break;
							case 4:
								System.out.println("Current crypto prices");
								System.out.println("======================");
								System.out.println("Enter the symbol of a cryptocurrency (ex: BTC or ETH)");
								userInput = scan.nextLine();
								cryptoPrices(userInput);

								menuOptionMessage();
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
				}	while (userChoice != 5);
			} catch (NumberFormatException ne) {
				System.out.println("Please enter a valid number.");
			} catch (Exception exception) {
				System.out.println("An error has occurred.");
			}

	}

	public static void menuOptionMessage() {
		System.out.println("\n" + "Enter a menu selection or press 5 to exit.");
	}

}
