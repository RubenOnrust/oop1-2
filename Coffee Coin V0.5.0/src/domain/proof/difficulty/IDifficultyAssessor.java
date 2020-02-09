package domain.proof.difficulty;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public interface IDifficultyAssessor {
	public int getDifficulty(Date moment) throws NoSuchAlgorithmException, IOException;
}
