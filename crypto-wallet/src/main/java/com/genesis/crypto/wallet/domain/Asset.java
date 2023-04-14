package com.genesis.crypto.wallet.domain;

public class Asset {

    private String symbol;
    private Double originalPrice;
    private Double actualPrice;
    private Double position;
    private Double percent;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public Double getPercent() {
        return (getActualPrice() * 100.0) / getOriginalPrice();
    }

    @Override
    public String toString() {
        return "Asset{" +
                "symbol='" + getSymbol() + '\'' +
                ", originalPrice=" + getOriginalPrice() +
                ", actualPrice=" + getActualPrice() +
                ", position=" + getPosition() +
                ", percent=" + getPercent() +
                '}';
    }
}
