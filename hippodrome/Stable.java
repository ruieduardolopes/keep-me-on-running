package hippodrome;

import entities.HorseJockey;
import entities.HorseJockeyState;

/**
 * Place where the horses rest waiting their turn to enter the competition.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 0.1
 */
public class Stable {
    /**
     * Signal given by the {@link entities.Broker} in order to allow the pair Horse/Jockey to move
     * to this place (the {@link Stable}).
     */
    public static void proceedToStable() {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);

    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses of the {@code raceNumber} race, from the
     * {@link Stable} to the {@link Paddock}.
     *
     * @param raceNumber number identification of the next race, where the called pairs Horse/Jockey will be competing
     *                   against each other.
     */
    public static void summonHorsesToPaddock(int raceNumber) {

    }

    /**
     * Allows the pairs Horse/Jockey to move from the {@link Stable} to the {@link Paddock}.
     */
    public static void proceedToPaddock() {

    }

    /**
     * Internal Structure for saving horses on {@code Stable}. Each index is a pair Horse/Jockey and each index content
     * have {@code true} if horse is on {@code Stable}; otherwise {@code false}.
     */
    private boolean[] horses;

}
