package hippodrome;

import hippodrome.responses.Response;

import java.rmi.Remote;

/**
 * Interface of the place where the horses rest waiting their turn to enter the competition.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public interface StableInterface extends Remote {

    Response proceedToStable(int horseJockeyId, int raceNumber) throws InterruptedException;

    Response proceedToStable(int horseJockeyId) throws InterruptedException;

    void summonHorsesToPaddock(int raceNumber) throws InterruptedException;
}
