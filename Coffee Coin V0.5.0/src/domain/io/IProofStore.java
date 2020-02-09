package domain.io;

import domain.proof.Proof;

public interface IProofStore {
	String get(Proof proof); // Returns a string representation of the proof
}
