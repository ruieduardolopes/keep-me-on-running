package hippodrome.actions;

/**
 * Representation of a race, which is done over a {@link hippodrome.RacingTrack}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 1.0
 */
public class Race {
    /**
     * Constructs a race with {@code numberOfTracks} number of tracks and identification {@code identification}.
     *
     * @param numberOfTracks the number of tracks to be used on this race.
     * @param identification the race number identification.
     */
    public Race(int numberOfTracks, int identification) {
        this.numberOfTracks = numberOfTracks;
        this.identification = identification;
    }

    /**
     * The number of tracks this race will have in use.
     */
    private int numberOfTracks;

    /**
     * This race identification number.
     */
    private int identification;
}
