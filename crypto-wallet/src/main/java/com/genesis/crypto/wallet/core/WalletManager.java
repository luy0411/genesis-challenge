package com.genesis.crypto.wallet.core;

import com.genesis.crypto.wallet.domain.Asset;
import com.genesis.crypto.wallet.domain.WalletResult;
import com.genesis.crypto.wallet.domain.coincap.CoincapAssetItem;
import com.genesis.crypto.wallet.domain.coincap.CoincapAssets;
import com.genesis.crypto.wallet.domain.coincap.CoincapHistory;
import com.genesis.crypto.wallet.domain.coincap.CoincapHistoryItem;
import com.genesis.crypto.wallet.domain.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class WalletManager {

    private static final Logger logger = LoggerFactory.getLogger(WalletManager.class);

    private CSVParser csvParser;
    private CoincapCaller coincapCaller;

    public WalletManager(CSVParser csvParser, CoincapCaller coincapCaller) {
        this.csvParser = csvParser;
        this.coincapCaller = coincapCaller;
    }

    public void doStuff() throws Exception {
        // GIVEN: a csv file that is a client wallet
        List<CSVRecord> records = csvParser.getWalletRecords();
        List<Asset> assets = new ArrayList<>();

        // WHEN: for each record, search in the Coincap API its id and actual price (D1)
        // TODO: put assets response in a cache
        for (CSVRecord record : records) {
            CoincapAssets coincapAssets = coincapCaller.getAssets();
            CoincapAssetItem coincapAsset = coincapAssets.getData().stream()
                                                                   .filter(a -> a.getSymbol().equals(record.getSymbol()))
                                                                   .findFirst().get();

            CoincapHistory coincapHistory = coincapCaller.getAssetHistoryById(coincapAsset.getId());
            CoincapHistoryItem actualPrice = coincapHistory.getData().get(0);

            Asset asset = createAsset(record, actualPrice);
            assets.add(asset);
        }

        // THEN: print results
        WalletResult result = new WalletResult(assets);
        logger.info(result.toString());
    }

    private Asset createAsset(CSVRecord record,CoincapHistoryItem actualPrice) {
        Asset asset = new Asset();
        asset.setSymbol(record.getSymbol());
        asset.setOriginalPrice(record.getPrice());
        asset.setActualPrice(actualPrice.getPriceUsd());
        asset.setPosition(record.getQuantity() * actualPrice.getPriceUsd());

        return asset;
    }
}
