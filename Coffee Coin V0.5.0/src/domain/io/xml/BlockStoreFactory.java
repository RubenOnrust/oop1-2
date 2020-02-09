package domain.io.xml;

import domain.io.IBlockStore;

public class BlockStoreFactory {
	private static BlockStoreFactory factory = null;

	
	private BlockStoreFactory() {

	}
	
	public static BlockStoreFactory instance() {
		if (factory == null) {
			factory = new BlockStoreFactory();
		}
		return factory;
	}
	
	public IBlockStore get() {
		return BlockStoreXML.instance();
	}
}
