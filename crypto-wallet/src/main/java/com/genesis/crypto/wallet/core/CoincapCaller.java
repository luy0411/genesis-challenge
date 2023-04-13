package com.genesis.crypto.wallet.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.crypto.wallet.domain.coincap.CoincapAssetItem;
import com.genesis.crypto.wallet.domain.coincap.CoincapAssets;
import com.genesis.crypto.wallet.domain.coincap.CoincapHistory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

@Component
public class CoincapCaller {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public CoincapCaller() {
        this.okHttpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public CoincapAssets getAssets() throws Exception {
        Request request = new Request.Builder()
                .url("https://api.coincap.io/v2/assets")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(responseBody, CoincapAssets.class);
        }
    }

    public CoincapHistory getAssetHistoryById(String id) throws IOException {
        String url = String.format("https://api.coincap.io/v2/assets/%s/history?interval=d1", id);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();

            objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
            return objectMapper.readValue(responseBody, CoincapHistory.class);
        }
    }
}
