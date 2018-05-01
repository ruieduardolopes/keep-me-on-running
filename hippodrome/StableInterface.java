package hippodrome;

public interface StableInterface {
    void proceedToStable(int raceNumber) throws InterruptedException;

    void proceedToStable();

    void summonHorsesToPaddock(int raceNumber);
}