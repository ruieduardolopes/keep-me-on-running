package hippodrome;

import configurations.SimulationConfigurations;
import entities.HorseJockeyState;
import hippodrome.responses.Response;
import hippodrome.responses.ResponseType;

/**
 * Place where the horses rest waiting their turn to enter the competition.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 2.0
 */
public class Stable implements StableInterface {
    /**
     * Creates a Stable.
     * <br>
     * This constructor creates a Stable. Plus, an instance of the
     * repository is also given in order to report status changes on the course of its actions.
     */
    public Stable(GeneralInformationRepositoryInterface repository) {
        this.repository = repository;
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Stable ({@code ATS}) and waits till the current race is,
     * in fact, the current race of the event which is about to start.
     *
     * @param raceNumber the race number which is about to start.
     *
     * @throws Exception if the wait() is interrupted.
     */
    public synchronized Response proceedToStable(int horseJockeyId, int raceNumber) throws Exception {
        //((ServiceProviderAgent)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
        repository.setHorseJockeyStatus(horseJockeyId, HorseJockeyState.AT_THE_STABLE);
        brokerDidNotSaidToAdvance = true;
        if (++numberOfHorsesOnStable == SimulationConfigurations.NUMBER_OF_PAIRS_HORSE_JOCKEY && raceNumber == 0) {
            horsesAreNotAvailable = false;
            notifyAll();
        }
        while (currentRaceNumber != raceNumber) {
            try {
                wait();
            } catch (Exception ie) {
                ie.printStackTrace();
                throw new Exception("The proceedToStable() has been interrupted on its wait().");
            }
        }
        return new Response(ResponseType.STABLE_PROCEED_TO_STABLE_WITH_RACE_ID, HorseJockeyState.AT_THE_STABLE, horseJockeyId);
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Stable ({@code ATS}).
     * <br>
     * This method is useful to finish the lifecycle of the pairs Horse/Jockey.
     */
    public synchronized Response proceedToStable(int horseJockeyId) throws Exception {
        //((ServiceProviderAgent)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
        repository.setHorseJockeyStatus(horseJockeyId, HorseJockeyState.AT_THE_STABLE);
        if (thisIsAfterTheLastRun) {
            return new Response(ResponseType.STABLE_PROCEED_TO_STABLE, HorseJockeyState.AT_THE_STABLE, horseJockeyId);
        }
        while (brokerDidNotSaidToAdvance) {
            try {
                wait();
            } catch (Exception ie) {
                ie.printStackTrace();
                throw new Exception("The proceedToStable() has been interrupted on its wait().");
            }
        }
        if (currentRaceNumber == SimulationConfigurations.NUMBER_OF_RACES-1) {
            thisIsAfterTheLastRun = true;
        }
        return new Response(ResponseType.STABLE_PROCEED_TO_STABLE, HorseJockeyState.AT_THE_STABLE, horseJockeyId);
    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses of the {@code raceNumber} race, from the
     * {@link Stable} to the {@link Paddock}.
     *
     * @param raceNumber number identification of the next race, where the called pairs Horse/Jockey will be competing
     *                   against each other.
     */
    public synchronized void summonHorsesToPaddock(int raceNumber) throws Exception {
        while (horsesAreNotAvailable) {
            try {
                wait();
            } catch (Exception ie) {
                ie.printStackTrace();
                throw new Exception("The proceedToStable() has been firstly interrupted on its wait().");
            }
        }
        currentRaceNumber = raceNumber;
        brokerDidNotSaidToAdvance = false;
        notifyAll();
    }

    /**
     * Current race number identifier, as an integer.
     */
    private int currentRaceNumber = -1;

    /**
     * The number of pairs Horse/Jockey which arrived on stable.
     */
    private int numberOfHorsesOnStable = 0;

    /**
     * Condition variable for noticing when the pairs Horse/Jockey are not yet created.
     */
    private boolean horsesAreNotAvailable = true;

    /**
     * Condition variable for noticing when the races are over.
     */
    private boolean thisIsAfterTheLastRun = false;

    /**
     * Condition variable for noticing when the Broker does not want to the Spectators to advance.
     */
    private boolean brokerDidNotSaidToAdvance = true;

    /**
     * An entity which represents the repository.
     */
    private GeneralInformationRepositoryInterface repository;
}
