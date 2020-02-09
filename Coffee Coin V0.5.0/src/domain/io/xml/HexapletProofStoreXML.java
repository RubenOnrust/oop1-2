package domain.io.xml;

import domain.io.IProofStore;

public class HexapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new HexapletProofStoreXML();
		}
		return store;
	}
	
	private HexapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Hexaplet";
	}
}