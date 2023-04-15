package com.genesis.crypto.wallet.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WalletAsset {

    private String symbol;
    private BigDecimal originalPrice;
    private BigDecimal actualPrice;
    private BigDecimal position;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getPosition() {
        return position;
    }

    public void setPosition(BigDecimal position) {
        this.position = position;
    }

    public BigDecimal getPerformance() {
        BigDecimal hundred = new BigDecimal(100.0);
        return getActualPrice().multiply(hundred).divide(getOriginalPrice(), RoundingMode.HALF_UP)
                                                 .divide(hundred, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "symbol='" + getSymbol() + '\'' +
                ", originalPrice=" + getOriginalPrice() +
                ", actualPrice=" + getActualPrice() +
                ", position=" + getPosition() +
                ", performance=" + getPerformance() +
                '}';
    }
}
