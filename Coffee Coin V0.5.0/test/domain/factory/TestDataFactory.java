package domain.factory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import domain.Account;
import domain.Asset;
import miner.IMiner;
import miner.hash.SHA512Miner;
import miner.kconstellation.HexapletMiner;
import miner.kconstellation.PentapletMiner;

public class TestDataFactory {

	public static List<Account> getTestAccounts() {
		List<Account> result = new ArrayList<>();
		result.add(new Account("Donald Trump", "Donald Trump","Current president of the United States of America"));
		result.add(new Account("Kim Jong-Un", "Kim Jong-Un","Dictator-president of North Korea"));
		result.add(new Account("Boris Johnson", "Boris Johnson","Prime Minister of the Divided Kingdom"));
		result.add(new Account("Vladimir Putin", "Vladimir Putin","Big Boss in Russia"));
		result.add(new Account("Victor Orban", "Victor Orban","Chairmain of the Ungarian government"));
		result.add(new Account("Dagobert Duck", "Dagobert Duck","Waterbird with lots of money"));
		result.add(new Account("Hunky the Junky", "Hunky the Junky","Needs money for his narcotics"));
		return result;
	}
	
	public static List<IMiner> getTestMiners() throws NoSuchAlgorithmException, IOException {
		List<IMiner> result = new ArrayList<>();
		result.add(new PentapletMiner(Asset.getDefault(), AccountFactory.instance().get(0)));
		result.add(new HexapletMiner(Asset.getDefault(), AccountFactory.instance().get(1)));
		result.add(new SHA512Miner(Asset.getDefault(), AccountFactory.instance().get(6)));
    	return result;
	}
}
