package domain.io.xml;

import domain.io.IProofStore;

public class SHA512ProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new SHA512ProofStoreXML();
		}
		return store;
	}
	
	private SHA512ProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "SHA512";
	}
}