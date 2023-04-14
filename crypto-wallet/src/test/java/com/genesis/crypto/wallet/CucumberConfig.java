package com.genesis.crypto.wallet;

import com.genesis.crypto.wallet.config.JacksonConfig;
import com.genesis.crypto.wallet.config.OkHttpConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {OkHttpConfig.class, JacksonConfig.class})
public class CucumberConfig {
}
