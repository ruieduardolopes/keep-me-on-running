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
import java.util.ArrayList;
import java.util.Arrays;
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
    public BettingCentre(int numberOfHorses, int numberOfSpectators, GeneralInformationRepository repository) {
        this.numberOfHorses = numberOfHorses;
        this.numberOfSpectators = numberOfSpectators;
        this.bets = new Bet[numberOfSpectators];
        this.moneyOnSafe = 0;
        this.amountPerWinner = 0;
        this.bettingQueue = new LinkedBlockingQueue<>();
        this.repository = repository;
        this.winners = new int[numberOfSpectators];
        this.numberOfBetters = 0;
    }

    /**
     * Accept all the bets done by the {@link Spectator}s.
     */
    public synchronized void acceptTheBets() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
        while (bettingQueue.size() !=  numberOfSpectators) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(7);
            }
        }
        brokerHaveNotAcceptedTheBet = false;
        notifyAll();
        bettingQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Give all the money to the respective betting parts - the {@link Spectator}s.
     */
    public synchronized void honourTheBets() {
        ((Broker)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        while (allWinnersAreNotOnBettingCentre) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(12);
            }
        }
        winnersMustNotReceiveTheirMoney = false;
        notifyAll();
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
    public synchronized int placeABet(int spectator, int bet, int horse) {
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.PLACING_A_BET);
        bettingQueue.add(spectator);
        try {
            bets[spectator] = new Bet(horse, bet);
            repository.setSpectatorBetAmount(spectator, bet);
            repository.setSpectatorBetSelection(spectator, horse);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownSpectatorException(spectator);
        }
        moneyOnSafe += bet;
        notifyAll();
        while (brokerHaveNotAcceptedTheBet) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(8);
            }
        }
        return bet;
    }

    /**
     * Let a {@code spectator} collect all his (or hers) gains after being placed a bet and a race had ended.
     *
     * @param spectator the {@link Spectator} which had collected some money after his (or hers) bet.
     *
     * @return the amount of money collected by the {@code spectator}.
     */
    public synchronized int goCollectTheGains(int spectator) {
        ((Spectator)Thread.currentThread()).setSpectatorState(SpectatorState.COLLECTING_THE_GAINS);
        winnersArrived++;
        if (winnersArrived == winners.length) {
            allWinnersAreNotOnBettingCentre = false;
            notifyAll();
        }
        while (winnersMustNotReceiveTheirMoney) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(13);
            }
        }
        return amountPerWinner;
    }

    public synchronized void setHorseJockeyWinner(int winner) {
        if (winner != -1) {
            this.winner = winner;   // HJ winner
        }
    }

    /**
     * Verification if a given Spectator identified with {@code spectatorId} has won his (or hers) bet.
     *
     * @param spectatorId identification of {@link Spectator} who has placed a bet earlier and wants to verify if it did
     *                    win indeed or not.
     *
     * @return {@code true} if {@code spectator} has won his (or hers) bet; otherwise, it will return {@code false}.
     */
    public synchronized boolean haveIWon(int spectatorId) {
        for (int winner : winners) {
            if (winner == spectatorId) {
                return true;
            }
        }
        return false;
        // check if I won
        // done
    }

    /**
     * Verification of bet's winners.
     *
     * @return {@code true} if anybody had won indeed; otherwise it will return {@code false}.
     */
    public synchronized boolean areThereAnyWinners() {
        winnersArrived = 0;
        ArrayList<Integer> winningList = new ArrayList<>();
        for (int i = 0; i != bets.length; i++) {
            if (bets[i].getHorseJockeyId() == winner) {
                winningList.add(i);
            }
        }
        winners = new int[winningList.size()];
        for (int i = 0; i != winners.length; i++) {
            winners[i] = winningList.get(i);
        }
        if (winners.length != 0) {
            amountPerWinner = moneyOnSafe / winners.length;
        } else {
            amountPerWinner = 0;
        }
        return winners.length != 0;
        // done
    }

    /**
     *
     * @return
     */
    public synchronized int getNumberOfHorses() {
        return numberOfHorses;
    }

    /**
     * The waiting queue to contact the {@link Broker} on its tasks. This could be used while waiting to a {@link Spectator}
     * place a bet, or while wainting to a {@link Spectator} go collect his (or hers) gains. This structure is granted by a
     * {@link LinkedBlockingQueue} Java class, which performs a blocking queue under the logic of a linked list.
     */
    private Queue<Integer> bettingQueue = null;

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

    /**
     * Instance of the global repository of information given by the {@code Simulation}.
     */
    private GeneralInformationRepository repository;

    private int numberOfBetters;

    private boolean lastSpectatorHasNotBetted = true;

    private boolean brokerIsNotAcceptingBets = true;

    private boolean brokerStillHasToPayToSpectators = true;

    private boolean brokerStillHasToHonorTheBets = true;

    private int winner;

    private boolean brokerHaveNotAcceptedTheBet = true;

    private boolean winnersMustNotReceiveTheirMoney = true;

    private boolean allWinnersAreNotOnBettingCentre = true;

    private int winnersArrived = 0;
}
