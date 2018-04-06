package hippodrome.rollfilm;

/**
 * This exception exists to be thrown when a file cannot be accessed with
 * the current permissions (by the current user) or something's gone wrong
 * manipulating it.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @version 1.0
 * @since 0.1
 */
public class InaccessibleFileException extends RuntimeException {
    /**
     * A meaningful and default exception followed by a simple message.
     */
    public InaccessibleFileException() {
        this("This file is inaccessible.");
    }

    /**
     * This exception thrown with a specific {@code message}.
     *
     * @param message what is to report.
     */
    public InaccessibleFileException(String message) {
        super(message);
    }
}
