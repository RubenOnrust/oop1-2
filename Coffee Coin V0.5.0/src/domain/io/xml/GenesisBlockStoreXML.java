package domain.io.xml;

import java.util.Iterator;
import java.util.List;

import domain.GenesisBlock;
import domain.GenesisRewardTransaction;
import domain.Transaction;
import domain.io.IGenesisBlockStore;
import domain.io.IGenesisRewardTransactionStore;

public class GenesisBlockStoreXML implements IGenesisBlockStore {
	private static IGenesisBlockStore store;
	
	public static IGenesisBlockStore instance() {
		if (store == null) {
			store = new GenesisBlockStoreXML();
		}
		return store;
	}
	
	private GenesisBlockStoreXML() {
		
	}

	@Override
	public String get(GenesisBlock block) {
		StringBuilder result = new StringBuilder();
		result.append("<GenesisBlock \thashPrevious=\"");
		if (block.getHashPrevious() != null) {
			result.append(block.getHashPrevious().toString(16));
		}
		else {		
			result.append("NaN\"");
		}
		result.append("\"\n\t\thash=\"");
		result.append(block.getMerkleRoot().toString(16));
		result.append("\"\n\t\ttimestamp=\"");
		result.append(block.getTimestamp());
		result.append("\">\n");

		// A genesis block does not have regular transactions. It does however have a list of already known Proof of Rewards
		List<Transaction> transactions = block.getTransactions();
		Iterator<Transaction> i = transactions.iterator();
		GenesisRewardTransaction genesisRewardTransaction = (GenesisRewardTransaction) i.next();	
		IGenesisRewardTransactionStore genesisRewardTransactionStore = GenesisRewardTransactionStoreFactory.instance().get();
		result.append(lineGenesisRewardTransaction(genesisRewardTransactionStore.get(genesisRewardTransaction)));

		result.append("</GenesisBlock>\n");
		return new String(result);
	}
	
	private String lineGenesisRewardTransaction(String genesisRewardTransaction) {
		StringBuilder result = new StringBuilder(genesisRewardTransaction);
		result.insert(0, "\t\t");
		int index = result.indexOf("hash=", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("hash=", index);
		}
		while (index != -1);
		index = result.indexOf("amount", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("amount", index);
		}
		while (index != -1);
		index = result.indexOf("timestamp", 0);
		do  {
			result.insert(index, "\t\t");
			index +=5;
			index = result.indexOf("timestamp", index);
		}
		while (index != -1);
		index = result.indexOf("<Asset", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("<Asset", index);
		}
		while (index != -1);
		index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		index = result.indexOf("description", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		index = result.indexOf("<Account", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("<Account", index);
		}
		while (index != -1);
		index = result.indexOf("balance", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("balance", index);
		}
		while (index != -1);
		index = result.indexOf("<" + XMLUtil.getProofTag(genesisRewardTransaction), 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("<" + XMLUtil.getProofTag(genesisRewardTransaction), index);
		}
		while (index != -1);
		index = result.indexOf("primes", 0);
		do  {
			result.insert(index, "\t\t\t");
			index +=6;
			index = result.indexOf("primes", index);
		}
		while (index != -1);
		index = result.indexOf("<Reward", 0);
		do  {
			result.insert(index, "\t\t\t");
			index +=6;
			index = result.indexOf("<Reward", index);
		}
		while (index != -1);
		index = result.indexOf("</Reward", 0);
		do  {
			result.insert(index, "\t\t\t");
			index +=6;
			index = result.indexOf("</Reward", index);
		}
		while (index != -1);
		index = result.indexOf("</" + XMLUtil.getProofTag(genesisRewardTransaction) + ">", 0);
		do  {
			result.insert(index, "\t\t");
			index +=6;
			index = result.indexOf("</" + XMLUtil.getProofTag(genesisRewardTransaction) + ">", index);
		}
		while (index != -1);
		index = result.indexOf("</GenesisRewardTransaction>", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("</GenesisRewardTransaction>", index);
		}
		while (index != -1);
		index = result.indexOf("<" + XMLUtil.getProofTag(new String(result)), 0);
		index = result.indexOf("\t", index);
		result.deleteCharAt(index);
		index = result.indexOf("<Reward ", 0);
		index = result.indexOf("\t", index);
		result.deleteCharAt(index);
		return new String(result);
	}
}
