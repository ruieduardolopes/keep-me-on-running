package hippodrome;

import entities.Spectator;
import hippodrome.responses.Response;

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

    Response startTheRace() throws InterruptedException;

    Response entertainTheGuests() throws InterruptedException;

    Response waitForTheNextRace(int spectator) throws InterruptedException;

    Response goWatchTheRace(int spectator) throws InterruptedException;

    Response relaxABit(int spectator) throws InterruptedException;

    int reportResults() throws InterruptedException;

    Response summonHorsesToPaddock() throws InterruptedException;

    Response proceedToPaddock(int horseJockeyId) throws InterruptedException;

    void goCheckHorses() throws InterruptedException;

    void makeAMove(int horseJockeyId) throws InterruptedException;
}
