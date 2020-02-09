package domain.io.xml;

import domain.io.IProofStore;

public class SeptapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new SeptapletProofStoreXML();
		}
		return store;
	}
	
	private SeptapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Septaplet";
	}
}