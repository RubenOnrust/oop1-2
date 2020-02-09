package util;

public class IllegalParameterException extends RuntimeException {
	private static final long serialVersionUID = 1490549809776619545L;

	public IllegalParameterException() {
		super();
	}

	public IllegalParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalParameterException(String message) {
		super(message);
	}

	public IllegalParameterException(Throwable cause) {
		super(cause);
	}

}
