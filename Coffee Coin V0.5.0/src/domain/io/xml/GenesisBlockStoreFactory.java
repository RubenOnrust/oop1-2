package domain.io.xml;

import domain.io.IGenesisBlockStore;

public class GenesisBlockStoreFactory {
	private static GenesisBlockStoreFactory factory = null;

	
	private GenesisBlockStoreFactory() {

	}
	
	public static GenesisBlockStoreFactory instance() {
		if (factory == null) {
			factory = new GenesisBlockStoreFactory();
		}
		return factory;
	}
	
	public IGenesisBlockStore get() {
		return GenesisBlockStoreXML.instance();
	}
}
