package domain.io.xml;

import java.util.List;

import domain.GenesisRewardTransaction;
import domain.io.IGenesisRewardTransactionStore;
import domain.io.IProofStore;
import domain.proof.Proof;

public class GenesisRewardTransactionStoreXML implements IGenesisRewardTransactionStore {
	private static IGenesisRewardTransactionStore store;
	
	public static IGenesisRewardTransactionStore instance() {
		if (store == null) {
			store = new GenesisRewardTransactionStoreXML();
		}
		return store;
	}
	
	private GenesisRewardTransactionStoreXML() {
		
	}

	@Override
	public String get(GenesisRewardTransaction transaction) {
		StringBuilder result = new StringBuilder();
		result.append("<GenesisRewardTransaction \thashPrevious=\"");
		if (transaction.getPreviousHash() != null) {
			result.append(transaction.getPreviousHash().toString(16));
		}
		else {
			result.append("NaN\"");
		}
		result.append("\n\t\t\t\thash=\"");
		result.append(transaction.getOwnHash().toString(16));
		result.append("\"\n\t\t\t\tamount=\"");
		result.append(transaction.getAmount());
		result.append("\"\n\t\t\t\tamountProcessing=\"");
		result.append(transaction.getAmountProcessing());
		result.append("\"\n\t\t\t\ttimestamp=\"");
		result.append(transaction.getDatetime());
		result.append("\">\n");
		result.append(lineAsset(AssetStoreFactory.instance().get().get(transaction.getAsset())));
		result.append(lineAccount(AccountStoreFactory.instance().get().get(transaction.getFrom())));
		result.append(lineAccount(AccountStoreFactory.instance().get().get(transaction.getTo())));
		
		// Process the list of proofs specific for a Genesis block
		List<Proof> proofs = transaction.getProofs();
		for (Proof genesisProof: proofs) {
			IProofStore proofStore = ProofOfRewardStoreFactory.get(genesisProof.getClass());
			result.append(lineProof(proofStore.get(genesisProof)));
		}
		
		result.append("</GenesisRewardTransaction>\n");
		return new String(result);
	}
	
	private String lineAccount(String account) {
		StringBuilder result = new StringBuilder(account);
		result.insert(0, "\t\t\t\t");
		int index = result.indexOf("balance", 0);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=8;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		return new String(result);
	}
	
	private String lineAsset(String asset) {
		StringBuilder result = new StringBuilder(asset);
		result.insert(0, "\t\t\t\t");
		int index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t\t\t\t\t");
			index +=8;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		index = result.indexOf("ticker", index);
		do  {
			result.insert(index, "\t");
			index +=3;
			index = result.indexOf("ticker", index);
		}
		while (index != -1);
		index = result.indexOf("description", index);
		do  {
			result.insert(index, "\t\t\t\t\t");
			index +=8;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		return new String(result);
	}
	
	private String lineProof(String proof) {
		StringBuilder result = new StringBuilder(proof);
		result.insert(0, "\t\t\t\t");
		int index = result.indexOf("primes", 0);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=8;
			index = result.indexOf("primes", index);
		}
		
		while (index != -1);
		
		index = result.indexOf("<" + XMLUtil.getProofTag(proof), index);
		do  {
			result.insert(index, "");
			index +=8;
			index = result.indexOf("<" + XMLUtil.getProofTag(proof), index);
		}
		while (index != -1);
		
		index = result.indexOf("</" + XMLUtil.getProofTag(proof) + ">", index);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=8;
			index = result.indexOf("</" + XMLUtil.getProofTag(proof) + ">", index);
		}
		while (index != -1);
	
		index = result.indexOf("<Reward", index);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=8;
			index = result.indexOf("<Reward", index);
		}
		while (index != -1);
		
		index = result.indexOf("</Reward>", index);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=8;
			index = result.indexOf("</Reward>", index);
		}
		while (index != -1);
		
		index = result.indexOf("<Asset", index);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=8;
			index = result.indexOf("<Asset", index);
		}
		while (index != -1);
		
		index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=8;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		
		index = result.indexOf("description", 0);
		do  {
			result.insert(index, "\t\t\t\t");
			index +=8;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		
		return new String(result);
	}
}
