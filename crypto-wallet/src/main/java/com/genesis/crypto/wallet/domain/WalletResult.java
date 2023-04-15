package com.genesis.crypto.wallet.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class WalletResult {

    private List<WalletAsset> assets;

    public WalletResult(List<WalletAsset> assets) {
        this.assets = assets;
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0.0);
        for (WalletAsset asset : assets) {
            total = total.add(asset.getPosition());
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public WalletAsset getBestAsset() {
        return assets.stream().max(Comparator.comparing(a -> a.getPerformance())).get();
    }

    public WalletAsset getWorstAsset() {
        return assets.stream().min(Comparator.comparing(a -> a.getPerformance())).get();
    }

    @Override
    public String toString() {
        WalletAsset best = getBestAsset();
        WalletAsset worst = getWorstAsset();

        return String.format("total=%f,best_asset=%s,best_performance=%f,worst_asset=%s,worst_performance=%f}",
                getTotal(), best.getSymbol(), best.getPerformance(), worst.getSymbol(), worst.getPerformance());

    }
}
