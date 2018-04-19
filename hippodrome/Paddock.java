package hippodrome;

import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectator;
import entities.SpectatorState;

/**
 * Place where the horses are paradded before the {@link entities.Spectator}s. As the
 * pairs Horse/Jockey comes here, it means that each and everyone of them is getting prepared
 * to be on the next race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 1.1
 */
public class Paddock implements PaddockInterface {
    /**
     * Creates a Paddock.
     * <br>
     * This constructor creates a Paddock giving a number of pairs Horse/Jockey and a number of Spectators. Plus,
     * an instance of the repository is also given in order to report status changes on the course of its actions.
     *
     * @param numberOfSpectators The number of Spectators which will be attending the event.
     * @param numberOfHorses The number of pairs Horse/Jockey which will be competing.
     */
    public Paddock(int numberOfSpectators, int numberOfHorses) {
        this.numberOfSpectators = numberOfSpectators;
        this.numberOfHorses = numberOfHorses;
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Paddock ({@code ATP}) and waits for the last Spectator to
     * arrive on Paddock on the {@link Paddock#goCheckHorses(boolean)} method.
     * <br>
     * This method also resets the condition variable of the {@link Paddock#goCheckHorses(boolean)} method.
     *
     * @param raceNumber number identification of the race which is about to begin.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void proceedToPaddock(int raceNumber) throws InterruptedException {
        lastHorseDidNotProceedToStartLine = true;
        currentNumberOfHorses++;
        while (lastSpectatorHasNotArrivedOnPaddock) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The proceedToPaddock() has been interrupted on its wait().");
            }
        }
        notifyAll();
    }

    /**
     * Changes the Spectator's state to Appraising the Horses ({@code ATH}) and waits till the last
     * {@link Paddock#proceedToStartLine()}. It also notifies {@link Paddock#proceedToPaddock(int)} when the last
     * Spectator is about to appraise the horse - you fool.
     *
     * @param isTheLastSpectator identification if the last {@link entities.Spectator}
     *                           has reached the premises - if {@code true}; otherwise {@code false}.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void goCheckHorses(boolean isTheLastSpectator) throws InterruptedException {
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
                throw new InterruptedException("The goCheckHorses() has been interrupted on its wait().");
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
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
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
    private int currentNumberOfHorses = 0;

    /**
     * Current number of {@link Spectator}s present in the {@link Paddock}.
     */
    private int currentNumberOfSpectators = 0;

    /**
     * Total number of {@link HorseJockey} competing.
     */
    private int numberOfHorses;

    /**
     * Total number of {@link Spectator} attending the events.
     */
    private int numberOfSpectators;

    /**
     * Condition variable for noticing when the last pair Horse/Jockey have (or have not) proceeded to the
     * start line.
     * <br>
     * This is a condition variable of the {@link Paddock#goCheckHorses(boolean)} method and it is reset on the
     * {@link Paddock#proceedToPaddock(int)} method.
     */
    private boolean lastHorseDidNotProceedToStartLine = true;

    /**
     * Condition variable for noticing when the last Spectator has (or has not) arrived to the Paddock.
     * <br>
     * This is a condition variable of the {@link Paddock#proceedToPaddock(int)} method and it is reset on the
     * {@link Paddock#goCheckHorses(boolean)} method.
     */
    private boolean lastSpectatorHasNotArrivedOnPaddock = true;
}
