package hippodrome;

import hippodrome.actions.Race;

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
public interface RacingTrackInterface {
    void proceedToStartLine() throws InterruptedException;

    void makeAMove(int horseId) throws InterruptedException;

    boolean hasFinishLineBeenCrossed(int horseJockeyId) throws InterruptedException, Exception;

    void startTheRace() throws InterruptedException;

    Race getRace() throws InterruptedException;

    void setRace(Race race) throws InterruptedException;

    int getWinner() throws InterruptedException;
}
