package com.company.Final.Project.apiCalls;

import com.company.Final.Project.coin.CryptoResponse;
import com.company.Final.Project.exceptions.WebExceptions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class CoinAPI {

    // method to call the coin API
    // also controls the response of a call
    // returns the symbol, currency name, and current price in USD

    public static void cryptoPrices(String userInput) {

        try {

            String userCryptoSymbol = userInput;

            String cryptoSymbol;
            String cryptoName;
            Double cryptoCurrentPrice;

            WebClient client = WebClient.create("https://rest.coinapi.io/v1/assets/"
                    + userCryptoSymbol + "?apikey=A59F836C-9CE3-4105-9DFB-3D489691575B");

            Mono<CryptoResponse[]> response = client
                    .get()
                    .retrieve()
                    .bodyToMono(CryptoResponse[].class);

            CryptoResponse[] cryptoResponse = response.share().block();

            cryptoSymbol = cryptoResponse[0].getAsset_id();
            cryptoName = cryptoResponse[0].getName();
            cryptoCurrentPrice = Double.valueOf(cryptoResponse[0].getPrice_usd());

            System.out.println("\n" + cryptoSymbol);
            System.out.println("=============");
            System.out.println("Name: " + cryptoName);
            System.out.println("Symbol: " + cryptoSymbol);
            System.out.printf("The current price of " + cryptoName + " in USD is %,.2f\n", cryptoCurrentPrice);
            System.out.println("=============");
        }
        catch (WebClientResponseException we) {

            WebExceptions.catchException(we);

            int statusCode = we.getRawStatusCode();

            if (statusCode == 550) {
                System.out.println("There is currently no data on this item.");
            }
        }
        catch (Exception exception) {
            System.out.println("Currency not found. Please try again.");
        }
    }
}
