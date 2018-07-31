package com.parifix.app.model;

public class MarketChangeEventBusHelperModel {
    private String marketCode;

    public MarketChangeEventBusHelperModel(String str) {
        this.marketCode = str;
    }

    public String getMarketCode() {
        return this.marketCode;
    }

    public void setMarketCode(String str) {
        this.marketCode = str;
    }
}
