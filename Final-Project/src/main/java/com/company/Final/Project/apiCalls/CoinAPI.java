package com.company.Final.Project.apiCalls;

import com.company.Final.Project.coin.CryptoResponse;
import com.company.Final.Project.exceptions.WebExceptions;
import com.company.Final.Project.caching.CachedCoin;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.Map;

public class CoinAPI {

    // method to call the coin API
    // also controls the response of a call
    // returns the symbol, currency name, and current price in USD
    // stores the data in the map provided for caching

    public static void cryptoPrices(String userInput, Map<String, CachedCoin> map) {

        try {

            String userCryptoSymbol = userInput;

            String cryptoSymbol;
            String cryptoName;
            Double cryptoCurrentPrice;

            CachedCoin userCoin = new CachedCoin();

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

            userCoin.setSymbol(cryptoSymbol);
            userCoin.setName(cryptoName);
            userCoin.setPrice(String.format("%,.2f", cryptoCurrentPrice));

            map.put(userInput, userCoin);
        }
        catch (WebClientResponseException we) {

            WebExceptions.catchException(we);

            int statusCode = we.getRawStatusCode();

            if (statusCode == 550) {
                System.out.println("There is currently no data on this item.");
            }
        }
        catch (Exception exception) {
            System.out.println("Symbol not found. Please try again.");
        }
    }
}
