package hippodrome;

import entities.HorseJockey;
import entities.HorseJockeyState;

/**
 * Place where the horses rest waiting their turn to enter the competition.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 1.0
 */
public class Stable {
    public Stable() {

    }

    /**
     * Signal given by the {@link entities.Broker} in order to allow the pair Horse/Jockey to move
     * to this place (the {@link Stable}).
     */
    public synchronized void proceedToStable(int raceNumber) {
        while (currentRaceNumber != raceNumber) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(5);
            }
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
    }

    /**
     * Changes the state of current {@link HorseJockey} to move to the {@link Stable}.
     */
    public synchronized void proceedToStable() {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses of the {@code raceNumber} race, from the
     * {@link Stable} to the {@link Paddock}.
     *
     * @param raceNumber number identification of the next race, where the called pairs Horse/Jockey will be competing
     *                   against each other.
     */
    public synchronized void summonHorsesToPaddock(int raceNumber) {
        currentRaceNumber = raceNumber;
        notifyAll();
    }

    /**
     * Current race number identifier, as an integer.
     */
    private int currentRaceNumber;

}
