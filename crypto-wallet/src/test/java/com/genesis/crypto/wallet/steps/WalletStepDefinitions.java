package com.genesis.crypto.wallet.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.crypto.wallet.AppIntegrationTest;
import com.genesis.crypto.wallet.coincap.CoincapCaller;
import com.genesis.crypto.wallet.core.WalletProcessor;
import com.genesis.crypto.wallet.csv.CSVParser;
import com.genesis.crypto.wallet.csv.CSVRecord;
import com.genesis.crypto.wallet.domain.WalletResult;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;

public class WalletStepDefinitions extends AppIntegrationTest {

	private static final Logger logger = LoggerFactory.getLogger(WalletStepDefinitions.class);

	@Autowired private WalletProcessor processor;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private CSVParser csvParser;

	// When mocking these beans were being used to do the magic

	//	@Autowired private CoincapCaller coincapCaller;
	//	@Autowired private OkHttpClient okHttpClient;
	//	@Autowired private Call mockCall;

	@Value("classpath:/mocks/wallet.csv")
	private Resource csvFile;

	private List<CSVRecord> csvRecords;
	private WalletResult result;

	@Given("^a client wallet file$")
	public void givenWalletCSV() throws Exception {
		csvRecords = csvParser.getWalletRecords(csvFile.getFile());
	}

	// Mocking Coincap simply does not is a good solution when working with high volume data
	// Decided just to move on with an integrated approach (like 'suggested' in the challenge description)

	//	@Given("^we have the Coincap API working fine$")
	//	public void prepareCoincapMocks() throws IOException {
	//		doReturn(mockCall).when(okHttpClient).newCall(any(Request.class));
	//
	//		String assetsMock = new String(Files.readAllBytes(this.assetsMock.getFile().toPath()));
	//		doReturn(assetsMock).when(coincapCaller).getResponseBody(contains("assets"), any());
	//
	//		String btcMock = new String(Files.readAllBytes(this.btcMock.getFile().toPath()));
	//		doReturn(btcMock).when(coincapCaller).getResponseBody(contains("bitcoin"), any());
	//
	//		String ethMock = new String(Files.readAllBytes(this.ethMock.getFile().toPath()));
	//		doReturn(ethMock).when(coincapCaller).getResponseBody(contains("ethereum"), any());
	//	}

	@When("^we initiallize the wallet processing$")
	public void initProcess() throws Exception {
		result = processor.process(csvRecords);
		logger.info(result.toString());
	}

	@Then("^get the final results as expected$")
	public void getResults() {
		assertThat(result).isNotNull().satisfies(it -> {
			assertThat(it.getTotal()).isNotNull();
			assertThat(it.getBestAsset()).isNotNull();
			assertThat(it.getWorstAsset()).isNotNull();
		});

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
