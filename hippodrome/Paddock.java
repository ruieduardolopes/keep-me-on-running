package hippodrome;

import entities.HorseJockey;
import entities.HorseJockeyState;
import entities.Spectator;
import entities.SpectatorState;
import hippodrome.responses.Response;
import hippodrome.responses.ResponseType;

/**
 * Place where the horses are paradded before the {@link entities.Spectator}s. As the
 * pairs Horse/Jockey comes here, it means that each and everyone of them is getting prepared
 * to be on the next race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 2.0
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
    public Paddock(GeneralInformationRepositoryInterface repository, int numberOfSpectators, int numberOfHorses) {
        this.repository = repository;
        this.numberOfSpectators = numberOfSpectators;
        this.numberOfHorses = numberOfHorses;
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Paddock ({@code ATP}) and waits for the last Spectator to
     * arrive on Paddock on the {@link Paddock#goCheckHorses(int, boolean)} method.
     * <br>
     * This method also resets the condition variable of the {@link Paddock#goCheckHorses(int, boolean)} method.
     *
     * @param raceNumber number identification of the race which is about to begin.
     *
     * @throws Exception if the wait() is interrupted.
     */
    public synchronized void proceedToPaddock(int raceNumber) throws Exception {
        lastHorseDidNotProceedToStartLine = true;
        currentNumberOfHorses++;
        while (lastSpectatorHasNotArrivedOnPaddock) {
            try {
                wait();
            } catch (Exception ie) {
                ie.printStackTrace();
                throw new Exception("The proceedToPaddock() has been interrupted on its wait().");
            }
        }
        notifyAll();
    }

    /**
     * Changes the Spectator's state to Appraising the Horses ({@code ATH}) and waits till the last
     * {@link Paddock#proceedToStartLine(int)}. It also notifies {@link Paddock#proceedToPaddock(int)} when the last
     * Spectator is about to appraise the horse - you fool.
     *
     * @param isTheLastSpectator identification if the last {@link entities.Spectator}
     *                           has reached the premises - if {@code true}; otherwise {@code false}.
     *
     * @throws Exception if the wait() is interrupted.
     */
    public synchronized Response goCheckHorses(int spectator, boolean isTheLastSpectator) throws Exception {
        //((ServiceProviderAgent)Thread.currentThread()).setSpectatorState(SpectatorState.APPRAISING_THE_HORSES);
        repository.setSpectatorStatus(spectator, SpectatorState.APPRAISING_THE_HORSES);

        if (isTheLastSpectator) {
            lastSpectatorHasNotArrivedOnPaddock = false;
            notifyAll();
        }
        while (lastHorseDidNotProceedToStartLine) {
            try {
                wait();
            } catch (Exception ie) {
                ie.printStackTrace();
                throw new Exception("The goCheckHorses() has been interrupted on its wait().");
            }
        }
        lastSpectatorHasNotArrivedOnPaddock = true;
        numberOfEntitiesDeclaringExit++;
        return new Response(ResponseType.PADDOCK_GO_CHECK_HORSES, SpectatorState.APPRAISING_THE_HORSES, spectator);
    }

    /**
     * Signal that the race is about to start, by a {@link entities.HorseJockey}.
     * <br>
     * Note that this method changes the value of the condition variable to the {@link Paddock#goCheckHorses(int, boolean)}
     * wait condition, notifying its changes.
     */
    public synchronized Response proceedToStartLine(int horseJockeyId) throws Exception {
        try {
            currentNumberOfSpectators = 0;
            //((ServiceProviderAgent)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
            repository.setHorseJockeyStatus(horseJockeyId, HorseJockeyState.AT_THE_START_LINE);
            currentNumberOfHorses++;
            if (currentNumberOfHorses == numberOfHorses*2) {
                lastHorseDidNotProceedToStartLine = false;
                currentNumberOfHorses = 0;
                notifyAll();
            }
        } catch (Exception e) {
            throw new Exception();
        }
        return new Response(ResponseType.PADDOCK_PROCEED_TO_START_LINE, HorseJockeyState.AT_THE_START_LINE, horseJockeyId);
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
     * Gives the number of entities running on this hippodrome region which will exit the simulation.
     * @return the number of entities which declares death.
     */
    public int getNumberOfEntitiesDeclaringExit() {
        return numberOfEntitiesDeclaringExit;
    }

    /**
     * The number of entities running on this hippodrome region which will exit the simulation.
     */
    private int numberOfEntitiesDeclaringExit = 0;

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
     * This is a condition variable of the {@link Paddock#goCheckHorses(int, boolean)} method and it is reset on the
     * {@link Paddock#proceedToPaddock(int)} method.
     */
    private boolean lastHorseDidNotProceedToStartLine = true;

    /**
     * Condition variable for noticing when the last Spectator has (or has not) arrived to the Paddock.
     * <br>
     * This is a condition variable of the {@link Paddock#proceedToPaddock(int)} method and it is reset on the
     * {@link Paddock#goCheckHorses(int, boolean)} method.
     */
    private boolean lastSpectatorHasNotArrivedOnPaddock = true;

    /**
     * An entity which represents the repository.
     */
    private GeneralInformationRepositoryInterface repository;
}
