package com.genesis.crypto.wallet;

import com.genesis.crypto.wallet.core.WalletProcessor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalletStepDefinitions extends AppIntegrationTest {

	private static final Logger logger = LoggerFactory.getLogger(WalletStepDefinitions.class);

	@Given("^a client wallet file$")
	public void a_client_wallet_file() {
		logger.info("given");
	}

	@When("^we parse this file$")
	public void we_parse_this_file() {
		logger.info("given");
	}

	@When("^call Coincap API$")
	public void call_coincap_api() {
		// Write code here that turns the phrase above into concrete actions
	}

	@Then("^get the final result$")
	public void get_the_final_result() {
		// Write code here that turns the phrase above into concrete actions
	}
}
