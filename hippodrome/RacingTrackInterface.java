package hippodrome;

import hippodrome.actions.Race;

public interface RacingTrackInterface {
    void proceedToStartLine() throws InterruptedException;

    void makeAMove(int horseId) throws InterruptedException;

    boolean hasFinishLineBeenCrossed(int horseJockeyId);

    void startTheRace();

    Race getRace();

    int getWinner();
}
