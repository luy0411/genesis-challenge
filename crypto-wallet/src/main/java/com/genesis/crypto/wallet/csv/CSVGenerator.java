package com.genesis.crypto.wallet.csv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.crypto.wallet.coincap.AssetItem;
import com.genesis.crypto.wallet.coincap.AssetsWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

@Component
public class CSVGenerator {

    @Value("classpath:assets.json")
    private Resource assets;

    private ObjectMapper objectMapper;

    public CSVGenerator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void generate() throws IOException {
        String assetsJSON = new String(Files.readAllBytes(assets.getFile().toPath()));
        AssetsWrapper assets = objectMapper.readValue(assetsJSON, AssetsWrapper.class);

        StringBuffer sb = new StringBuffer("symbol,quantity,price\n");

        for (AssetItem data : assets.getData()) {
            Random random = new Random();
            BigDecimal quantity = new BigDecimal(random.nextDouble(1, 5)).setScale(5, RoundingMode.HALF_UP);
            BigDecimal price  = new BigDecimal(random.nextDouble(1,20)).setScale(5, RoundingMode.HALF_UP);
            sb.append(data.getSymbol())
              .append(",").append(quantity)
              .append(",").append(price)
              .append("\n");
        }

        System.out.println(sb.toString());
    }
}
