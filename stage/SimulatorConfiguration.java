package stage;

/**
 * This class pretends to deliver some constants already defined to change the
 * behaviors of the {@link Simulator} which happens to execute a simulation of a
 * series of horse races with a set of pairs Horse/Jockey, a set of Spectators and
 * a Broker.
 */
class SimulatorConfiguration {

    /**
     * Number of Pairs Horse/Jockey to compete on the simulation.
     */
    static final int NUMBER_OF_PAIRS_HORSE_JOCKEY = 4;

    /**
     * Number of Spectators to attend these events.
     */
    static final int NUMBER_OF_SPECTATORS = 4;

    /**
     * Number of races of a simulated event.
     */
    static final int NUMBER_OF_RACES = 5;

    /**
     * Maximum amount of money a Spectator could have.
     */
    static final int MONEY_MAX_BOUND = 999;

    /**
     * Minimum amount of money a Spectator could have.
     */
    static final int MONEY_MIN_BOUND = 1;

    /**
     * Maximum ability of the pairs Horse/Jockey.
     */
    static final int ABILITY_MAX_BOUND = 5;

    /**
     * Minimum ability of the pairs Horse/Jockey.
     */
    static final int ABILITY_MIN_BOUND = 1;
}
