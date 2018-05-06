package communications;

/**
 * This exception exists to be thrown when a reply cannot be recognized.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @version 2.0
 * @since 2.0
 */
public class UnexpectedReplyTypeException extends RuntimeException {
    /**
     * A meaningful and default exception followed by a simple message.
     * @param type the Message Type which has not been identified.
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
