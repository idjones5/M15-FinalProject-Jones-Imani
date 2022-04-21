package com.company.Final.Project.coin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class CoinResponse {

    private Object[] assets;

    public Object getCoin() {
        return assets;
    }

}
