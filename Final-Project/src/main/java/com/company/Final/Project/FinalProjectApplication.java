package com.company.Final.Project;

import com.company.Final.Project.caching.CachedCoin;
import com.company.Final.Project.caching.CachedWeather;
import com.company.Final.Project.caching.CoinScheduler;
import com.company.Final.Project.caching.WeatherScheduler;
import com.company.Final.Project.fileWriter.CoinFile;
import com.company.Final.Project.fileWriter.ISSWeatherFile;
import com.company.Final.Project.fileWriter.WeatherFile;
import com.company.Final.Project.fileWriter.IssFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
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

		Set<Integer> menuChoicesSet = new HashSet<>(Arrays.asList(1,2,3,4,5,6));
		HashMap<String, CachedWeather> cachedWeather = new HashMap<>();
		HashMap<String, CachedCoin> cachedCoin = new HashMap<>();

		HashMap<Integer, IssFile> issFileData = new HashMap<>();
		HashMap<Integer, ISSWeatherFile> issWeatherFileData = new HashMap<>();

		HashMap<Integer, String> filenamesWeather = new HashMap<>();
		HashMap<Integer, String> filenamesISS = new HashMap<>();
		HashMap<Integer, String> filenamesISSWeather = new HashMap<>();
		HashMap<Integer, String> filenamesCrypto = new HashMap<>();

		WeatherScheduler clearCachedWeather = new WeatherScheduler();
		CoinScheduler clearCachedCoin = new CoinScheduler();

		clearCachedWeather.clearMap(cachedWeather);
		clearCachedCoin.clearMap(cachedCoin);

		Scanner scan = new Scanner(System.in);
		String userInput;
		String toCSV;
		int userChoice = 0;

		int mapSizeBeforeWeather = 0; // counts if retrieval is successful or if information is added to map
		int mapSizeAfterWeather = 0; // keeps track of the size of the map
		int mapSizeBeforeCoin = 0;
		int mapSizeAfterCoin = 0;

		System.out.println("===========================================");
		System.out.println("Welcome. Please select from the menu below");
		System.out.println("===========================================");

		menuSelection();

		do {
			try {

					userInput = scan.nextLine();
					userChoice = Integer.parseInt(userInput);

					if (menuChoicesSet.contains(userChoice)) {

						switch (userChoice) {
							case 1:
								System.out.println("===================");
								System.out.println(" Weather in a city");
								System.out.println("===================");
								System.out.println("What city would you like to find the weather for?");
								userInput = scan.nextLine();
								userInput = userInputStringFormat(userInput);
								mapSizeBeforeWeather = cachedWeather.size();

								if (cachedWeather.containsKey(userInput)) {
									System.out.println(cachedWeather.get(userInput));
									mapSizeAfterWeather++;
								} else {
									openWeather(userInput, cachedWeather);
									mapSizeAfterWeather = cachedWeather.size();
								}


								if (mapSizeAfterWeather > mapSizeBeforeWeather) {

									toCSV = csvMessage();

									if (toCSV.equals("y") && cachedWeather.containsKey(userInput)) {
										WeatherFile.newCSVFile(userInput, scan, filenamesWeather, cachedWeather);

									} else if (toCSV.equals("y") && !cachedWeather.containsKey(userInput)) {
										openWeather(userInput, cachedWeather);
										WeatherFile.newCSVFile(userInput, scan, filenamesWeather, cachedWeather);

									} else if (toCSV.equals("n")) {
										System.out.println("Okay, thank you.");
									} else {
										System.out.println("Invalid.");
									}
								}


								menuOptionMessage();
								break;
							case 2:
								System.out.println("===================");
								System.out.println("Location of ISS");
								System.out.println("===================");
								issAPI(issFileData);

								toCSV = csvMessage();

								if (toCSV.equals("y") && !issFileData.isEmpty()) {
									IssFile.newCSVFile(scan, filenamesISS, issFileData);

								} else if (toCSV.equals("y") && issFileData.isEmpty()) {
									issAPI(issFileData);
									IssFile.newCSVFile(scan, filenamesISS, issFileData);

								} else if (toCSV.equals("n")) {
									System.out.println("Okay, thank you.");
								} else {
									System.out.println("Invalid.");
								}


								menuOptionMessage();
								break;
							case 3:
								System.out.println("===========================");
								System.out.println("Weather in location of ISS");
								System.out.println("===========================");
								issWeatherConditions(issWeatherFileData);

								toCSV = csvMessage();

								if (toCSV.equals("y") && !issWeatherFileData.isEmpty()) {
									ISSWeatherFile.newCSVFile(scan, filenamesISSWeather, issWeatherFileData);

								} else if (toCSV.equals("y") && issWeatherFileData.isEmpty()) {
									issAPI(issFileData);
									ISSWeatherFile.newCSVFile(scan, filenamesISSWeather, issWeatherFileData);

								} else if (toCSV.equals("n")) {
									System.out.println("Okay, thank you.");
								} else {
									System.out.println("Invalid.");
								}


								menuOptionMessage();
								break;
							case 4:
								System.out.println("======================");
								System.out.println("Current crypto prices");
								System.out.println("======================");
								System.out.println("Enter the symbol of a cryptocurrency (ex: BTC or ETH)");
								userInput = scan.nextLine();
								userInput = userInput.toUpperCase();
								mapSizeBeforeCoin = cachedCoin.size();

								if (cachedCoin.containsKey(userInput)) {
									System.out.println(cachedCoin.get(userInput));
									mapSizeAfterCoin++;
								} else {
									cryptoPrices(userInput, cachedCoin);
									mapSizeAfterCoin = cachedCoin.size();
								}


								if (mapSizeAfterCoin > mapSizeBeforeCoin) {

									toCSV = csvMessage();

									if (toCSV.equals("y") && cachedCoin.containsKey(userInput)) {
										CoinFile.newCSVFile(userInput, scan, filenamesCrypto, cachedCoin);

									} else if (toCSV.equals("y") && !cachedCoin.containsKey(userInput)) {
										cryptoPrices(userInput, cachedCoin);
										CoinFile.newCSVFile(userInput, scan, filenamesCrypto, cachedCoin);

									} else if (toCSV.equals("n")) {
										System.out.println("Okay, thank you.");
									} else {
										System.out.println("Invalid.");
									}
								}


								menuOptionMessage();
								break;
							case 5:
								System.out.println("Goodbye.");
								break;
							case 6:
								menuSelection();
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
			} catch (Exception exception) {
				System.out.println("An error has occurred.");
			}
		} while (userChoice != 5);
	}

	public static void menuOptionMessage() {
		System.out.println("\n" + "Enter a menu selection. ");
		System.out.println("===========================");
		System.out.println("You can also: ");
		System.out.println("Press 5 to exit.");
		System.out.println("Press 6 to view the menu options again.");
		System.out.println("===========================");
	}

	public static void menuSelection() {
		System.out.println("1 - Weather in a city");
		System.out.println("2 - Location of the International Space Station (ISS)");
		System.out.println("3 - Weather in the Location of the ISS");
		System.out.println("4 - Current Cryptocurrency Prices");
		System.out.println("5 - Exit");
	}

	public static String userInputStringFormat(String userInput) {
		// method to convert the first letter of input to uppercase
		// and the rest of the characters to lowercase
		// this is to reduce possible redundancy of values in hashmap
		// ex: if a user enters "london" and "London" the openWeatherAPI
		// returns the same data

		String[] userInputC;
		String temp;

		userInput = userInput.toLowerCase();
		userInputC = userInput.split("");
		userInputC[0] = userInputC[0].toUpperCase();
		temp = String.join("", userInputC);
		userInput = temp;

		return userInput;
	}

	public static String csvMessage() {

		Scanner scan = new Scanner(System.in);

		System.out.println("Would you like to store this data in a csv " +
				" \nfile on your computer? If so, press y. If no, press n.");

		System.out.println("\nPlease note that the following data will " +
				"\nbe added to the file if it already exists.");
		System.out.println("============================================");
		String toCSV = scan.nextLine();

		return toCSV;
	}
}
