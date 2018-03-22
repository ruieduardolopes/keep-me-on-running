package hippodrome;

import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectator;
import entities.SpectatorState;
import hippodrome.actions.Race;

/**
 * Place where the horses are paradded before the {@link entities.Spectator}s. As the
 * pairs Horse/Jockey comes here, it means that each and everyone of them is getting prepared
 * to be on the next race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 0.1
 */
public class Paddock {
    public Paddock(int numberOfSpectators, int numberOfHorses, GeneralInformationRepository repository) {
        this.numberOfSpectators = numberOfSpectators;
        this.numberOfHorses = numberOfHorses;
        this.repository = repository;
    }
    /**
     * Signal given by the {@link entities.Broker} in order to proceed to here (the {@link Paddock}).
     * This signal is given calling only the pairs Horse/Jockey which are supposed to run on the next {@link Race},
     * identified by the number {@code raceNumber}.
     *
     * @param raceNumber number identification of the race which is about to begin.
     *
     * @return {@code true} if the last pair Horse/Jockey has been moved to the {@link Paddock}; otherwise
     * {@code false}.
     */
    public synchronized void proceedToPaddock(int raceNumber) {
        currentNumberOfHorses++;
        HorseJockey horse = ((HorseJockey)Thread.currentThread());
        if (horse.getRaceNumber() == raceNumber) {
            horse.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
            while (currentNumberOfHorses == numberOfHorses) {
                try {
                    wait();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    System.err.println("An error occurred while terminating the threads.");
                    System.err.println("The last program status was such as follows:");
                    ie.printStackTrace();
                    System.err.println("This program will now quit.");
                    System.exit(6);
                }
            }
        }
        // wait fot goCheckHorses() from Spectators on Paddock
        // switch HJ state to ATP
    }

    /**
     * Signal that the last {@link entities.Spectator} has reached the {@link Paddock}.
     *
     * @param isTheLastSpectator {@code boolean} variable which identifies when the last {@link entities.Spectator}
     *                           has reached the premises.
     */
    public synchronized void goCheckHorses(boolean isTheLastSpectator) {
        if (isTheLastSpectator) {
            notifyAll();
        }
        // wait till APH from Horses launches its last PTSL()
        // change S state to ATH
    }

    public synchronized void proceedToStartLine() {
        // if I'm the last HJ, then wake up Spectators at ATH
    }

    /**
     * Signal given by the {@link entities.Broker} in order to the {@link entities.Spectator}s go check the
     * pairs Horse/Jockey and verify if there are more to come. This must be done in order to establish a
     * more accurate bet on the probable winner.
     *
     * @return {@code true} if the last {@link entities.Spectator} has moved to the {@link Paddock}; otherwise
     * {@code false}.
     */
    public synchronized boolean goCheckHorses() {
        currentNumberOfSpectators++;
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.APPRAISING_THE_HORSES);
        return currentNumberOfSpectators == numberOfSpectators;
        // wake up horses at the paddock to free ATP state of HJ
        // return if this is the last spectator
    }

    /**
     * Internal Structure for saving horses on {@code Paddock}. Each index is a pair Horse/Jockey and each index content
     * have {@code true} if horse is on {@code Paddock}; otherwise {@code false}.
     */
    private boolean[] horses;

    /**
     * Internal Structure for saving spectators on {@code Paddock}. Each index is a Spectators and each index content
     * have {@code true} if spectator is on {@code Paddock}; otherwise {@code false}.
     */
    private boolean[] spectators;

    private GeneralInformationRepository repository;

    private int currentNumberOfHorses;

    private int currentNumberOfSpectators;

    private int numberOfHorses;

    private int numberOfSpectators;
}
