package application;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import algo.kconstellation.AbstractKConstellationPrimer;
import algo.kconstellation.NenapletConstellation;
import domain.Account;
import domain.Asset;
import domain.Block;
import domain.Chain;
import domain.GenesisBlock;
import domain.RewardTransaction;
import domain.Transaction;
import domain.io.IChainStore;
import domain.io.xml.ChainStoreFactory;
import domain.proof.Proof;
import domain.proof.ProofsInitializer;
import domain.proof.primes.NenapletProof;

public class TestDomainToXML {

	public static void main(String[] args) throws Exception {
		// Create some accounts to work with
		Map<String, Account> accounts = new HashMap<>();
		accounts.put("Account to fill with lots of Coffee", new Account("Account to fill with lots of Coffee", "Coffee Machine", null));
		accounts.put("System bank", Account.getAssetBitBucket());
		accounts.put("Funds for designing new spaceship", new Account("Funds for designing new spaceship", "SpaceX account",null));
		accounts.put("Nigerian prince with too much money", new Account("Nigerian prince with too much money", "Totally not a scam",null));
		
		// Asset
		Asset asset = Asset.getDefault();
		
		// A GenesisRewardTransaction
		SortedSet<Proof> genesisProofs = ProofsInitializer.instance(asset).getProofs();
		
		// A GenesisBlock
		GenesisBlock genesisBlock = new GenesisBlock(genesisProofs);
		
		// Simulating mining by setting up nenapletprimer and tripletprimer
		AbstractKConstellationPrimer nenapletPrimer = new NenapletConstellation();
		try {
			nenapletPrimer.getNextKPrime(new BigInteger("17"));
			nenapletPrimer.getNextKPrime();
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
				
		// A proof using a nenaplet
		nenapletPrimer.getNextKPrime();
		NenapletProof proof = new NenapletProof(nenapletPrimer.getHighestKPrime(), asset);
			
		// A RewardTransaction
		RewardTransaction rewardTransaction = new RewardTransaction(proof, accounts.get("Account to fill with lots of Coffee"));
			
		// A Block. After creating the block, transactions can be added
		Block block = new Block(genesisBlock, rewardTransaction);
		
		// A few regular transactions
		block.add(new Transaction(asset, BigInteger.ZERO, accounts.get("Account to fill with lots of Coffee"), accounts.get("System bank"), BigInteger.ZERO));
		block.add(new Transaction(asset, BigInteger.ZERO, accounts.get("Account to fill with lots of Coffee"), accounts.get("Funds for designing new spaceship"), BigInteger.ZERO));
		block.add(new Transaction(asset, BigInteger.ZERO, accounts.get("Account to fill with lots of Coffee"), accounts.get("Nigerian prince with too much money"), BigInteger.ZERO));
		
		// A Chain
		Chain chain = Chain.instance(asset);
		chain.add(block);
		
		// Generate some more blocks and add them to the chain
		nenapletPrimer.getNextKPrime();
		RewardTransaction aNewRewardTransaction = new RewardTransaction(
											new NenapletProof(nenapletPrimer.getHighestKPrime(), asset), 
											accounts.get("Account to fill with lots of Coffee"));
	
		Block block2 = new Block(block, aNewRewardTransaction);
		chain.add(block2);
				
		// Show the chain as XML
		IChainStore chainStore = ChainStoreFactory.instance().get();
		List<String> xmlList = chainStore.get(chain);
		for (String s: xmlList) {
			System.out.println(s);
		}
	}
}
