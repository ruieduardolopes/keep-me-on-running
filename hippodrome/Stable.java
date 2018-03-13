package hippodrome;

/**
 * Place where the horses rest waiting their turn to enter the competition.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 1.0
 */
public class Stable {
    /**
     * Signal given by the {@link entities.Broker} in order to allow the pair Horse/Jockey to move
     * to this place (the {@link Stable}).
     */
    public void proceedToStable() {

    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses of the {@code raceNumber} race, from the
     * {@link Stable} to the {@link Paddock}.
     *
     * @param raceNumber number identification of the next race, where the called pairs Horse/Jockey will be competing
     *                   against each other.
     */
    public void summonHorsesToPaddock(int raceNumber) {

    }
}
