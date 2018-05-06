package hippodrome;

/**
 * Interface of the place where the horses rest waiting their turn to enter the competition.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public interface StableInterface {
    void proceedToStable(int raceNumber) throws InterruptedException;

    void proceedToStable() throws InterruptedException ;

    void summonHorsesToPaddock(int raceNumber) throws InterruptedException;
}
