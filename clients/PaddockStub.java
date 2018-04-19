package clients;

import hippodrome.PaddockInterface;

public class PaddockStub implements PaddockInterface {
    @Override
    public void proceedToPaddock(int raceNumber) throws InterruptedException {

    }

    @Override
    public void goCheckHorses(boolean isTheLastSpectator) throws InterruptedException {

    }

    @Override
    public void proceedToStartLine() {

    }

    @Override
    public boolean goCheckHorses() {
        return false;
    }
}
