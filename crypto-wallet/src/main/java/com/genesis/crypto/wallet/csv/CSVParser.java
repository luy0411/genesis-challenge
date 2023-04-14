package com.genesis.crypto.wallet.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;

@Component
public class CSVParser {

    @Value("classpath:/wallet.csv")
    private Resource resource;

    public List<CSVRecord> getWalletRecords() throws IOException {
        try (Reader reader = Files.newBufferedReader(resource.getFile().toPath())) {
            CsvToBean<CSVRecord> cb = new CsvToBeanBuilder<CSVRecord>(reader)
                    .withType(CSVRecord.class)
                    .build();
            return cb.parse();
        }
    }
}
