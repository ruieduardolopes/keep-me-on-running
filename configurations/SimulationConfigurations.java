package configurations;

/**
 * Definition of the configurations for the simulation (also known as server).
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class SimulationConfigurations {
    /**
     * Number of Pairs Horse/Jockey to compete on the simulation.
     */
    public static final int NUMBER_OF_PAIRS_HORSE_JOCKEY = 4;

    /**
     * Number of Spectators to attend these events.
     */
    public static final int NUMBER_OF_SPECTATORS = 4;

    /**
     * Number of races of a simulated event.
     */
    public static final int NUMBER_OF_RACES = 5;

    /**
     * Number of tracks on the racing track.
     */
    public static final int NUMBER_OF_TRACKS = 4;

    /**
     * Maximum amount of money a Spectator could have.
     */
    public static final int MONEY_MAX_BOUND = 999;

    /**
     * Minimum amount of money a Spectator could have.
     */
    public static final int MONEY_MIN_BOUND = 1;

    /**
     * Maximum ability of the pairs Horse/Jockey.
     */
    public static final int ABILITY_MAX_BOUND = 5;

    /**
     * Minimum ability of the pairs Horse/Jockey.
     */
    public static final int ABILITY_MIN_BOUND = 1;

    /**
     * Flag to control if user wants to have colored information about the system's status on the {@code stdout} and
     * simple one on file (behavior activated if {@code false}); otherwise, with {@code true}, only on file, without
     * colors.
     */
    public static final boolean ONLY_LOG_ON_FILE = true;

    /**
     * Maximum distance of a race track.
     */
    public static final int TRACK_DISTANCE_MAX_BOUND = 100;

    /**
     * Minimum distance of a race track.
     */
    public static final int TRACK_DISTANCE_MIN_BOUND = 50;
}
