package hippodrome;

import entities.Spectator;
import hippodrome.responses.Response;

import java.rmi.Remote;

/**
 * Interface of the place where the {@link Spectator}s place their bets on the winning horse. As they come here
 * to place their bets, they also come here to collect his (or hers) further gains, if such a
 * scenario applies on a given race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public interface BettingCentreInterface extends Remote {

    Response acceptTheBets() throws Exception;

    Response honourTheBets() throws Exception;

    Response placeABet(int spectator, int bet, int horse) throws Exception;

    Response goCollectTheGains(int spectator) throws Exception;

    boolean haveIWon(int spectatorId) throws Exception;

    boolean areThereAnyWinners(int winner) throws Exception;

    int getNumberOfHorses() throws Exception;

    void setAbility(int horse, int ability) throws Exception;

    public int getNumberOfEntitiesDeclaringExit() throws Exception;
}
