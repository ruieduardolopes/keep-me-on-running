package hippodrome;

import hippodrome.actions.Race;

public interface RacingTrackInterface {
    void proceedToStartLine() throws InterruptedException;

    void makeAMove(int horseId) throws InterruptedException;

    boolean hasFinishLineBeenCrossed(int horseJockeyId) throws InterruptedException, Exception;

    void startTheRace() throws InterruptedException;

    Race getRace() throws InterruptedException;

    void setRace(Race race) throws InterruptedException;

    int getWinner() throws InterruptedException;
}
