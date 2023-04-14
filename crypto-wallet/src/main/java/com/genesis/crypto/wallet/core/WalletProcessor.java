package com.genesis.crypto.wallet.core;

import com.genesis.crypto.wallet.coincap.CoincapCaller;
import com.genesis.crypto.wallet.csv.CSVParser;
import com.genesis.crypto.wallet.domain.WalletAsset;
import com.genesis.crypto.wallet.domain.WalletResult;
import com.genesis.crypto.wallet.coincap.AssetItem;
import com.genesis.crypto.wallet.coincap.AssetsWrapper;
import com.genesis.crypto.wallet.coincap.HistoryWrapper;
import com.genesis.crypto.wallet.coincap.HistoryItem;
import com.genesis.crypto.wallet.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WalletProcessor {

    private static final Logger logger = LoggerFactory.getLogger(WalletProcessor.class);

    private CSVParser csvParser;
    private CoincapCaller coincapCaller;

    public WalletProcessor(CSVParser csvParser, CoincapCaller coincapCaller) {
        this.csvParser = csvParser;
        this.coincapCaller = coincapCaller;
    }

    public void doStuff() throws Exception {
        List<CSVRecord> records = csvParser.getWalletRecords();
        List<WalletAsset> assets = new ArrayList<>();

        for (CSVRecord record : records) {
            AssetsWrapper coincapAssets = coincapCaller.getAssets();
            AssetItem coincapAsset = coincapAssets.getData().stream()
                                                            .filter(a -> a.getSymbol().equals(record.getSymbol()))
                                                            .findFirst().get();

            HistoryWrapper coincapHistory = coincapCaller.getAssetHistoryById(coincapAsset.getId());
            HistoryItem actualPrice = coincapHistory.getData().get(0);

            WalletAsset asset = createAsset(record, actualPrice);
            assets.add(asset);
        }

        WalletResult result = new WalletResult(assets);
        logger.info(result.toString());
    }

    private WalletAsset createAsset(CSVRecord record, HistoryItem actualPrice) {
        WalletAsset asset = new WalletAsset();
        asset.setSymbol(record.getSymbol());
        asset.setOriginalPrice(record.getPrice());
        asset.setActualPrice(actualPrice.getPriceUsd());
        asset.setPosition(record.getQuantity() * actualPrice.getPriceUsd());

        return asset;
    }
}
