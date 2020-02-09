package domain.io.xml;

import domain.io.IGenesisRewardTransactionStore;

public class GenesisRewardTransactionStoreFactory {
	private static GenesisRewardTransactionStoreFactory factory = null;

	
	private GenesisRewardTransactionStoreFactory() {

	}
	
	public static GenesisRewardTransactionStoreFactory instance() {
		if (factory == null) {
			factory = new GenesisRewardTransactionStoreFactory();
		}
		return factory;
	}
	
	public IGenesisRewardTransactionStore get() {
		return GenesisRewardTransactionStoreXML.instance();
	}
}
