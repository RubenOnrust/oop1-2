package domain.io.xml;

import domain.io.IChainStore;

public class ChainStoreFactory {
	private static ChainStoreFactory factory = null;

	
	private ChainStoreFactory() {

	}
	
	public static ChainStoreFactory instance() {
		if (factory == null) {
			factory = new ChainStoreFactory();
		}
		return factory;
	}
	
	public IChainStore get() {
		return ChainStoreXML.instance();
	}
}
