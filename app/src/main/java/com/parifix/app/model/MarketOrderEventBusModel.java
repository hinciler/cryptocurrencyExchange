package com.parifix.app.model;

public class MarketOrderEventBusModel {
    private String marketCode;

    public MarketOrderEventBusModel(String str) {
        this.marketCode = str;
    }

    public String getMarketOrder() {
        return this.marketCode;
    }

    public void setMarketOrder(String str) {
        this.marketCode = str;
    }
}
