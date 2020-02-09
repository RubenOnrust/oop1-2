package domain.io.xml;

import domain.RewardTransaction;
import domain.io.IProofStore;
import domain.io.IRewardTransactionStore;
import domain.proof.Proof;

public class RewardTransactionStoreXML implements IRewardTransactionStore {
	private static IRewardTransactionStore store;
	
	public static IRewardTransactionStore instance() {
		if (store == null) {
			store = new RewardTransactionStoreXML();
		}
		return store;
	}
	
	private RewardTransactionStoreXML() {
		
	}

	@Override
	public String get(RewardTransaction transaction) {
		StringBuilder result = new StringBuilder();
		result.append("<RewardTransaction \thashPrevious=\"");
		if (transaction.getPreviousHash() != null) {
			result.append(transaction.getPreviousHash().toString(16));
		}
		else {
			result.append("NaN\"");
		}
		result.append("\n\t\t\thash=\"");
		result.append(transaction.getOwnHash().toString(16));
		result.append("\"\n\t\t\tamount=\"");
		result.append(transaction.getAmount());
		result.append("\"\n\t\t\tamountProcessing=\"");
		result.append(transaction.getAmountProcessing());
		result.append("\"\n\t\t\ttimestamp=\"");
		result.append(transaction.getDatetime());
		result.append("\">\n");
		result.append(lineAsset(AssetStoreFactory.instance().get().get(transaction.getAsset())));
		result.append(lineAccount(AccountStoreFactory.instance().get().get(transaction.getFrom())));
		result.append(lineAccount(AccountStoreFactory.instance().get().get(transaction.getTo())));
		
		// Process the IProofOfReward
		Proof proof = transaction.getProof();

		IProofStore proofStore = ProofOfRewardStoreFactory.get(proof.getClass());
		result.append(lineProof(proofStore.get(proof)));
		
		result.append("</RewardTransaction>\n");
		return new String(result);
	}
	
	private String lineAccount(String account) {
		StringBuilder result = new StringBuilder(account);
		result.insert(0, "\t\t\t");
		int index = result.indexOf("balance", 0);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		return new String(result);
	}
	
	private String lineAsset(String asset) {
		StringBuilder result = new StringBuilder(asset);
		result.insert(0, "\t\t\t");
		int index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=6;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		index = result.indexOf("ticker", index);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("ticker", index);
		}
		while (index != -1);
		index = result.indexOf("description", index);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=6;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		return new String(result);
	}
	
	private String lineProof(String proof) {
		StringBuilder result = new StringBuilder(proof);
		result.insert(0, "\t\t\t");
		int index = result.indexOf("primes", 0);
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
		index = result.indexOf("</" + XMLUtil.getProofTag(proof) + ">", index);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("</" + XMLUtil.getProofTag(proof) + ">", index);
		}
		while (index != -1);
		index = result.indexOf("<" + XMLUtil.getProofTag(proof), index);
		do  {
			result.insert(index, "");
			index +=4;
			index = result.indexOf("<" + XMLUtil.getProofTag(proof), index);
		}
		while (index != -1);
		index = result.indexOf("<Reward", index);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("<Reward", index);
		}
		while (index != -1);
		index = result.indexOf("</Reward>", index);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("</Reward>", index);
		}
		while (index != -1);
		index = result.indexOf("<Asset", index);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("<Asset", index);
		}
		while (index != -1);
		index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		index = result.indexOf("description", 0);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		return new String(result);
	}
}
