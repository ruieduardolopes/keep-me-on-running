package hippodrome;

import entities.*;
import hippodrome.actions.Bet;
import hippodrome.registry.UnknownSpectatorException;

/**
 * This class needs a Queue implementation in order to accomplish the creation of such a
 * structure which could represent a set of {@link Spectator}s waiting to be received by a
 * {@link Broker}, to place a bet or to collect its further gains. For this matter we use
 * an already tested version of a Queue implementation, as the Java's one.
 *
 * Further documentation on this matter could be accessed here: {@link Queue}.
 */
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Place where the {@link Spectator}s place their bets on the winning horse. As they come here
 * to place their bets, they also come here to collect his (or hers) further gains, if such a
 * scenario applies on a given race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 0.2
 */
public class BettingCentre {
    public BettingCentre(int numberOfHorses, int numberOfSpectators) {
        this.numberOfHorses = numberOfHorses;
        this.numberOfSpectators = numberOfSpectators;
        this.bets = new Bet[numberOfSpectators];
        this.moneyOnSafe = 0;
        this.amountPerWinner = 0;
        this.bettingQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Accept all the bets done by the {@link Spectator}s.
     */
    public void acceptTheBets() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
    }

    /**
     * Give all the money to the respective betting parts - the {@link Spectator}s.
     */
    public void honourTheBets() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
    }

    /**
     * Let a {@link Spectator} place a {@code bet} onto the {@link BettingCentre}.
     * This {@code bet}, made by a specific {@code spectator} is done respecting a proper
     * {@code horse}, which represents a pair Horse/Jockey.
     *
     * @param spectator an identification of a {@link Spectator} which represents a better.
     * @param bet an amount of money represented as an integer, which the {@code spectator} wants to bet.
     * @param horse the identification of the pair Horse/Jockey in which the {@code spectator} wants to bet.
     *
     * @return the amount of money which was accepted by the {@link Broker} to place the bet.
     */
    public int placeABet(int spectator, int bet, int horse) {
        try {
            bets[spectator] = new Bet(horse, bet);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownSpectatorException(spectator);
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.PLACING_A_BET);
        return 0;
    }

    /**
     * Let a {@code spectator} collect all his (or hers) gains after being placed a bet and a race had ended.
     *
     * @param spectator the {@link Spectator} which had collected some money after his (or hers) bet.
     *
     * @return the amount of money collected by the {@code spectator}.
     */
    public int goCollectTheGains(int spectator) {
        for (int person : winners) {
            if (spectator == person) {
                moneyOnSafe -= amountPerWinner;
                return amountPerWinner;
            }
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.COLLECTING_THE_GAINS);
        moneyOnSafe = 0;
        amountPerWinner = 0;
        return 0;
    }

    /**
     * Verification of bet's winners.
     *
     * @return {@code true} if anybody had won indeed; otherwise it will return {@code false}.
     */
    public boolean areThereAnyWinners() {
        return winners.length != 0;
    }

    /**
     * The waiting queue to contact the {@link Broker} on its tasks. This could be used while waiting to a {@link Spectator}
     * place a bet, or while wainting to a {@link Spectator} go collect his (or hers) gains. This structure is granted by a
     * {@link LinkedBlockingQueue} Java class, which performs a blocking queue under the logic of a linked list.
     */
    private Queue<Spectator> bettingQueue = null;

    /**
     * Internal structure of bets. This is an array of bets (issued by an entity called {@link Bet}). Here, each index
     * is related to a specific {@link Spectator} with its identification number. This is applicable since we consider
     * that the {@code Spectators} have sequential numbers.
     */
    private Bet[] bets = null;

    /**
     * Array of {@code Spectator}'s identification numbers to whom the betting money should go. Its length represents the
     * number of winners and its content is the set of {@code Spectator}'s IDs of who won the bet.
     */
    private int[] winners;

    /**
     * Internal knowledge of the number of horses which are competing on track.
     */
    private int numberOfHorses;

    /**
     * Internal knowledge of the number of spectators which are attendind and betting on the races.
     */
    private int numberOfSpectators;

    /**
     * Internal attribute of the Betting Centre which represents the amount of money which we have on our account, to
     * perform the bets from the {@code Spectator}'s.
     */
    private int moneyOnSafe;

    /**
     * Internal attribute of the Betting Centre which must contain the amount of money which should be given per winner,
     * on a bet.
     */
    private int amountPerWinner;
}
