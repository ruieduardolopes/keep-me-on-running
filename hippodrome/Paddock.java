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
    /**
     * Creates a Racing Track.
     * <br>
     * This constructor creates a Racing Track giving a race. Plus, an instance of the
     * repository is also given in order to report status changes on the course of its actions.
     *
     * @param race A race to be executed over this Racing Track.
     * @param repository An instance of a {@link GeneralInformationRepository} in order to report all the actions and
     *                   log each and every moment.
     */

    /**
     * Creates a Paddock.
     * <br>
     * This constructor creates a Paddock giving a number of pairs Horse/Jockey and a number of Spectators. Plus,
     * an instance of the repository is also given in order to report status changes on the course of its actions.
     *
     * @param numberOfSpectators The number of Spectators which will be attending the event.
     * @param numberOfHorses The number of pairs Horse/Jockey which will be competing.
     * @param repository An instance of a {@link GeneralInformationRepository} in order to report all the actions and
     *                   log each and every moment.
     */
    public Paddock(int numberOfSpectators, int numberOfHorses, GeneralInformationRepository repository) {
        this.numberOfSpectators = numberOfSpectators;
        this.numberOfHorses = numberOfHorses;
        this.repository = repository;
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Paddock ({@code ATP}) and waits for the last Spectator to
     * arrive on Paddock on the {@link Paddock#goCheckHorses(boolean)} method.
     * <br>
     * This method also resets the condition variable of the {@link Paddock#goCheckHorses(boolean)} method.
     *
     * @param raceNumber number identification of the race which is about to begin.
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

    /**
     * Changes the Spectator's state to Appraising the Horses ({@code ATH}) and waits till the last
     * {@link Paddock#proceedToStartLine()}. It also notifies {@link Paddock#proceedToPaddock(int)} when the last
     * Spectator is about to appraise the horse - you fool.
     *
     * @param isTheLastSpectator identification if the last {@link entities.Spectator}
     *                           has reached the premises - if {@code true}; otherwise {@code false}.
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

    /**
     * Signal that the race is about to start, by a {@link entities.HorseJockey}.
     * <br>
     * Note that this method changes the value of the condition variable to the {@link Paddock#goCheckHorses(boolean)}
     * wait condition, notifying its changes.
     */
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
     * Current number of {@link HorseJockey} present in the {@link Paddock}.
     */
    private int currentNumberOfHorses;

    /**
     * Current number of {@link Spectator}s present in the {@link Paddock}.
     */
    private int currentNumberOfSpectators;

    /**
     * Total number of {@link HorseJockey} competing.
     */
    private int numberOfHorses;

    /**
     * Total number of {@link Spectator} attending the events.
     */
    private int numberOfSpectators;


    /**
     * Condition variable for noticing when a race is about to end.
     * <br>
     * This is a condition variable of {@link RacingTrack#makeAMove(int)}.
     */

    /**
     * Condition variable for noticing when the last pair Horse/Jockey have (or have not) proceeded to the
     * start line.
     *
     * This is a condition variable of the {@link Paddock#goCheckHorses(boolean)} method and it is reset on the
     * {@link Paddock#proceedToPaddock(int)} method.
     */
    private boolean lastHorseDidNotProceedToStartLine = true;

    /**
     * Condition variable for noticing when the last Spectator has (or has not) arrived to the Paddock.
     *
     * This is a condition variable of the {@link Paddock#proceedToPaddock(int)} method and it is reset on the
     * {@link Paddock#goCheckHorses(boolean)} method.
     */
    private boolean lastSpectatorHasNotArrivedOnPaddock = true;

    /**
     * The {@link GeneralInformationRepository} instance where all this region's actions will be reported.
     */
    private GeneralInformationRepository repository; // TODO - is it needed?
}
