package hippodrome;

import clients.GeneralInformationRepositoryStub;
import configurations.SimulationConfigurations;
import entities.HorseJockeyState;

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
    public Stable() {
        repository = new GeneralInformationRepositoryStub();
    }

    /**
     * Get a singleton instance of a General Repository of Information.
     *
     * @return an instance of the General Repository of Information.
     */
    public static Stable getInstance() {
        if (instance == null) {
            instance = new Stable();
        }
        return instance;
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Stable ({@code ATS}) and waits till the current race is,
     * in fact, the current race of the event which is about to start.
     *
     * @param raceNumber the race number which is about to start.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void proceedToStable(int raceNumber) throws InterruptedException {

        ((ServiceProviderAgent)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
        repository.setHorseJockeyStatus(((ServiceProviderAgent)(Thread.currentThread())).getHorseJockeyIdentification(), HorseJockeyState.AT_THE_STABLE);
        brokerDidNotSaidToAdvance = true;
        if (++numberOfHorsesOnStable == SimulationConfigurations.NUMBER_OF_PAIRS_HORSE_JOCKEY && raceNumber == 0) {
            horsesAreNotAvailable = false;
            notifyAll();
        }
        while (currentRaceNumber != raceNumber) {
            try {

                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The proceedToStable() has been interrupted on its wait().");
            }
        }
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Stable ({@code ATS}).
     * <br>
     * This method is useful to finish the lifecycle of the pairs Horse/Jockey.
     */
    public synchronized void proceedToStable() throws InterruptedException {
        ((ServiceProviderAgent)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
        repository.setHorseJockeyStatus(((ServiceProviderAgent)(Thread.currentThread())).getHorseJockeyIdentification(), HorseJockeyState.AT_THE_STABLE);
        if (thisIsAfterTheLastRun) {
            return;
        }
        while (brokerDidNotSaidToAdvance) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The proceedToStable() has been interrupted on its wait().");
            }
        }
        if (currentRaceNumber == SimulationConfigurations.NUMBER_OF_RACES-1) {
            thisIsAfterTheLastRun = true;
        }
    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses of the {@code raceNumber} race, from the
     * {@link Stable} to the {@link Paddock}.
     *
     * @param raceNumber number identification of the next race, where the called pairs Horse/Jockey will be competing
     *                   against each other.
     */
    public synchronized void summonHorsesToPaddock(int raceNumber) throws InterruptedException {
        while (horsesAreNotAvailable) {
            try {

                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The proceedToStable() has been firstly interrupted on its wait().");
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
     * The created instance of this class
     */
    private static Stable instance;

    /**
     * An entity which represents the repository.
     */
    private GeneralInformationRepositoryStub repository;
}
