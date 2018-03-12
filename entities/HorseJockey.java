package entities;

/**
 * Implementation of a pair Horse/Jockey, the main horse race character. This entity,
 * belonging to the {@link entities} package, represents one of N pairs in a competition,
 * being the bets target (by the {@link Spectator}) and having its conduct coordinated by the
 * {@link Broker}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Broker
 * @see Spectator
 * @since 1.0
 */
public class HorseJockey implements Runnable {
    /**
     * Definition of the HorseJockey's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code HorseJockey} pair, since its displacement from the
     * {@link hippodrome.Paddock}, to the {@link hippodrome.Stable}, passing by the {@link hippodrome.RacingTrack}.
     */
    @Override
    public void run() {

    }

    /**
     * Returns the pair Horse/Jockey state representation given by the {@link HorseJockeyState}
     * enumeration.
     *
     * @return the current spectator {@link HorseJockeyState}.
     * @see HorseJockeyState
     */
    public HorseJockeyState getState() {
        return state;
    }

    /**
     * Returns the pair Horse/Jockey number identification.
     *
     * @return a number represented by an integer.
     */
    public int getIdentification() {
        return identification;
    }

    /**
     * Returns the pair Horse/Jockey ability to move forward on its track of the Racing Track.
     *
     * @return an integer representation of the ability.
     */
    public int getAbility() {
        return ability;
    }

    /**
     * A representation of the pair Horse/Jockey's state given by the {@link HorseJockeyState}
     * enumeration.
     */
    private HorseJockeyState state = HorseJockeyState.AT_THE_STABLE;

    /**
     * Proper pair Horse/Jockey identification number.
     */
    private int identification;

    /**
     * Pair Horse/Jockey ability to run. This value represents how easily this pair Horse/Jockey
     * can advance on its track of the Racing Track.
     */
    private int ability;
}
