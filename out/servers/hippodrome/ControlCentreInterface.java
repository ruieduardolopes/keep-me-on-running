package hippodrome;

import entities.Spectator;

import java.rmi.Remote;

/**
 * Interface of the place where the {@link Spectator}s go to enjoy the race (at a Watching Stand) and the {@link entities.Broker}
 * controls the races and publishes its results.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public interface ControlCentreInterface extends Remote {

    void startTheRace() throws InterruptedException;

    void entertainTheGuests() throws InterruptedException;

    boolean waitForTheNextRace() throws InterruptedException;

    void goWatchTheRace() throws InterruptedException;

    void relaxABit() throws InterruptedException;

    int reportResults() throws InterruptedException;

    void summonHorsesToPaddock() throws InterruptedException;

    void proceedToPaddock() throws InterruptedException;

    void goCheckHorses() throws InterruptedException;

    void makeAMove() throws InterruptedException;
}
