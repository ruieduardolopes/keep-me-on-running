package hippodrome;

import clients.GeneralInformationRepositoryStub;
import entities.HorseJockey;
import entities.HorseJockeyState;
import lib.logging.Logger;
import server.ServiceProviderAgent;

/**
 * Place where the horses rest waiting their turn to enter the competition.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 1.1
 */
public class Stable implements StableInterface {
    public Stable() {
        repository = new GeneralInformationRepositoryStub();
    }

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
        Logger.printError("On proceed to stable, the race number is %d", raceNumber);
        ((ServiceProviderAgent)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_STABLE);
        repository.setHorseJockeyStatus(((ServiceProviderAgent)(Thread.currentThread())).getHorseJockeyIdentification(), HorseJockeyState.AT_THE_STABLE);
        brokerDidNotSaidToAdvance = true;
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
        while (brokerDidNotSaidToAdvance) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The proceedToStable() has been interrupted on its wait().");
            }
        }
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
        brokerDidNotSaidToAdvance = false;
        notifyAll();
    }

    /**
     * Current race number identifier, as an integer.
     */
    private int currentRaceNumber = -1;

    private boolean brokerDidNotSaidToAdvance = true;

    private static Stable instance;

    private GeneralInformationRepositoryStub repository;
}
