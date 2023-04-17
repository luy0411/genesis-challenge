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
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Component
public class WalletProcessor {

    private static final Logger logger = LoggerFactory.getLogger(WalletProcessor.class);

    private CSVParser csvParser;
    private CoincapCaller coincapCaller;
    private List<WalletAsset> assets = Collections.synchronizedList(new ArrayList<>());

    public WalletProcessor(CSVParser csvParser, CoincapCaller coincapCaller) {
        this.csvParser = csvParser;
        this.coincapCaller = coincapCaller;
    }

    public WalletResult process(List<CSVRecord> records) throws Exception {
        logger.info("Now is " + LocalDateTime.now());

        List<List<CSVRecord>> partitions = Lists.partition(records, 3);
        createParallelProcessing(partitions);

        return new WalletResult(assets);
    }

    private void createParallelProcessing(List<List<CSVRecord>> partitions) {
        if (partitions.isEmpty())
            throw new RuntimeException("No data to process. CSV file is empty.");

        for (List<CSVRecord> partition : partitions) {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            List<Runnable> tasks = getTasks(partition);
            CompletableFuture<?>[] futures = tasks.stream()
                    .map(task -> CompletableFuture.runAsync(task, executorService))
                    .toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(futures).join();
            executorService.shutdown();
        }
    }

    private List<Runnable> getTasks(List<CSVRecord> partition) {
        List<Runnable> runnables = new ArrayList<>();
        for (CSVRecord csvRecord : partition) {
            Runnable task = (() -> {
                try {
                    WalletAsset asset = processAssetInAnUniqueThread(csvRecord);
                    if (!asset.isNull()) {
                        assets.add(asset);
                    } else {
                        logger.error(String.format("Could not process asset %s correctly.", csvRecord.getSymbol()));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(String.format("Error during asset %s processing", csvRecord.getSymbol()));
                }
            });

            runnables.add(task);
        }

        return runnables;
    }

    private WalletAsset processAssetInAnUniqueThread(CSVRecord record) throws Exception {
        logger.info(String.format("Submitted request %s at %s", record.getSymbol(), LocalDateTime.now()));

        WalletAsset asset = new WalletAsset();

        AssetsWrapper coincapAssets = coincapCaller.getAssets();
        Optional<AssetItem> coincapAsset = coincapAssets.getData().stream()
                                                   .filter(a -> a.getSymbol().equals(record.getSymbol()))
                                                   .findFirst();

        if (coincapAsset.isPresent()) {
            HistoryWrapper coincapHistory = coincapCaller.getAssetHistoryById(coincapAsset.get().getId());
            Optional<List<HistoryItem>> actualPrice = Optional.of(coincapHistory.getData());

            if (actualPrice.isPresent()) {
                List<HistoryItem> historyItems = actualPrice.get();
                if (historyItems.size() > 0) {
                    asset = buildAsset(record, historyItems.get(0));
                    logger.info(asset.toString());
                }
            }
        }

        return asset;
    }

    // TODO: this builder should/must be in other place (SOLID S is crying)
    private WalletAsset buildAsset(CSVRecord record, HistoryItem actualPrice) {
        WalletAsset asset = new WalletAsset();
        asset.setSymbol(record.getSymbol());
        asset.setOriginalPrice(record.getPrice());
        asset.setActualPrice(actualPrice.getPriceUsd());
        asset.setPosition(record.getQuantity().multiply(actualPrice.getPriceUsd()));

        return asset;
    }
}
