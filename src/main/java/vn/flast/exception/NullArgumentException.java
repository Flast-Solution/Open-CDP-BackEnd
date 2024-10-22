package vn.flast.exception;

import java.io.Serial;

public class NullArgumentException extends IllegalArgumentException {

    /**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 1L;

	public NullArgumentException() {
        super("The argument can't be null");
    }
    
    public NullArgumentException(String argumentName) {
        super("The \"" + argumentName + "\" argument can't be null");
    }

    public NullArgumentException(String argumentName, String details) {
        super("The \"" + argumentName + "\" argument can't be null. " + details);
    }
    
    /**
     * Convenience method to protect against a {@code null} argument.
     */
    public static void check(String argumentName, Object argumentValue) {
        if (argumentValue == null) {
            throw new NullArgumentException(argumentName);
        }
    }

    public static void check(Object argumentValue) {
        if (argumentValue == null) {
            throw new NullArgumentException();
        }
    }
}