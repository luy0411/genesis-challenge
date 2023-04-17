package com.genesis.crypto.wallet;

import com.genesis.crypto.wallet.core.WalletProcessor;
import com.genesis.crypto.wallet.csv.CSVGenerator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements ApplicationRunner {

	private CSVGenerator generator;

	public App(CSVGenerator generator) {
		this.generator = generator;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Used to create big-wallet csv file for testing purpose
		//generator.generate();
	}
}
