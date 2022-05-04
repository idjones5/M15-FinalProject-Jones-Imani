
# Weather, Space Station, and Cryptocurrency Tracker App


<p>This is a command line application that will allow a user to interact with a menu to get requested information about the weather, International Space Station, 
and cryptocurrency via input read from Java's Scanner class. </p>

## Tech/Frameworks

<p>Intellij Community Edition, Java 8, and Spring Boot (v. 2.6.7) with reactive web dependency were used to create this application.
The following APIs were used to obtain the information used in this application: </p>


1. [OpenWeather API](https://openweathermap.org/api) </br>
2. [CoinAPI](https://www.coinapi.io/) </br>
3. [Open Notify: Current ISS Location](http://open-notify.org/Open-Notify-API/)


## Features

This application presents the user with a menu and prompts them to enter one of the choices. As the user selects a choice they are able to retrieve information about the following:

<ol>
<li>Weather in a City</li>
<li>Location of the International Space Station (ISS)</li>
<li>Weather in the Location of the ISS</li>
<li>Current Cryptocurrency Prices</li>
</ol>

### Further Information about the features

#### Weather in a City
<P>Allows the user to input a city and retrieve the current weather in that current location </p>

<ol>
<li>This option will return:</li>
<ol>
<li>The location the user entered</li>
<li>The main weather at that location</li>
<li>Further detail about the main weather</li>
<li>The current temperature</li>
<li>What the current temperature "feels like"</li>
</ol>
</ol>

#### Location of the International Space Station (ISS)
<p>Provides information about the current location of the ISS.</p>

<ol>
<li>This option will return: </li>
<ol>
<li>The latitude of the ISS</li>
<li>The longitude of the ISS</li>
<li>The current country the ISS is in (if applicable)</li>
<li>The current city the ISS is in (if applicable)</li>
</ol>
</ol>

#### Weather in the location of the ISS
<p>This option will return weather information at the
current location of the ISS.</p>

<ol>
<li>This option will return: </li>
<ol>
<li>Every menu option included in the Location of the ISS option</li>
<li>The main weather at the current location</li>
<li>The temperature at the current location</li>
</ol>
</ol>

#### Current Cryptocurrency Prices
<p>Allows the user to input a crypto coin symbol and retrieve the current price of that coin in USD.</p>

<ol>
<li>This option will return: </li>
<ol>
<li>The name of the coin</li>
<li>The symbol of the coin</li>
<li>The current price of the coin in USD</li>
</ol>
</ol>

### Other Features

<ol>
<li>Caching</li>
<ol>
<li> user input is stored in a hashmap to reduce the amount of calls to the APIs if the user enters a duplicate location or coin</li>
<li>A scheduler is set up to clear the maps every five minutes while the app is running</li>
</ol>
</ol>