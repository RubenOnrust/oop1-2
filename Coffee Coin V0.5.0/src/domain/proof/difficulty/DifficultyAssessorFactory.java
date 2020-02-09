package domain.proof.difficulty;

import domain.proof.hash.SHA512Proof;
import util.IllegalParameterException;

public class DifficultyAssessorFactory {
	
	public static IDifficultyAssessor getAssessor(Class<?> type) {
		if (type.equals(SHA512Proof.class)) {
			return new SHA512DifficultyAssessor();
		}
		throw new IllegalParameterException("Unknown PoW algorithm: " + type.getSimpleName());
	}
}
