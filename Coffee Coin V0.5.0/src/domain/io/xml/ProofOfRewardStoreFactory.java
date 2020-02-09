package domain.io.xml;

import domain.io.IProofStore;
import domain.proof.hash.SHA512Proof;
import domain.proof.primes.DecapletProof;
import domain.proof.primes.DodecapletProof;
import domain.proof.primes.HexapletProof;
import domain.proof.primes.NenapletProof;
import domain.proof.primes.OctapletProof;
import domain.proof.primes.PentapletProof;
import domain.proof.primes.SeptapletProof;
import domain.proof.primes.TridecapletProof;
import domain.proof.primes.UndecapletProof;
import proof.RandomProof;

public class ProofOfRewardStoreFactory {
	public static IProofStore get(Class<?> classOfProof) {
		if (classOfProof == PentapletProof.class) {
			return PentapletProofStoreXML.instance();
		}
		if (classOfProof == HexapletProof.class) {
			return HexapletProofStoreXML.instance();
		}
		if (classOfProof == SeptapletProof.class) {
			return SeptapletProofStoreXML.instance();
		}
		if (classOfProof == OctapletProof.class) {
			return OctapletProofStoreXML.instance();
		}
		if (classOfProof == NenapletProof.class) {
			return NenapletProofStoreXML.instance();
		}
		if (classOfProof == DecapletProof.class) {
			return DecapletProofStoreXML.instance();
		}
		if (classOfProof == UndecapletProof.class) {
			return UndecapletProofStoreXML.instance();
		}
		if (classOfProof == DodecapletProof.class) {
			return DodecapletProofStoreXML.instance();
		}
		if (classOfProof == TridecapletProof.class) {
			return TridecapletProofStoreXML.instance();
		}
		if (classOfProof == RandomProof.class) {
			return RandomProofStoreXML.instance();
		}
		if (classOfProof == SHA512Proof.class) {
			return SHA512ProofStoreXML.instance();
		}
		throw new IllegalStateException("Unknown proof type.");
	}
}
