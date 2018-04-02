package hippodrome.actions;

import static hippodrome.actions.RaceConfiguration.TRACK_DISTANCE_MAX_BOUND;
import static hippodrome.actions.RaceConfiguration.TRACK_DISTANCE_MIN_BOUND;

/**
 * Representation of a race, which is done over a {@link hippodrome.RacingTrack}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 1.0
 */
public class Race {
    /**
     * Constructs a race with {@code numberOfTracks} number of tracks and identification {@code identification}.
     *
     * @param numberOfTracks the number of tracks to be used on this race.
     * @param identification the race number identification.
     * @param distance the distance of the race.
     */
    public Race(int numberOfTracks, int identification, int distance) {
        this.numberOfTracks = numberOfTracks;
        this.identification = identification;
        this.distance = distance;
    }

    /**
     * Generate a distance for the race.
     *
     * @return the distance of a race, from 500 to 1500
     */
    public static int generateDistance() {
        return (int)(Math.random() * (TRACK_DISTANCE_MAX_BOUND - TRACK_DISTANCE_MIN_BOUND)) + TRACK_DISTANCE_MIN_BOUND;
    }

    /**
     * Gets the identification of this race.
     * <br>
     * This value is usually read as the number of this race).
     *
     * @return an integer value representing the identification of this race.
     */
    public int getIdentification() {
        return identification;
    }

    /**
     * Gets the number of tracks which this race has.
     * <br>
     * Usually, this number of tracks is equal to the number of horses attending a race, but this should not be
     * interpreted as a global rule.
     *
     * @return the number of tracks of this race.
     */
    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    /**
     * Gets the distance of this track's race.
     *
     * @return the distance of this race.
     */
    public int getDistance() {
        return distance;
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
}
