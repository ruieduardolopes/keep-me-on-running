package clients;

import hippodrome.ControlCentreInterface;

public class ControlCentreStub implements ControlCentreInterface {
    @Override
    public void startTheRace() throws InterruptedException {

    }

    @Override
    public void entertainTheGuests() {

    }

    @Override
    public boolean waitForTheNextRace() throws InterruptedException {
        return false;
    }

    @Override
    public void goWatchTheRace() throws InterruptedException {

    }

    @Override
    public void relaxABit() {

    }

    @Override
    public int reportResults() {
        return 0;
    }

    @Override
    public void summonHorsesToPaddock() throws InterruptedException {

    }

    @Override
    public void proceedToPaddock() {

    }

    @Override
    public void goCheckHorses() {

    }

    @Override
    public void makeAMove() {

    }
}
