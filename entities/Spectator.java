package entities;

/**
 * Implementation of a Spectator which will be watching an horse race and betting on it. This entity,
 * belonging to the {@link entities} package, must coordinate its actions throughout several shared
 * memory regions, while interacting with the {@link Broker} (which controlls its bets) and with the
 * {@link HorseJockey} (in which each Spectator will bet).
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Broker
 * @see HorseJockey
 * @since 1.0
 */
public class Spectator implements Runnable {
    /**
     * Definition of the Spectator's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code Spectator}, since its displacement from the
     * {@link hippodrome.BettingCentre}, to the {@link hippodrome.Paddock}, passing by the {@link hippodrome.Stable}.
     */
    @Override
    public void run() {

    }

    /**
     * Returns the Spectator state representation given by the {@link SpectatorState}
     * enumeration.
     *
     * @return the current spectator {@link SpectatorState}.
     * @see SpectatorState
     */
    public SpectatorState getState() {
        return state;
    }

    /**
     * Returns the Spectator numerical identification between all the others Spectators.
     *
     * @return a numerical identification represented as an integer.
     */
    public int getIdentification() {
        return identification;
    }

    /**
     * Returns the amount of current money that this Spectator has available for new bets.
     *
     * @return the current amount of money, as an integer.
     */
    public int getMoney() {
        return money;
    }

    /**
     * A representation of the Spectator's state given by the {@link SpectatorState}
     * enumeration.
     */
    private SpectatorState state = SpectatorState.WAITING_FOR_A_RACE_TO_START;

    /**
     * Proper Spectator numerical identification.
     */
    private int identification;

    /**
     * Current amount of this Spectator's money to spend on new bets.
     */
    private int money;
}
