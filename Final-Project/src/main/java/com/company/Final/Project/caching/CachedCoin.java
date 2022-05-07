package com.company.Final.Project.caching;

// response of the coin that is going to be stored

public class CachedCoin {

    private String symbol;
    private String name;
    private String price;

    // overriding the toString method to return a similar response to the CoinAPI
    @Override
    public String toString() {
        return "\n" + getSymbol() + "\n=============" + "\nName: " + getName() + "\nSymbol: " + getSymbol()
                + "\nThe current price of " + getName() + " in USD is: " + getPrice() + "\n=============";
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
