package com.genesis.crypto.wallet.coincap;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@PropertySource("classpath:coincap.properties")
public class CoincapCaller {

    @Value( "${coincap.assets.url}" )
    private String ASSETS_API;

    @Value( "${coincap.history.url}" )
    private String HISTORY_API;

    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;

    public CoincapCaller(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    // TODO: wouldn't it be great to have a daily cache here (Spring Cache to the rescue)?
    public AssetsWrapper getAssets() throws Exception {
        Request request = getRequest(ASSETS_API);
        return (AssetsWrapper) callAPI(request, AssetsWrapper.class);
    }

    // TODO: wouldn't it be great to have a daily cache here (Spring Cache to the rescue)?
    public HistoryWrapper getAssetHistoryById(String id) throws IOException, InterruptedException {
        Request request = getRequest(String.format(HISTORY_API, id));
        return (HistoryWrapper) callAPI(request, HistoryWrapper.class);
   }

    private static Request getRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        return request;
    }

    private Object callAPI(Request request, Class responseClazz) throws IOException, InterruptedException {
        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = getResponseBody(request.url().toString(), response);
            Thread.sleep(1000);
            return objectMapper.readValue(responseBody,responseClazz);
        }
    }

    // TODO: When mocking on unit testing this is helpful (not saying its beautiful)
    public String getResponseBody(String url, Response response) throws IOException {
        String responseBody = response != null ? response.body().string() : "";
        return responseBody;
    }
}
