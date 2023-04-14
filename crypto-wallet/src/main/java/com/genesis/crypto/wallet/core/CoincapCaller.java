package com.genesis.crypto.wallet.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.crypto.wallet.domain.coincap.CoincapAssetItem;
import com.genesis.crypto.wallet.domain.coincap.CoincapAssets;
import com.genesis.crypto.wallet.domain.coincap.CoincapHistory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

@Component
public class CoincapCaller {

    private static final String ASSETS_API = "https://api.coincap.io/v2/assets";
    private static final String HISTORY_API_QUERYSTRING = "?interval=d1&start=1617753600000&end=1617753601000";
    private static final String HISTORY_API = "https://api.coincap.io/v2/assets/%s/history";

    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;

    public CoincapCaller(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    public CoincapAssets getAssets() throws Exception {
        Request request = getRequest(ASSETS_API);
        return (CoincapAssets) callAPI(request, CoincapAssets.class);
    }

    public CoincapHistory getAssetHistoryById(String id) throws IOException {
        Request request = getRequest(String.format(HISTORY_API + HISTORY_API_QUERYSTRING, id));
        return (CoincapHistory) callAPI(request, CoincapHistory.class);
   }

    @NotNull
    private static Request getRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    private Object callAPI(Request request, Class responseClazz) throws IOException {
        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody,responseClazz);
        }
    }
}
