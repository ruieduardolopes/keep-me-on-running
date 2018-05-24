package hippodrome;

import java.rmi.Remote;

/**
 * Interface of the place where the horses are paradded before the {@link entities.Spectator}s. As the
 * pairs Horse/Jockey comes here, it means that each and everyone of them is getting prepared
 * to be on the next race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public interface PaddockInterface extends Remote {

    void proceedToPaddock(int raceNumber) throws InterruptedException;

    void goCheckHorses(boolean isTheLastSpectator) throws InterruptedException;

    void proceedToStartLine() throws InterruptedException;

    boolean goCheckHorses() throws InterruptedException;
}
