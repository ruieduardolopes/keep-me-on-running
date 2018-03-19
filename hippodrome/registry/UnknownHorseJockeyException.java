package hippodrome.registry;

/**
 * This exception exists to be thrown when an invalid pair Horse/Jockey
 * was indexed.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @version 1.0
 * @since 1.0
 */
public class UnknownHorseJockeyException extends RuntimeException {
    /**
     * This exception thrown with a specific {@link HorseJockey}'s {@code id}.
     *
     * @param id identification of the invalid {@link HorseJockey}.
     */
    public UnknownHorseJockeyException(int id) {
        super("An invalid spectator with ID " + id + " was indexed.");
    }
}
