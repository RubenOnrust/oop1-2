package domain.io.xml;

import domain.io.IProofStore;
import domain.io.xml.AbstractProofStoreXML;

public class RandomProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new RandomProofStoreXML();
		}
		return store;
	}
	
	private RandomProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Random";
	}
}