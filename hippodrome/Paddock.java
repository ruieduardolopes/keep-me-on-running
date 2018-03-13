package hippodrome;

/**
 * Place where the horses are paradded before the {@link entities.Spectator}s. As the
 * pairs Horse/Jockey comes here, it means that each and everyone of them is getting prepared
 * to be on the next race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 1.0
 */
public class Paddock {
    /**
     * Signal given by the {@link entities.Broker} in order to proceed to here (the {@link Paddock}).
     * This signal is given calling only the pairs Horse/Jockey which are supposed to run on the next {@link concepts.Race},
     * identified by the number {@code raceNumber}.
     *
     * @param raceNumber number identification of the race which is about to begin.
     */
    public void proceedToPaddock(int raceNumber) {

    }

    /**
     * Signal given by the {@link entities.Broker} in order to the {@link entities.Spectator}s go check the
     * pairs Horse/Jockey. This must be done in order to establish a more accurate bet on the probable winner.
     */
    public void goCheckHorses() {

    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses from the {@link Stable} to the
     * {@link Paddock}.
     */
    public void summonHorsesToPaddock() {

    }
}
