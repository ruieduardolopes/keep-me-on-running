package hippodrome;

import entities.*;
import hippodrome.actions.Race;

/**
 * Place where the {@link Spectator}s go to enjoy the race (at a Watching Stand) and the {@link entities.Broker}
 * controls the races and publishes its results.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 1.0
 */
public class ControlCentre {
    /**
     * Creates a Control Centre.
     * <br>
     * This constructor creates a Control Centre giving a number of pairs Horse/Jockeys. Plus, an instance of the
     * repository is also given in order to report status changes on the course of its actions.
     *
     * @param repository An instance of a {@link GeneralInformationRepository} in order to report all the actions and
     *                   log each and every moment.
     * @param numberOfHorses the number of pairs Horse/Jockeys which will compete against one another.
     */
    public ControlCentre(GeneralInformationRepository repository, int numberOfHorses) {
        this.numberOfHorses = numberOfHorses;
        this.repository = repository;
    }

    /**
     * Changes the state of the {@link Broker} to Supervising the Race ({@code STR}) and waits till the last
     * {@link ControlCentre#makeAMove()} is performed by a pair Horse/Jockey.
     */
    public synchronized void startTheRace() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SUPERVISING_THE_RACE);
        while (thereIsStillHorsesToFinishRace) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(10);
            }
        }
    }

    /**
     * Start entertaining the guests (representation of the {@link Spectator}s), as the {@link entities.Broker}'s actions
     * can be considered as terminated.
     */
    public synchronized void entertainTheGuests() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
    }

    /**
     * Changes the {@link Spectator}'s state to Waiting for a Race to Start ({@code WFRTS}) and waits till the last
     * {@link ControlCentre#proceedToPaddock()} of the pair Horse/Jockey.
     *
     * @return if the last pair Horse/Jockey has arrived on Paddock.
     */
    public synchronized boolean waitForTheNextRace() {
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.WAITING_FOR_A_RACE_TO_START);
        while (lastHorseJockeyHasNotArrivedOnPaddock) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(15);
            }
        }
        return true;
    }

    /**
     * Changes the state of the {@link Spectator} to Watching a Race ({@code WAR}) and waits till the
     * {@link ControlCentre#reportResults()} of the Broker.
     * <br>
     * This method also resets the control variable for the last race results report, the number of horses which have
     * finished the race and the control variable for the {@link ControlCentre#startTheRace()}.
     */
    public synchronized void goWatchTheRace() {
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.WATCHING_A_RACE);
        brokerDidNotReportResults = true;
        finishedHorses = 0;
        thereIsStillHorsesToFinishRace = true;
        while (brokerDidNotReportResults) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(11);
            }
        }
    }

    /**
     * Relax a bit from the games, as this is could be the final transition of a {@link Spectator} lifecycle.
     */
    public synchronized void relaxABit() {
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.CELEBRATING);
    }

    /**
     * Signal that the race is over, having the publishing of the results by the {@link entities.Broker} performing its job.
     * <br>
     * Note that this method changes the value of the condition variable to the {@link ControlCentre#goWatchTheRace()}
     * wait condition, notifying its changes.
     */
    public synchronized void reportResults() {
        brokerDidNotReportResults = false;
        notifyAll();
    }

    /**
     * Changes the state of the Broker to Announcing Next Race ({@code ANR}) and waits till the last
     * {@link ControlCentre#goCheckHorses()} from the last Spectator to arrive on Paddock.
     * <br>
     * Note that this method also resets its proper condition variable after unlocking itself.
     */
    public synchronized void summonHorsesToPaddock() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        while (lastSpectatorHasNotArrivedOnPaddock) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(4);
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
    public synchronized void proceedToPaddock() {
        numberOfHorseJockeysOnPaddock++;
        if (numberOfHorseJockeysOnPaddock == numberOfHorses) {
            lastHorseJockeyHasNotArrivedOnPaddock = false;
            notifyAll();
            numberOfHorseJockeysOnPaddock = 0;
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
     * to relief the {@link Broker} from the state Supervising the Race ({@code STR}), as the race is over.
     */
    public synchronized void makeAMove() {
        finishedHorses++;
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
     *
     * This is a condition variable of the {@link ControlCentre#startTheRace()} and it is reset on the
     * {@link ControlCentre#goWatchTheRace()} method.
     */
    private boolean thereIsStillHorsesToFinishRace = true;

    /**
     * Condition variable for the Broker to report the results of the race.
     *
     * This is a condition variable of the {@link ControlCentre#goWatchTheRace()} and it is reset on the
     * {@link ControlCentre#goWatchTheRace()} method itself.
     */
    private boolean brokerDidNotReportResults = true;

    /**
     * The {@link GeneralInformationRepository} instance where all this pair Horse/Jockey's actions will be reported.
     */
    private GeneralInformationRepository repository; // TODO -- verify its use here.
}
