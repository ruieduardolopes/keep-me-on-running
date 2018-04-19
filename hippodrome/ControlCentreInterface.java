package hippodrome;

public interface ControlCentreInterface {

    void startTheRace() throws InterruptedException;

    void entertainTheGuests();

    boolean waitForTheNextRace() throws InterruptedException;

    void goWatchTheRace() throws InterruptedException;

    void relaxABit();

    int reportResults();

    void summonHorsesToPaddock() throws InterruptedException;

    void proceedToPaddock();

    void goCheckHorses();

    void makeAMove();
}
