package com.genesis.crypto.wallet.domain.coincap;

import java.time.Period;
import java.util.Date;

public class CoincapHistoryItem {

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
