package domain.io.xml;

import java.util.Iterator;
import java.util.List;

import domain.Block;
import domain.MessageTransaction;
import domain.RewardTransaction;
import domain.Transaction;
import domain.io.IBlockStore;

public class BlockStoreXML implements IBlockStore {
	private static IBlockStore store;
	
	public static IBlockStore instance() {
		if (store == null) {
			store = new BlockStoreXML();
		}
		return store;
	}
	
	private BlockStoreXML() {
		
	}

	@Override
	public String get(Block block) {
		StringBuilder result = new StringBuilder();
		result.append("<Block \thashPrevious=\"");
		if (block.getHashPrevious() != null) {
			result.append(block.getHashPrevious().toString(16));
		}
		else {
			result.append("NaN\"");
		}
		result.append("\"\n\thash=\"");		
		result.append(block.getMerkleRoot().toString(16));
		result.append("\"\n\ttimestamp=\"");
		result.append(block.getTimestamp());
		result.append("\">\n");
		
		// First transaction is the RewardTransaction
		List<Transaction> transactions = block.getTransactions();	
		Iterator<Transaction> i = transactions.iterator();
		RewardTransaction rewardTransaction = (RewardTransaction) i.next();				
		result.append(lineRewardTransaction(RewardTransactionStoreFactory.instance().get().get(rewardTransaction)));

		// The rest of the list are other transactions; usually general transfers, but can be others like messages
		while (i.hasNext() ) {
			Transaction transaction = i.next();
			if (transaction.getClass().equals(MessageTransaction.class)) {
				result.append(lineMessageTransaction(MessageTransactionStoreFactory.instance().get().get((MessageTransaction) transaction)));
			}
			else {
				result.append(lineTransaction(TransactionStoreFactory.instance().get().get(transaction)));
			}
		}
		result.append("</Block>\n");
		return new String(result);
	}
	
	private String lineRewardTransaction(String rewardTransaction) {
		StringBuilder result = new StringBuilder(rewardTransaction);
		int index = result.indexOf("hash=", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("hash=", index);
		}
		while (index != -1);
		index = result.indexOf("amount", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("amount", index);
		}
		while (index != -1);
		index = result.indexOf("timestamp", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("timestamp", index);
		}
		while (index != -1);
		index = result.indexOf("<Asset", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("<Asset", index);
		}
		while (index != -1);
		index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		index = result.indexOf("description", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		index = result.indexOf("<Account", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("<Account", index);
		}
		while (index != -1);
		index = result.indexOf("balance", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("balance", index);
		}
		while (index != -1);
		index = result.indexOf("<" + XMLUtil.getProofTag(rewardTransaction), 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("<" + XMLUtil.getProofTag(rewardTransaction), index);
		}
		while (index != -1);
		index = result.indexOf("primes", 0);
		if (index != -1) {
			do  {
				result.insert(index, "\t\t\t");
				index +=4;
				index = result.indexOf("primes", index);
			}
			while (index != -1);
		}
		index = result.indexOf("value", 0);
		if (index != -1) {
			do  {
				result.insert(index, "\t\t\t");
				index +=4;
				index = result.indexOf("value", index);
			}
			while (index != -1);
		}		
		index = result.indexOf("chance", 0);
		if (index != -1) {
			do  {
				result.insert(index, "\t\t\t");
				index +=4;
				index = result.indexOf("chance", index);
			}
			while (index != -1);
		}
		index = result.indexOf("<Reward", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("<Reward", index);
		}
		while (index != -1);
		index = result.indexOf("</Reward", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("</Reward", index);
		}
		while (index != -1);
		index = result.indexOf("</" + XMLUtil.getProofTag(rewardTransaction) + ">", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("</" + XMLUtil.getProofTag(rewardTransaction) + ">", index);
		}
		while (index != -1);
		index = result.indexOf("<" + XMLUtil.getProofTag(new String(result)) + ">", 0);
		index = result.indexOf("\t", index);
		result.deleteCharAt(index);
		index = result.indexOf("<Reward ", 0);
		index = result.indexOf("\t", index);
		result.deleteCharAt(index);
		return new String(result);
	}
	
	private String lineTransaction(String transaction) {
		StringBuilder result = new StringBuilder(transaction);
		result.insert(0, "\t");
		int index = result.indexOf("hash=", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("hash=", index);
		}
		while (index != -1);
		index = result.indexOf("amount", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("amount", index);
		}
		while (index != -1);
		
		index = result.indexOf("timestamp", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("timestamp", index);
		}
		while (index != -1);
		index = result.indexOf("<Asset", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("<Asset", index);
		}
		while (index != -1);
		index = result.indexOf("<Account", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("<Account", index);
		}
		while (index != -1);
		
		index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		
		index = result.indexOf("description", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		
		index = result.indexOf("balance", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("balance", index);
		}
		while (index != -1);
		
		index = result.indexOf("</Transaction", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("</Transaction", index);
		}
		while (index != -1);
		return new String(result);
	}
	
	private String lineMessageTransaction(String transaction) {
		StringBuilder result = new StringBuilder(transaction);
		result.insert(0, "\t");
		int index = result.indexOf("hash=", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("hash=", index);
		}
		while (index != -1);
		index = result.indexOf("amount", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("amount", index);
		}
		while (index != -1);
		
		index = result.indexOf("timestamp", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("timestamp", index);
		}
		while (index != -1);
		
		index = result.indexOf("message", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("message", index);
		}
		while (index != -1);
		
		index = result.indexOf("<Asset", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("<Asset", index);
		}
		while (index != -1);
		index = result.indexOf("<Account", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("<Account", index);
		}
		while (index != -1);
		
		index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		
		index = result.indexOf("description", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		
		index = result.indexOf("balance", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("balance", index);
		}
		while (index != -1);
		
		index = result.indexOf("</MessageTransaction", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("</MessageTransaction", index);
		}
		while (index != -1);
		return new String(result);
	}
}
