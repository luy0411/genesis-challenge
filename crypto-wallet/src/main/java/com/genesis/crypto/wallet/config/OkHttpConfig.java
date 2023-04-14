package com.genesis.crypto.wallet.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OkHttpConfig {

    @Bean
    public OkHttpClient getGkHttpClient(){
        return new OkHttpClient();
    }
}
