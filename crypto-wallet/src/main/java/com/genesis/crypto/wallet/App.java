package com.genesis.crypto.wallet;

import com.genesis.crypto.wallet.core.WalletProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements ApplicationRunner {

	private WalletProcessor manager;

	public App(WalletProcessor manager) {
		this.manager = manager;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		manager.doStuff();
	}
}
