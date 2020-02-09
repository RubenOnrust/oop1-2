package domain.proof;

public class IllegalProofException extends RuntimeException {
	private static final long serialVersionUID = 1490549809776619545L;

	public IllegalProofException() {
		super();
	}

	public IllegalProofException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalProofException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalProofException(String message) {
		super(message);
	}

	public IllegalProofException(Throwable cause) {
		super(cause);
	}

}
