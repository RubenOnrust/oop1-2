package miner.factory;

import static domain.Settings.Environment.DEVELOPMENT;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import domain.Account;
import domain.Asset;
import domain.Settings;
import domain.factory.TestDataFactory;
import miner.IMiner;
import miner.hash.SHA512Miner;
import miner.kconstellation.DecapletMiner;
import miner.kconstellation.DodecapletMiner;
import miner.kconstellation.HexapletMiner;
import miner.kconstellation.NenapletMiner;
import miner.kconstellation.OctapletMiner;
import miner.kconstellation.PentapletMiner;
import miner.kconstellation.SeptapletMiner;
import miner.kconstellation.TridecapletMiner;
import miner.kconstellation.UndecapletMiner;

public class MinerFactory {
	public static List<IMiner> getMiners(Account to) throws NoSuchAlgorithmException, IOException {
		List<IMiner> miners = new ArrayList<>();
		if (Settings.instance().getEnvironment() == DEVELOPMENT) {
			miners = TestDataFactory.getTestMiners();
		}
		else {
			Asset asset = new Asset("COF", "Coffee Coin","If the world runs on this, everyone would be active.");
			SortedSet<Long> values = new TreeSet<>();
			
			miners.add(new TridecapletMiner(asset, to));
			miners.add(new DodecapletMiner(asset, to));
			miners.add(new UndecapletMiner(asset, to));
			miners.add(new DecapletMiner(asset, to));
			miners.add(new NenapletMiner(asset, to));
			miners.add(new OctapletMiner(asset, to));
			miners.add(new SeptapletMiner(asset, to));
			miners.add(new HexapletMiner(asset, to));
			miners.add(new PentapletMiner(asset, to));
			miners.add(new SHA512Miner(asset, to));
			
			for (IMiner proofMiner: miners) {
				values.add(proofMiner.getExpectedProfit());
			}
			long max = values.last();
			for (IMiner miner: miners) {
				if (miner.getExpectedProfit() == max) {
					miners.add(miner);
				}
			}
		}
		// Cull the number of results to no more than the maximum number of miner threads
		List<IMiner> result = new ArrayList<>();
		int max = Settings.instance().getNumberOfMinerThreads();
		Iterator<IMiner> iterator = miners.iterator();
		while (iterator.hasNext() && max > 0) {
			result.add(iterator.next());
			max--;
		}
    	return result;
	}
}
