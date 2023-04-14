package com.genesis.crypto.wallet;

import com.genesis.crypto.wallet.config.JacksonConfig;
import com.genesis.crypto.wallet.config.OkHttpConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class AppIntegrationTest {

}
