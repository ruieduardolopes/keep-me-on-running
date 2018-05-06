package hippodrome;

import clients.ControlCentreStub;
import clients.GeneralInformationRepositoryStub;
import entities.*;
import lib.logging.Logger;
import server.ServiceProviderAgent;

import static configurations.SimulationConfigurations.*;

/**
 * Place where the {@link Spectator}s go to enjoy the race (at a Watching Stand) and the {@link entities.Broker}
 * controls the races and publishes its results.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 2.0
 */
public class ControlCentre implements ControlCentreInterface {
    /**
     * Creates a Control Centre.
     * <br>
     * This constructor creates a Control Centre giving a number of pairs Horse/Jockeys. Plus, an instance of the
     * repository is also given in order to report status changes on the course of its actions.
     *
     * @param numberOfHorses the number of pairs Horse/Jockeys which will compete against one another.
     */
    private ControlCentre(int numberOfHorses) {
        this.numberOfHorses = numberOfHorses;
        this.repository = new GeneralInformationRepositoryStub();
    }

    /**
     * Get a singleton instance of a Control Centre.
     *
     * @return an instance of the Control Centre.
     */
    public static ControlCentre getInstance() {
        if (instance == null) {
            instance = new ControlCentre(NUMBER_OF_PAIRS_HORSE_JOCKEY);
        }
        return instance;
    }

    /**
     * Changes the state of the {@link Broker} to Supervising the Race ({@code STR}) and waits till the last
     * {@link ControlCentre#makeAMove()} is performed by a pair Horse/Jockey.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void startTheRace() throws InterruptedException {
        ((ServiceProviderAgent)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        repository.setBrokerStatus(BrokerState.SUPERVISING_THE_RACE);
        while (thereIsStillHorsesToFinishRace) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The startTheRace() has been interrupted on its wait().");
            }
        }
    }

    /**
     * Start entertaining the guests (representation of the {@link Spectator}s), as the {@link entities.Broker}'s actions
     * can be considered as terminated.
     */
    public synchronized void entertainTheGuests() throws InterruptedException {
        try {
            ((ServiceProviderAgent)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
            repository.setBrokerStatus(BrokerState.PLAYING_HOST_AT_THE_BAR);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            throw new InterruptedException();
        }
    }

    /**
     * Changes the {@link Spectator}'s state to Waiting for a Race to Start ({@code WFRTS}) and waits till the last
     * {@link ControlCentre#proceedToPaddock()} of the pair Horse/Jockey.
     *
     * @throws InterruptedException if the wait() is interrupted.
     *
     * @return if the last pair Horse/Jockey has arrived on Paddock.
     */
    public synchronized boolean waitForTheNextRace() throws InterruptedException {
        brokerDidNotReportResults = true;
        ((ServiceProviderAgent)Thread.currentThread()).setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START);
        repository.setSpectatorStatus(((ServiceProviderAgent)Thread.currentThread()).getSpectatorIdentification(), SpectatorState.WAITING_FOR_A_RACE_TO_START);
        while (lastHorseJockeyHasNotArrivedOnPaddock) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The waitForTheNextRace() has been interrupted on its wait().");
            }
        }
        return true;
    }

    /**
     * Changes the state of the {@link Spectator} to Watching a Race ({@code WAR}) and waits till the
     * {@link ControlCentre#reportResults()} of the Entities.
     * <br>
     * This method also resets the control variable for the last race results report, the number of horses which have
     * finished the race and the control variable for the {@link ControlCentre#startTheRace()}.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void goWatchTheRace() throws InterruptedException {
        ((ServiceProviderAgent)Thread.currentThread()).setSpectatorState(SpectatorState.WATCHING_A_RACE);
        repository.setSpectatorStatus(((ServiceProviderAgent)Thread.currentThread()).getSpectatorIdentification(), SpectatorState.WATCHING_A_RACE);
        finishedHorses = 0;
        raceWinner = -1;
        thereIsStillHorsesToFinishRace = true;
        while (brokerDidNotReportResults) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The goWatchTheRace() has been interrupted on its wait().");
            }
        }
    }

    /**
     * Relax a bit from the games, as this is could be the final transition of a {@link Spectator} lifecycle.
     */
    public synchronized void relaxABit() {
        try {
            ((ServiceProviderAgent)Thread.currentThread()).setSpectatorState(SpectatorState.CELEBRATING);
            repository.setSpectatorStatus(((ServiceProviderAgent)Thread.currentThread()).getSpectatorIdentification(), SpectatorState.CELEBRATING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Signal that the race is over, having the publishing of the results by the {@link entities.Broker} performing its job.
     * <br>
     * Note that this method changes the value of the condition variable to the {@link ControlCentre#goWatchTheRace()}
     * wait condition, notifying its changes.
     *
     * @return the winner of the current race.
     */
    public synchronized int reportResults() {
        brokerDidNotReportResults = false;
        notifyAll();
        return raceWinner;
    }

    /**
     * Changes the state of the Entities to Announcing Next Race ({@code ANR}) and waits till the last
     * {@link ControlCentre#goCheckHorses()} from the last Spectator to arrive on Paddock.
     * <br>
     * Note that this method also resets its proper condition variable after unlocking itself.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void summonHorsesToPaddock() throws InterruptedException {
        ((ServiceProviderAgent)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        repository.setBrokerStatus(BrokerState.ANNOUNCING_NEXT_RACE);
        while (lastSpectatorHasNotArrivedOnPaddock) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The summonHorsesToPaddock() has been interrupted on its wait().");
            }
        }
        lastSpectatorHasNotArrivedOnPaddock = true;
    }

    /**
     * Signal given by the last pair Horse/Jockey that arrives on Paddock, notifying the {@link ControlCentre#waitForTheNextRace()}
     * of the {@link Spectator}s.
     * <br>
     * Note that this method also resets the condition variable of itself after the notification.
     */
    public synchronized void proceedToPaddock() throws InterruptedException {
        try {
            ++numberOfHorseJockeysOnPaddock;
            ((ServiceProviderAgent)(Thread.currentThread())).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
            repository.setHorseJockeyStatus(((ServiceProviderAgent)(Thread.currentThread())).getHorseJockeyIdentification(), HorseJockeyState.AT_THE_PADDOCK);

            if (numberOfHorseJockeysOnPaddock == NUMBER_OF_PAIRS_HORSE_JOCKEY) {
                lastHorseJockeyHasNotArrivedOnPaddock = false;
                notifyAll();
                numberOfHorseJockeysOnPaddock = 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new InterruptedException();
        }
    }

    /**
     * Signal given by the last Spectator which arrives at the {@link Paddock} to appraise the horses, notifying the
     * {@link ControlCentre#summonHorsesToPaddock()} of the {@link Broker}.
     * <br>
     * Note that this method also resets the condition variable used on the {@link ControlCentre#waitForTheNextRace()}.
     */
    public synchronized void goCheckHorses() {
        lastSpectatorHasNotArrivedOnPaddock = false;
        lastHorseJockeyHasNotArrivedOnPaddock = true;
        notifyAll();
    }

    /**
     * Signal given the last horse has crossed the finish line, notifying the {@link ControlCentre#startTheRace()} in order
     * to relief the {@link Broker} from the state Supervising the Race ({@code STR}), as the race is over. This method also
     * helps identifying when a pair Horse/Jockey has won the race.
     */
    public synchronized void makeAMove() {
        finishedHorses++;
        if (finishedHorses == 1) {
            raceWinner = ((ServiceProviderAgent)(Thread.currentThread())).getHorseJockeyIdentification();
        }
        if (finishedHorses == numberOfHorses) {
            thereIsStillHorsesToFinishRace = false;
            notifyAll();
        }
    }

    /**
     * The number of pairs Horse/Jockey that will be running and controlled on this region.
     */
    private int numberOfHorses;

    /**
     * The number of pairs Horse/Jockey which have arrived on Paddock.
     * <br>
     * This is a condition variable of {@link ControlCentre#proceedToPaddock()} and it is reset on the
     * {@link ControlCentre#proceedToPaddock()} method itself.
     */
    private int numberOfHorseJockeysOnPaddock = 0;

    /**
     * The number of pairs Horse/Jockey which have crossed the finish line.
     * <br>
     * This is a condition variable of {@link ControlCentre#makeAMove()} and it is reset on the
     * {@link ControlCentre#goWatchTheRace()} method.
     */
    private int finishedHorses = 0;

    /**
     * Condition variable for the last Spectator which has not arrived on the Paddock yet.
     * <br>
     * This is a condition variable of {@link ControlCentre#goCheckHorses()} and it is reset on the
     * {@link ControlCentre#summonHorsesToPaddock()} method.
     */
    private boolean lastSpectatorHasNotArrivedOnPaddock = true;

    /**
     * Condition variable for the last pair Horse/Jockey which has not arrived on the Paddock yet.
     * <br>
     * This is a condition variable of the {@link ControlCentre#waitForTheNextRace()} and it is reset on the
     * {@link ControlCentre#goCheckHorses()} method.
     */
    private boolean lastHorseJockeyHasNotArrivedOnPaddock = true;

    /**
     * Condition variable for the last pair Horse/Jockey to finish the race.
     * <br>
     * This is a condition variable of the {@link ControlCentre#startTheRace()} and it is reset on the
     * {@link ControlCentre#goWatchTheRace()} method.
     */
    private boolean thereIsStillHorsesToFinishRace = true;

    /**
     * Condition variable for the Entities to report the results of the race.
     * <br>
     * This is a condition variable of the {@link ControlCentre#goWatchTheRace()} and it is reset on the
     * {@link ControlCentre#goWatchTheRace()} method itself.
     */
    private boolean brokerDidNotReportResults = true;

    /**
     * Identification of the pair Horse/Jockey winner of the current race.
     */
    private int raceWinner = -1;

    /**
     * An entity which represents the repository.
     */
    private GeneralInformationRepositoryStub repository;

    /**
     * The created instance of this class
     */
    private static ControlCentre instance;
}
