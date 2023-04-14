package com.genesis.crypto.wallet.coincap;

import java.util.Date;

public class HistoryItem {

    private Double priceUsd;
    private Date time;

    public Double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
