package com.genesis.crypto.wallet;

import com.genesis.crypto.wallet.core.CoincapCaller;
import com.genesis.crypto.wallet.core.WalletManager;
import com.genesis.crypto.wallet.domain.coincap.CoincapAssetItem;
import com.genesis.crypto.wallet.domain.coincap.CoincapAssets;
import com.genesis.crypto.wallet.domain.coincap.CoincapHistory;
import com.genesis.crypto.wallet.domain.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class App implements ApplicationRunner {

	@Autowired
	private WalletManager walletManager;

	@Autowired
	private CoincapCaller coincapCaller;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// DADO: arquivo csv com suposta carteira
		List<CSVRecord> walletRecords = walletManager.getWalletRecords();

		// QUANDO: para cada registro, buscar na API externa seus valores e seu histórico
		for (CSVRecord record : walletRecords) {
			CoincapAssets assets = coincapCaller.getAssets();

			// Encontra o asset na lista
			CoincapAssetItem item = assets.getData().stream().filter(a -> a.getSymbol().equals(record.getSymbol())).findFirst().get();

			// Pega o historico
			CoincapHistory history = coincapCaller.getAssetHistoryById(item.getId());

			System.out.println(item.getMaxSupply());
		}

		// ENTAO: imprimir resultados
	}
}
