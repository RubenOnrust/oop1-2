package domain.io.xml;

import domain.io.IProofStore;

public class UndecapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new UndecapletProofStoreXML();
		}
		return store;
	}
	
	private UndecapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Undecaplet";
	}
}