package com.genesis.crypto.wallet.coincap;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CoincapCaller {

    public static final String ASSETS_API = "https://api.coincap.io/v2/assets";
    public static final String HISTORY_API = "https://api.coincap.io/v2/assets/%s/history";
    public static final String HISTORY_API_QUERYSTRING = "?interval=d1&start=1617753600000&end=1617753601000";

    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;

    public CoincapCaller(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    public AssetsWrapper getAssets() throws Exception {
        Request request = getRequest(ASSETS_API);
        return (AssetsWrapper) callAPI(request, AssetsWrapper.class);
    }

    public HistoryWrapper getAssetHistoryById(String id) throws IOException {
        Request request = getRequest(String.format(HISTORY_API + HISTORY_API_QUERYSTRING, id));
        return (HistoryWrapper) callAPI(request, HistoryWrapper.class);
   }

    private static Request getRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        return request;
    }

    private Object callAPI(Request request, Class responseClazz) throws IOException {
        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = getResponseBody(request.url().toString(), response);
            return objectMapper.readValue(responseBody,responseClazz);
        }
    }

    // Public access for mocking
    public String getResponseBody(String url, Response response) throws IOException {
        String responseBody = response != null ? response.body().string() : "";
        return responseBody;
    }
}
