package domain.io.xml;

import domain.io.IRewardTransactionStore;

public class RewardTransactionStoreFactory {
	private static RewardTransactionStoreFactory factory = null;

	
	private RewardTransactionStoreFactory() {

	}
	
	public static RewardTransactionStoreFactory instance() {
		if (factory == null) {
			factory = new RewardTransactionStoreFactory();
		}
		return factory;
	}
	
	public IRewardTransactionStore get() {
		return RewardTransactionStoreXML.instance();
	}
}
