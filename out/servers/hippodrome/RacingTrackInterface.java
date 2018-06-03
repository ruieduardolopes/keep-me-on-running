package hippodrome;

import hippodrome.actions.Race;
import hippodrome.responses.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of the place where the races take place. Here each race is represented by an element
 * of the class {@link Race}, described by a given number of tracks, an identification
 * and a distance.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public interface RacingTrackInterface extends Remote {

    void proceedToStartLine(int horseJockeyId) throws Exception;

    Response makeAMove(int horseId, int ability) throws Exception;

    boolean hasFinishLineBeenCrossed(int horseJockeyId) throws Exception;

    void startTheRace() throws Exception;

    Race getRace() throws Exception;

    void setRace(Race race) throws Exception;

    int getWinner() throws Exception;
}
