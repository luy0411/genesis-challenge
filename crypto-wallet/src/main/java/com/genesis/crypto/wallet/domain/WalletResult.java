package com.genesis.crypto.wallet.domain;

import java.util.Comparator;
import java.util.List;

public class WalletResult {

    private List<Asset> assets;

    public WalletResult(List<Asset> assets) {
        this.assets = assets;
    }

    public Double getTotal() {
        Double total = 0.0;
        for (Asset asset : assets) {
            total += asset.getPosition();
        }
        return total;
    }

    public Asset getBestAsset() {
        return assets.stream().max(Comparator.comparingDouble(Asset::getPerformance)).get();
    }

    public Asset getWorstAsset() {
        return assets.stream().min(Comparator.comparingDouble(Asset::getPerformance)).get();
    }

    @Override
    public String toString() {
        Asset best = getBestAsset();
        Asset worst = getWorstAsset();

        return String.format("total=%f,best_asset=%s,best_performance=%f,worst_asset=%s,worst_performance=%f}",
                getTotal(), best.getSymbol(), best.getPerformance(), worst.getSymbol(), worst.getPerformance());

    }
}
