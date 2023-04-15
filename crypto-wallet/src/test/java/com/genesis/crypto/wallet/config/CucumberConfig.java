package com.genesis.crypto.wallet.config;

import com.genesis.crypto.wallet.coincap.CoincapCaller;
import com.genesis.crypto.wallet.config.JacksonConfig;
import com.genesis.crypto.wallet.config.OkHttpConfig;
import com.genesis.crypto.wallet.csv.CSVParser;
import io.cucumber.spring.CucumberContextConfiguration;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberConfig {

    @SpyBean CoincapCaller coincapCaller;
    @MockBean OkHttpClient okHttpClient;
    @MockBean Call call;
}
