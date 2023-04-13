package com.genesis.crypto.wallet.domain;

public class WalletResult {

    private Double total;
    private Asset bestAsset;
    private Asset worstAsset;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Asset getBestAsset() {
        return bestAsset;
    }

    public void setBestAsset(Asset bestAsset) {
        this.bestAsset = bestAsset;
    }
}
