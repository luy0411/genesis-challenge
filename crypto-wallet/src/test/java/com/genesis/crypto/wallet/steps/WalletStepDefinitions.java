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
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WalletStepDefinitions extends AppIntegrationTest {

	private static final Logger logger = LoggerFactory.getLogger(WalletStepDefinitions.class);

	@Autowired private WalletProcessor processor;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private CoincapCaller coincapCaller;
	@Autowired private CSVParser csvParser;
	@Autowired private OkHttpClient okHttpClient;
	@Autowired private Call mockCall;

	@Value("classpath:/mocks/wallet.csv")
	private Resource csvFile;

	@Value("classpath:/mocks/assets.json")
	private Resource assetsMock;

	@Value("classpath:/mocks/btc-history.json")
	private Resource btcMock;

	@Value("classpath:/mocks/eth-history.json")
	private Resource ethMock;

	private List<CSVRecord> csvRecords;
	private WalletResult result;

	@Given("^a client wallet file$")
	public void givenWalletCSV() throws Exception {
		csvRecords = csvParser.getWalletRecords(csvFile.getFile());
	}

	@Given("^we have the Coincap API working fine$")
	public void prepareCoincapMocks() throws IOException {
		doReturn(mockCall).when(okHttpClient).newCall(any(Request.class));

		String assetsMock = new String(Files.readAllBytes(this.assetsMock.getFile().toPath()));
		doReturn(assetsMock).when(coincapCaller).getResponseBody(eq(CoincapCaller.ASSETS_API), any());

		String btcMock = new String(Files.readAllBytes(this.btcMock.getFile().toPath()));
		doReturn(btcMock).when(coincapCaller).getResponseBody(contains("bitcoin"), any());

		String ethMock = new String(Files.readAllBytes(this.ethMock.getFile().toPath()));
		doReturn(ethMock).when(coincapCaller).getResponseBody(contains("ethereum"), any());
	}

	@When("^we initiallize the wallet processing$")
	public void initProcess() throws Exception {
		result = processor.doStuff(csvRecords);
	}

	@Then("^get the final results as expected$")
	public void getResults() {
		assertThat(result).isNotNull();
		assertThat(result.getTotal().doubleValue()).isEqualTo(16984.62);

		assertThat(result.getBestAsset()).isNotNull();
		assertThat(result.getBestAsset().getSymbol()).isEqualTo("BTC");
		assertThat(result.getBestAsset().getOriginalPrice().doubleValue()).isEqualTo(37870.51);
		assertThat(result.getBestAsset().getActualPrice().doubleValue()).isEqualTo(56999.97);
		assertThat(result.getBestAsset().getPerformance().doubleValue()).isEqualTo(1.51);

		assertThat(result.getWorstAsset()).isNotNull();
		assertThat(result.getWorstAsset().getSymbol()).isEqualTo("ETH");
		assertThat(result.getWorstAsset().getOriginalPrice().doubleValue()).isEqualTo(2004.98);
		assertThat(result.getWorstAsset().getActualPrice().doubleValue()).isEqualTo(2032.14);
		assertThat(result.getWorstAsset().getPerformance().doubleValue()).isEqualTo(1.01);
	}
}
