package entities;

/**
 * Implementation of a Broker, an essential character on a horse race action. This
 * entity, belonging to the {@link entities} package, has the responsibility of
 * controlling the races and of honouring and accepting the spectator's bets (for
 * more information about the {@code spectator}, please, consult {@link Spectator}).
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Spectator
 * @see HorseJockey
 * @since 1.0
 */
public class Broker implements Runnable {
    /**
     * Definition of the Broker's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code Broker}, since its displacement from the
     * {@link hippodrome.BettingCentre}, to the {@link hippodrome.ControlCentre}.
     */
    @Override
    public void run() {

    }

    /**
     * Returns the Broker state representation given by the {@link BrokerState}
     * enumeration.
     *
     * @return the current broker {@link BrokerState}.
     * @see BrokerState
     */
    public BrokerState getState() {
        return state;
    }

    /**
     * A representation of the Broker's state given by the {@link BrokerState}
     * enumeration.
     */
    private BrokerState state = BrokerState.OPENING_THE_EVENT;
}
