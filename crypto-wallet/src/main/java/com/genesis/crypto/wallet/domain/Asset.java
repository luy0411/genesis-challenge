package com.genesis.crypto.wallet.domain;

public class Asset {

    private AssetType type;
    private Double bestPerformance;
    private Double worstPerformance;

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    public Double getBestPerformance() {
        return bestPerformance;
    }

    public void setBestPerformance(Double bestPerformance) {
        this.bestPerformance = bestPerformance;
    }

    public Double getWorstPerformance() {
        return worstPerformance;
    }

    public void setWorstPerformance(Double worstPerformance) {
        this.worstPerformance = worstPerformance;
    }
}
