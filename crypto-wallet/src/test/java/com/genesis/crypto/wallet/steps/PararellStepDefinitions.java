package com.genesis.crypto.wallet.steps;

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

public class PararellStepDefinitions extends AppIntegrationTest {

	private static final Logger logger = LoggerFactory.getLogger(PararellStepDefinitions.class);

	@Autowired private WalletProcessor processor;
	@Autowired private CSVParser csvParser;

	@Value("classpath:/mocks/big-wallet.csv")
	private Resource csvFile;

	private List<CSVRecord> csvRecords;
	private WalletResult result;

	@Given("^a client big wallet file$")
	public void givenBigWalletFile() throws Exception {
		csvRecords = csvParser.getWalletRecords(csvFile.getFile());
	}

	@When("^we initiallize the wallet parallel processing$")
	public void initParallelProcess() throws Exception {
		result = processor.process(csvRecords);
		logger.info(result.toString());
	}

	@Then("^get the final results$")
	public void getResults() {
		assertThat(result).isNotNull();
	}

}
