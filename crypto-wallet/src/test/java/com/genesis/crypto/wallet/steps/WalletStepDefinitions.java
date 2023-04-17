package com.genesis.crypto.wallet.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.crypto.wallet.AppIntegrationTest;
import com.genesis.crypto.wallet.core.WalletProcessor;
import com.genesis.crypto.wallet.csv.CSVParser;
import com.genesis.crypto.wallet.csv.CSVRecord;
import com.genesis.crypto.wallet.domain.WalletResult;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletStepDefinitions extends AppIntegrationTest {

	private static final Logger logger = LoggerFactory.getLogger(WalletStepDefinitions.class);

	@Autowired private WalletProcessor processor;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private CSVParser csvParser;

	@Value("classpath:/mocks/wallet.csv")
	private Resource csvFile;

	private List<CSVRecord> csvRecords;
	private WalletResult result;

	@Given("^a client wallet file$")
	public void givenWalletCSV() throws Exception {
		csvRecords = csvParser.getWalletRecords(csvFile.getFile());
	}

	@When("^we initiallize the wallet processing$")
	public void initProcess() throws Exception {
		result = processor.process(csvRecords);
		logger.info(result.toString());
	}

	@Then("^get the final results as expected$")
	public void getResults() {
		assertThat(result).isNotNull();

		// Must add specific querystring in Coincap history service call in a dynamic way
		// or use change seetings to use mocks in /resources/mocks/*.json

		//		assertThat(result).isNotNull().satisfies(it -> {
		//			assertThat(it.getTotal().doubleValue()).isEqualTo(16984.62);
		//		});
		//
		//		assertThat(result.getBestAsset()).isNotNull().satisfies(it -> {
		//			assertThat(it.getSymbol()).isEqualTo("BTC");
		//			assertThat(it.getOriginalPrice().doubleValue()).isEqualTo(37870.51);
		//			assertThat(it.getActualPrice().doubleValue()).isEqualTo(56999.97);
		//			assertThat(it.getPerformance().doubleValue()).isEqualTo(1.51);
		//		});
		//
		//		assertThat(result.getWorstAsset()).isNotNull().satisfies(it -> {
		//			assertThat(it.getSymbol()).isEqualTo("ETH");
		//			assertThat(it.getOriginalPrice().doubleValue()).isEqualTo(2004.98);
		//			assertThat(it.getActualPrice().doubleValue()).isEqualTo(2032.14);
		//			assertThat(it.getPerformance().doubleValue()).isEqualTo(1.01);
		//		});
	}
}
