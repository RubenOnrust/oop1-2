package domain.io;

import java.util.List;

import domain.Chain;

public interface IChainStore {
	List<String> get(Chain chain); // Returns a List of strings, representing the chain

}
