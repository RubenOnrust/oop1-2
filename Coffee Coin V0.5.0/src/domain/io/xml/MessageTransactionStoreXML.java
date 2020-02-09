package domain.io.xml;

import domain.MessageTransaction;
import domain.io.IMessageTransactionStore;

public class MessageTransactionStoreXML implements IMessageTransactionStore {
	private static IMessageTransactionStore store;
	
	public static IMessageTransactionStore instance() {
		if (store == null) {
			store = new MessageTransactionStoreXML();
		}
		return store;
	}
	
	private MessageTransactionStoreXML() {
		
	}

	@Override
	public String get(MessageTransaction messageTransaction) {
		StringBuilder result = new StringBuilder();
		result.append("<MessageTransaction \thashPrevious=\"");
		if (messageTransaction.getPreviousHash() != null) {
			result.append(messageTransaction.getPreviousHash().toString(16));
		}
		else {
			result.append("NaN\"");
		}
		result.append("\n\t\thash=\"");
		result.append(messageTransaction.getOwnHash().toString(16));
		result.append("\"\n\t\tamount=\"");
		result.append(messageTransaction.getAmount());
		result.append("\"\n\t\tamountProcessing=\"");
		result.append(messageTransaction.getAmountProcessing());
		result.append("\"\n\t\ttimestamp=\"");
		result.append(messageTransaction.getDatetime());
		result.append("\"\n\t\tmessage=\"");
		result.append(messageTransaction.getMessage());
		
		
		result.append("\">\n");
		result.append(lineAsset(AssetStoreFactory.instance().get().get(messageTransaction.getAsset())));
		result.append(lineAccount(AccountStoreFactory.instance().get().get(messageTransaction.getFrom())));
		result.append(lineAccount(AccountStoreFactory.instance().get().get(messageTransaction.getTo())));
		result.append("</MessageTransaction>\n");
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
