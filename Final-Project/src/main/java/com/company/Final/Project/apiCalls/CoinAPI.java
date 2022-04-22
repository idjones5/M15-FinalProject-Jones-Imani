package com.company.Final.Project.apiCalls;

import com.company.Final.Project.coin.Coin;
import com.company.Final.Project.exceptions.WebExceptions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class CoinAPI {

    public static void cryptoPrices(String userInput) {

        try {

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

            System.out.println("\n" + cryptoSymbol);
            System.out.println("=============");
            System.out.println("Name: " + cryptoName);
            System.out.println("Symbol: " + cryptoSymbol);
            System.out.printf("The current price of " + cryptoName + " in USD is %,.2f\n", cryptoCurrentPrice);
            System.out.println("=============");
        }
        catch (WebClientResponseException we) {
            WebExceptions.catchException(we);
        }
        catch (Exception exception) {
            System.out.println("An Error has occurred " + exception.getMessage());
        }
    }
}
