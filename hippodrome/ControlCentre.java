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
 * @version 0.1
 */
public class ControlCentre {
    public ControlCentre(GeneralInformationRepository repository, int numberOfSpectators, int numberOfHorses) {
        this.numberOfSpectators = numberOfSpectators;
        this.numberOfHorses = numberOfHorses;
        this.repository = repository;
    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses from the {@link Stable} to the
     * {@link Paddock}. Such horses, representation of pairs Horse/Jockey's, must be identified by a race
     * number identification, such as the {@link entities.Broker}'s call should only be relative to some of the
     * pairs - the ones which will run side-by-side on the same {@link Race}, on the same {@link RacingTrack}.
     *
     * @param raceNumber number identification of the next {@link Race}.
     */
    public synchronized void summonHorsesToPaddock(int raceNumber) {
        // SYNC - signal the horses on Stable to go to Paddock.
    }

    /**
     * Signal given by the {@link entities.Broker} to start a {@link Race} identified by {@code raceNumber}.
     *
     * @param raceNumber number idenitification of the next {@link Race} to be started as of this instant.
     */
    public synchronized void startTheRace(int raceNumber) {
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
        // wait for last makeAMove() of HJ
        // change B state to STR
        // done
    }

    /**
     * Start entertaining the guests (representation of the {@link Spectator}s), as the {@link entities.Broker}'s actions
     * can be considered as terminated.
     */
    public synchronized void entertainTheGuests() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.PLAYING_HOST_AT_THE_BAR);
        // non-blocking
        // done
    }

    /**
     * Wait for the next signal of the {@link entities.Broker} in order to prepare a new {@link Race}, identified
     * by the number {@code raceNumber}.
     *
     * @param raceNumber number identification of the next {@link Race} to begin.
     *
     * @return {@code true} if the next race is still not prepared to begin; otherwise {@code false}.
     */
    public synchronized boolean waitForTheNextRace(int raceNumber) {
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
        // wait till Horses free us with PTP() — the last horse
        // when wait is ignored, then return true
        // done
        return true;
    }

    /**
     * Signal set out to all the {@link Spectator}s in order to watch the {@link Race} number {@code raceNumber}.
     *
     * @param raceNumber number identification of the {@link Race} which is about to start.
     */
    public synchronized void goWatchTheRace(int raceNumber) {
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.WATCHING_A_RACE);
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
        // waits for report results of the broker
        // changes state to WAR of S
        // done
    }

    /**
     * Relax a bit from the games, as this is could be the final transition of a {@link Spectator} lifecycle.
     */
    public synchronized void relaxABit() {
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.CELEBRATING);
        // done
    }

    /**
     * Publishing of the results by the {@link entities.Broker} performing its job.
     */
    public synchronized void reportResults() {
        brokerDidNotReportResults = false;
        notifyAll();
        // notify S to unleash WAR
        // done
    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses from the {@link Stable} to the
     * {@link Paddock}.
     */
    public synchronized void summonHorsesToPaddock() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.ANNOUNCING_NEXT_RACE);
        while (lastSpectatorHasNotArrivedOnPaddock) {
            try {
                System.out.println("Cwsa");
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
        // wait for final goCheckHorses of CC
        // change B state to ANR
        // done
    }

    /**
     * Signal given by the last pair Horse/Jockey which exits the {@link Stable} in direction to the {@link Paddock}.
     */
    public synchronized void proceedToPaddock() {
        numberOfHorseJockeysOnPaddock++;
        if (numberOfHorseJockeysOnPaddock == numberOfHorses) {
            lastHorseJockeyHasNotArrivedOnPaddock = false;
            notifyAll();
        }
        // notify (as the last HJ) Spectators at WFARTS state
        // i.e. if I'm the last HJ, then notify
        // done
    }

    /**
     * Signal given by the last {@link Spectator} which arrives at the {@link Paddock} to watch the horses, before placing
     * a bet, to alert the {@link Broker}.
     */
    public synchronized void goCheckHorses() {
        lastSpectatorHasNotArrivedOnPaddock = false;
        notifyAll();
        // notify broker on ANR that the last spectator is done
        // done
    }

    public synchronized void makeAMove() {
        thereIsStillHorsesToFinishRace = false;
        notifyAll();
        // done
    }

    private int numberOfSpectators;

    private int numberOfSpectatorsOnPaddock = 0;

    private int numberOfHorseJockeysOnPaddock = 0;

    private int numberOfHorses;

    private int winnerHorseJockey;

    private GeneralInformationRepository repository;

    /**
     * Condition variable to control wait for last {@link ControlCentre#goCheckHorses()}.
     */
    private boolean lastSpectatorHasNotArrivedOnPaddock = true;

    private boolean lastHorseJockeyHasNotArrivedOnPaddock = true;

    private boolean thereIsStillHorsesToFinishRace = true;

    private boolean brokerDidNotReportResults = true;
}
