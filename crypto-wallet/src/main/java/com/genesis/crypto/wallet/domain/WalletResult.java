package com.genesis.crypto.wallet.domain;

import java.util.Comparator;
import java.util.List;

public class WalletResult {

    private List<WalletAsset> assets;

    public WalletResult(List<WalletAsset> assets) {
        this.assets = assets;
    }

    public Double getTotal() {
        Double total = 0.0;
        for (WalletAsset asset : assets) {
            total += asset.getPosition();
        }
        return total;
    }

    public WalletAsset getBestAsset() {
        return assets.stream().max(Comparator.comparingDouble(WalletAsset::getPerformance)).get();
    }

    public WalletAsset getWorstAsset() {
        return assets.stream().min(Comparator.comparingDouble(WalletAsset::getPerformance)).get();
    }

    @Override
    public String toString() {
        WalletAsset best = getBestAsset();
        WalletAsset worst = getWorstAsset();

        return String.format("total=%f,best_asset=%s,best_performance=%f,worst_asset=%s,worst_performance=%f}",
                getTotal(), best.getSymbol(), best.getPerformance(), worst.getSymbol(), worst.getPerformance());

    }
}
