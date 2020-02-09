package domain.io;

import domain.Block;

public interface IBlockStore {
	String get(Block block); // Returns a string representation of the block

}
