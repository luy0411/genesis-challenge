package com.genesis.crypto.wallet;

import com.genesis.crypto.wallet.coincap.CoincapCaller;
import com.genesis.crypto.wallet.config.JacksonConfig;
import com.genesis.crypto.wallet.config.OkHttpConfig;
import com.genesis.crypto.wallet.csv.CSVParser;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
public class AppIntegrationTest {

}
