package com.genesis.crypto.wallet.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CSVParser {

    public List<CSVRecord> getWalletRecords(File file) throws IOException {
        try (Reader reader = Files.newBufferedReader(file.toPath())) {
            CsvToBean<CSVRecord> cb = new CsvToBeanBuilder<CSVRecord>(reader)
                    .withType(CSVRecord.class)
                    .build();
            return cb.parse();
        }
    }
}
