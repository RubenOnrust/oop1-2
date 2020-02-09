package domain.io.xml;

import domain.Transaction;
import domain.io.ITransactionStore;

public class TransactionStoreXML implements ITransactionStore {
	private static ITransactionStore store;
	
	public static ITransactionStore instance() {
		if (store == null) {
			store = new TransactionStoreXML();
		}
		return store;
	}
	
	private TransactionStoreXML() {
		
	}

	@Override
	public String get(Transaction transaction) {
		StringBuilder result = new StringBuilder();
		result.append("<Transaction \thashPrevious=\"");
		if (transaction.getPreviousHash() != null) {
			result.append(transaction.getPreviousHash().toString(16));
		}
		else {
			result.append("NaN\"");
		}
		result.append("\n\t\thash=\"");
		result.append(transaction.getOwnHash().toString(16));
		result.append("\"\n\t\tamount=\"");
		result.append(transaction.getAmount());
		result.append("\"\n\t\tamountProcessing=\"");
		result.append(transaction.getAmountProcessing());
		result.append("\"\n\t\ttimestamp=\"");
		result.append(transaction.getDatetime());
		result.append("\">\n");
		result.append(lineAsset(AssetStoreFactory.instance().get().get(transaction.getAsset())));
		result.append(lineAccount(AccountStoreFactory.instance().get().get(transaction.getFrom())));
		result.append(lineAccount(AccountStoreFactory.instance().get().get(transaction.getTo())));
		result.append("</Transaction>\n");
		return new String(result);
	}
	
	private String lineAccount(String account) {
		StringBuilder result = new StringBuilder(account);
		result.insert(0, "\t\t");
		int index = result.indexOf("balance", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		return new String(result);
	}
	
	private String lineAsset(String asset) {
		StringBuilder result = new StringBuilder(asset);
		result.insert(0, "\t\t");
		int index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		index = result.indexOf("description", index);
		do  {
			result.insert(index, "\t\t\t");
			index +=4;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		index = result.indexOf("ticker", index);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("ticker", index);
		}
		while (index != -1);
		return new String(result);
	}
}
