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
 * @version 1.0
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
        HorseJockey horse = ((HorseJockey)Thread.currentThread());
        if (horse.getRaceNumber() == raceNumber) {
            horse.setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        }
        lastHorseDidNotProceedToStartLine = true;
        currentNumberOfHorses++;
        while (lastSpectatorHasNotArrivedOnPaddock) {
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
        notifyAll();
    }

    /**
     * Signal that the last {@link entities.Spectator} has reached the {@link Paddock}.
     *
     * @param isTheLastSpectator {@code boolean} variable which identifies when the last {@link entities.Spectator}
     *                           has reached the premises.
     */
    public synchronized void goCheckHorses(boolean isTheLastSpectator) {
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.APPRAISING_THE_HORSES);
        if (isTheLastSpectator) {
            lastSpectatorHasNotArrivedOnPaddock = false;
            notifyAll();
        }
        while (lastHorseDidNotProceedToStartLine) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(14);
            }
        }
        lastSpectatorHasNotArrivedOnPaddock = true;
    }

    public synchronized void proceedToStartLine() {
        currentNumberOfSpectators = 0;
        currentNumberOfHorses++;
        if (currentNumberOfHorses == numberOfHorses*2) {
            lastHorseDidNotProceedToStartLine = false;
            currentNumberOfHorses = 0;
            notifyAll();
        }
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
        return currentNumberOfSpectators == numberOfSpectators;
    }


    /**
     * Instance of the global repository of information given by the {@code Simulation}.
     */
    private GeneralInformationRepository repository;

    /**
     * Current number of {@link HorseJockey} present in the {@link Paddock}.
     */
    private int currentNumberOfHorses;

    /**
     * Current number of {@link Spectator}s present in the {@link Paddock}.
     */
    private int currentNumberOfSpectators;

    /**
     * Total number of {@link HorseJockey} present in the {@link Race}.
     */
    private int numberOfHorses;

    /**
     * Total number of {@link Spectator} present in the {@link Race}.
     */
    private int numberOfSpectators;

    /**
     * Indicates whether all the {@link HorseJockey}s have proceeded to the {@link RacingTrack}.
     */
    private boolean lastHorseDidNotProceedToStartLine = true;

    /**
     * Indicates whether all the {@link Spectator}s have proceeded to the {@link Paddock}.
     */
    private boolean lastSpectatorHasNotArrivedOnPaddock = true;
}
