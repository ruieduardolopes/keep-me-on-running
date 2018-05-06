package hippodrome;

public interface ControlCentreInterface {

    void startTheRace() throws InterruptedException;

    void entertainTheGuests() throws InterruptedException;

    boolean waitForTheNextRace() throws InterruptedException;

    void goWatchTheRace() throws InterruptedException;

    void relaxABit() throws InterruptedException;

    int reportResults() throws InterruptedException;

    void summonHorsesToPaddock() throws InterruptedException;

    void proceedToPaddock() throws InterruptedException;

    void goCheckHorses() throws InterruptedException;

    void makeAMove() throws InterruptedException;
}
