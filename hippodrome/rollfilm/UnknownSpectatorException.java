package hippodrome.rollfilm;

/**
 * This exception exists to be thrown when an invalid spectator was indexed.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @version 1.1
 * @since 0.1
 */
public class UnknownSpectatorException extends RuntimeException {
    /**
     * This exception thrown with a specific {@link Spectator}'s {@code id}.
     *
     * @param id identification of the invalid {@link Spectator}.
     */
    public UnknownSpectatorException(int id) {
        super("An invalid spectator with ID " + id + " was indexed.");
    }
}
