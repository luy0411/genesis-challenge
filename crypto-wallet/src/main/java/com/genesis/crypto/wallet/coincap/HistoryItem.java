package com.genesis.crypto.wallet.coincap;

import java.math.BigDecimal;
import java.util.Date;

public class HistoryItem {

    private BigDecimal priceUsd;
    private Date time;

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
