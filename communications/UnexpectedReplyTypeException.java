package communications;

/**
 * This exception exists to be thrown when a file cannot be accessed with
 * the current permissions (by the current user) or something's gone wrong
 * manipulating it.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @version 1.1
 * @since 0.1
 */
public class UnexpectedReplyTypeException extends RuntimeException {
    /**
     * A meaningful and default exception followed by a simple message.
     */
    public UnexpectedReplyTypeException(MessageType type) {
        this(String.format("This type of reply message was unexpected: %s", type));
    }

    /**
     * This exception thrown with a specific {@code message}.
     *
     * @param message what is to report.
     */
    public UnexpectedReplyTypeException(String message) {
        super(message);
    }
}
