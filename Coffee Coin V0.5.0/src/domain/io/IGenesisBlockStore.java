package domain.io;

import domain.GenesisBlock;

public interface IGenesisBlockStore {
	String get(GenesisBlock block); // Returns a string representation of the block

}
