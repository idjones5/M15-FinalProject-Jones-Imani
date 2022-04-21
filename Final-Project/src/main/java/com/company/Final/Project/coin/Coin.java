package com.company.Final.Project.coin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coin {

    @JsonProperty("asset_id")
    private String asset_id;
    private String name;
    @JsonProperty("price_usd")
    private String price_usd;

    public String getAsset_id() {
        return asset_id;
    }

    public String getName() {
        return name;
    }

    public String getPrice_usd() {
        return price_usd;
    }
}
