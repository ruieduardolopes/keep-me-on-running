package hippodrome.actions;

/**
 * Representation of a race, which is done over a {@link hippodrome.RacingTrack}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 0.1
 */
public class Race {
    /**
     * Constructs a race with {@code numberOfTracks} number of tracks and identification {@code identification}.
     *
     * @param numberOfTracks the number of tracks to be used on this race.
     * @param identification the race number identification.
     */
    public Race(int numberOfTracks, int identification, int distance) {
        this.numberOfTracks = numberOfTracks;
        this.identification = identification;
        this.distance = distance;
        this.positionOnTrack = new int[numberOfTracks];
    }

    /**
     * The number of tracks this race will have in use.
     */
    private int numberOfTracks;

    /**
     * This race identification number.
     */
    private int identification;

    /**
     * This race distance.
     */
    private int distance;

    /**
     * Current position of pairs Horse/Jockey on track.
     */
    private int[] positionOnTrack;
}
