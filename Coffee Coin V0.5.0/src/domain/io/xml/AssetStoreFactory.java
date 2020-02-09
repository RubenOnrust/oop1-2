package domain.io.xml;

import domain.io.IAssetStore;

public class AssetStoreFactory {
	private static AssetStoreFactory factory = null;

	
	private AssetStoreFactory() {

	}
	
	public static AssetStoreFactory instance() {
		if (factory == null) {
			factory = new AssetStoreFactory();
		}
		return factory;
	}
	
	public IAssetStore get() {
		return AssetStoreXML.instance();
	}
}
