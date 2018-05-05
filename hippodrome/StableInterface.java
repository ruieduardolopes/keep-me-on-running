package hippodrome;

public interface StableInterface {
    void proceedToStable(int raceNumber) throws InterruptedException;

    void proceedToStable() throws InterruptedException ;

    void summonHorsesToPaddock(int raceNumber);
}
