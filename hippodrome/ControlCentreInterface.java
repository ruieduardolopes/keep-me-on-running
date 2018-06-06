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

    Response startTheRace() throws Exception;

    Response entertainTheGuests() throws Exception;

    Response waitForTheNextRace(int spectator) throws Exception;

    Response goWatchTheRace(int spectator) throws Exception;

    Response relaxABit(int spectator) throws Exception;

    int reportResults() throws Exception;

    Response summonHorsesToPaddock() throws Exception;

    Response proceedToPaddock(int horseJockeyId) throws Exception;

    void goCheckHorses() throws Exception;

    void makeAMove(int horseJockeyId) throws Exception;

    public int getNumberOfEntitiesDeclaringExit() throws Exception;
}
