package clients;

import hippodrome.RacingTrackInterface;
import hippodrome.actions.Race;

public class RacingTrackStub implements RacingTrackInterface {
    @Override
    public void proceedToStartLine() throws InterruptedException {

    }

    @Override
    public void makeAMove(int horseId) throws InterruptedException {

    }

    @Override
    public boolean hasFinishLineBeenCrossed(int horseJockeyId) {
        return false;
    }

    @Override
    public void startTheRace() {

    }

    @Override
    public Race getRace() {
        return null;
    }

    @Override
    public int getWinner() {
        return 0;
    }
}
