package hippodrome;

import clients.GeneralInformationRepositoryStub;
import configurations.RMIConfigurations;
import configurations.ServerConfigurations;
import entities.*;
import hippodrome.actions.Bet;
import hippodrome.responses.Response;
import hippodrome.responses.ResponseType;
import hippodrome.rollfilm.UnknownHorseJockeyException;
import hippodrome.rollfilm.UnknownSpectatorException;

/**
 * This class needs a Queue implementation in order to accomplish the creation of such a
 * structure which could represent a set of {@link Spectator}s waiting to be received by a
 * {@link Broker}, to place a bet or to collect its further gains. For this matter we use
 * an already tested version of a Queue implementation, as the Java's one.
 *
 * Further documentation on this matter could be accessed here: {@link Queue}.
 */
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static configurations.SimulationConfigurations.*;

/**
 * Place where the {@link Spectator}s place their bets on the winning horse. As they come here
 * to place their bets, they also come here to collect his (or hers) further gains, if such a
 * scenario applies on a given race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 2.0
 */
public class BettingCentre implements BettingCentreInterface {

    /**
     * Creates a Betting Centre.
     * <br>
     * This constructor creates a Betting Centre giving a number of pairs Horse/Jockeys, a number of Spectators. Plus,
     * an instance of the repository is also given in order to report status changes on the course of its actions.
     *
     * @param numberOfHorses the number of pairs Horse/Jockeys which will compete against one another.
     * @param numberOfSpectators the number of Spectators which will attend the events.
     *
     */
    private BettingCentre(int numberOfHorses, int numberOfSpectators) {
        this.numberOfHorses = numberOfHorses;
        this.numberOfSpectators = numberOfSpectators;
        this.bets = new Bet[numberOfSpectators];
        this.moneyOnSafe = 0;
        this.bettingQueue = new LinkedBlockingQueue<>();
        //this.repository = new GeneralInformationRepositoryStub();
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(RMIConfigurations.RMI_HOSTNAME, RMIConfigurations.RMI_PORT_REGISTRY);
            this.repository = (GeneralInformationRepositoryInterface) registry.lookup(ServerConfigurations.GENERAL_INFORMATION_REPOSITORY_HOST);
        } catch (RemoteException | NotBoundException re) {
            re.printStackTrace();
        }
        this.winners = new int[numberOfSpectators];
        this.horsesOdds = new int[numberOfHorses];
        this.horsesAbilities = new int[numberOfHorses];
    }

    /**
     * Get a singleton instance of a Betting Centre.
     *
     * @return an instance of the Betting Centre.
     */
    public static BettingCentre getInstance() {
        if (instance == null) {
            instance = new BettingCentre(NUMBER_OF_PAIRS_HORSE_JOCKEY, NUMBER_OF_SPECTATORS);
        }
        return instance;
    }

    /**
     * Changes the state of the Entities to Waiting for Bets ({@code WFB}) and waits till the last
     * {@link BettingCentre#placeABet(int, int, int)} is done by a Spectator.
     * <br>
     * This method also notifies the Spectators when the bets are accepted.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void acceptTheBets() throws InterruptedException {
        evaluateOdds();
        brokerDoesNotAllowToProceedToHaveIWon = true;
        allWinnersAreNotOnBettingCentre = true;
        ((ServiceProviderAgent)Thread.currentThread()).setBrokerState(BrokerState.WAITING_FOR_BETS);
        repository.setBrokerStatus(BrokerState.WAITING_FOR_BETS);
        while (bettingQueue.size() !=  numberOfSpectators) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The acceptTheBets() has been interrupted on its wait().");
            }
        }
        brokerHaveNotAcceptedTheBet = false;
        notifyAll();
        bettingQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Changes the state of the Entities to Settling Accounts ({@code SA}) and waits till the last
     * {@link BettingCentre#goCollectTheGains()} is done by a Spectator.
     * <br>
     * This method also notifies the Spectators when the Entities is about to give their money.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void honourTheBets() throws InterruptedException {
        ((ServiceProviderAgent)Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        repository.setBrokerStatus(BrokerState.SETTLING_ACCOUNTS);
        while (allWinnersAreNotOnBettingCentre) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The honourTheBets() has been interrupted on its wait().");
            }
        }
        winnersMustNotReceiveTheirMoney = false;
        notifyAll();
    }

    /**
     * Changes the Spectator's state to Placing a Bet ({@code PAB}) and lets a {@link Spectator} place a {@code bet}
     * onto the {@link BettingCentre}.
     * <br>
     * This {@code bet}, made by a specific {@code spectator} is done respecting a proper
     * {@code horse}, which represents a pair Horse/Jockey.
     * <br>
     * After all the Spectators have placed their bets, the Entities will then accept them.
     *
     * @param spectator an identification of a {@link Spectator} which represents a better.
     * @param bet an amount of money represented as an integer, which the {@code spectator} wants to bet.
     * @param horse the identification of the pair Horse/Jockey in which the {@code spectator} wants to bet.
     *
     * @throws InterruptedException if the wait() is interrupted.
     *
     * @return the amount of money which was accepted by the {@link Broker} to place the bet.
     */
    public synchronized Response placeABet(int spectator, int bet, int horse) throws InterruptedException {
        //((ServiceProviderAgent)Thread.currentThread()).setSpectatorState(SpectatorState.PLACING_A_BET);
        repository.setSpectatorStatus(spectator, SpectatorState.PLACING_A_BET);
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
                throw new InterruptedException("The placeABet() has been interrupted on its wait().");
            }
        }
        return new Response(ResponseType.BETTING_CENTRE_PLACE_A_BET, bet, SpectatorState.PLACING_A_BET);
    }

    /**
     * Changes the Spectator's state to Collecting the Gains ({@code CTG}) and lets a {@code spectator} collect all
     * his (or hers) gains after being placed a bet and a race had ended.
     * <br>
     * After the Entities alerted that its honouring the bets, each Spectator must then execute this method in order
     * to collect its gains.
     *
     * @throws InterruptedException if the wait() is interrupted.
     *
     * @return the amount of money collected by the {@code spectator}, as an integer.
     */
    public synchronized int goCollectTheGains() throws InterruptedException {
        ServiceProviderAgent agent = ((ServiceProviderAgent)Thread.currentThread());
        agent.setSpectatorState(SpectatorState.COLLECTING_THE_GAINS);
        repository.setSpectatorStatus(agent.getSpectatorIdentification(), SpectatorState.COLLECTING_THE_GAINS);
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
                throw new InterruptedException("The goCollectTheGains() has been interrupted on its wait().");
            }
        }
        return bets[agent.getSpectatorIdentification()].getAmount()*horsesOdds[bets[agent.getSpectatorIdentification()].getHorseJockeyId()];
    }

    /**
     * Verification if a given Spectator identified with {@code spectatorId} has won his (or hers) bet.
     *
     * @param spectatorId identification of {@link Spectator} who has placed a bet earlier and wants to verify if it did
     *                    win indeed or not.
     *
     * @return {@code true} if {@code spectator} has won his (or hers) bet; otherwise, it will return {@code false}.
     */
    public synchronized boolean haveIWon(int spectatorId) throws InterruptedException {
        while (brokerDoesNotAllowToProceedToHaveIWon) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The haveIWon() has been interrupted on its wait().");
            }
        }

        for (int winner : winners) {
            if (winner == spectatorId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verification of bet's winners.
     *
     * @param winner the identification of the pair Horse/Jockey which have won the race.
     *
     * @return {@code true} if anybody had won indeed; otherwise it will return {@code false}.
     */
    public synchronized boolean areThereAnyWinners(int winner) {

        winnersArrived = 0;
        ArrayList<Integer> winningList = new ArrayList<>();
        for (int i = 0; i != bets.length; i++) {
            if (bets[i].getHorseJockeyId() == winner) {
                winningList.add(i);
            }
        }

        for (int i = 0; i != bets.length; i++) {

        }
        winners = new int[winningList.size()];
        for (int i = 0; i != winners.length; i++) {
            winners[i] = winningList.get(i);
        }
        brokerDoesNotAllowToProceedToHaveIWon = false;
        notifyAll();
        return winners.length != 0;
    }

    /**
     * Returns number of horses that the {@link BettingCentre} has knowledge of.
     *
     * @return number of horses, as an integer.
     */
    public synchronized int getNumberOfHorses() {
        return numberOfHorses;
    }

    /**
     * Sets the ability of a pair Horse/Jockey on a internal structure in order to evaluate its odds later.
     *
     * @param horse the identification of the pair Horse/Jockey.
     * @param ability the ability of the pair Horse/Jockey identified with {@code horse}.
     *
     * @throws UnknownHorseJockeyException if the pair Horse/Jockey {@code horse} does not exists.
     */
    public synchronized void setAbility(int horse, int ability) throws UnknownHorseJockeyException {
       try {
           horsesAbilities[horse] = ability;
       } catch (IndexOutOfBoundsException ioobe) {
           ioobe.printStackTrace();
           throw new UnknownHorseJockeyException(horse);
       }
    }

    /**
     * Evaluates the odds of all the pair Horse/Jockey.
     * @throws InterruptedException if the communication channel is busy.
     */
    private void evaluateOdds() throws InterruptedException {
        try {
            for (int i = 0; i != horsesOdds.length; i++) {
                horsesOdds[i] = sumOf(horsesAbilities) / horsesAbilities[i];
                repository.setHorseJockeyProbabilityToWin(i, horsesOdds[i]);
            }
        } catch (InterruptedException e) {
            throw new InterruptedException();
        }
    }

    /**
     * Retrieves the sum of all elements of an array {@code array}.
     *
     * @param array the array with the elements to sum.
     *
     * @return the value of the sum of all elements of the array {@code array}.
     */
    private int sumOf(int[] array) {
        int sum = 0;
        for (int i = 0; i != array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    /**
     * The waiting queue to contact the {@link Broker} on its tasks. This could be used while waiting to a {@link Spectator}
     * place a bet, or while wainting to a {@link Spectator} go collect his (or hers) gains. This structure is granted by a
     * {@link LinkedBlockingQueue} Java class, which performs a blocking queue under the logic of a linked list.
     */
    private Queue<Integer> bettingQueue;

    /**
     * Internal structure of bets. This is an array of bets (issued by an entity called {@link Bet}). Here, each index
     * is related to a specific {@link Spectator} with its identification number. This is applicable since we consider
     * that the {@code Spectators} have sequential numbers.
     */
    private Bet[] bets;

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
     * Array with the pairs Horse/Jockey odds.
     */
    private int[] horsesOdds;

    /**
     * Array with the pairs Horse/Jockey abilities
     */
    private int[] horsesAbilities;

    /**
     * Condition variable for noticing when Entities have not accepted the bets yet.
     * <br>
     * This is a condition variable of the {@link BettingCentre#placeABet(int, int, int)} method.
     */
    private boolean brokerHaveNotAcceptedTheBet = true;

    /**
     * Condition variable for noticing when Spectators must receive their money or not, if they have won the bet.
     * <br>
     * This is a condition variable of the {@link BettingCentre#goCollectTheGains()} method.
     */
    private boolean winnersMustNotReceiveTheirMoney = true;

    /**
     * Condition variable for noticing when Spectators (winner ones) are at the Betting Centre or not.
     * <br>
     * This is a condition variable of the {@link BettingCentre#honourTheBets()} method and it is reset on the
     * {@link BettingCentre#acceptTheBets()} method.
     */
    private boolean allWinnersAreNotOnBettingCentre = true;

    /**
     * Condition variable for noticing when Spectators must wait or not, to see if they've won.
     * <br>
     * This is a condition variable of the {@link BettingCentre#haveIWon(int)} method and it is reset on the
     * {@link BettingCentre#acceptTheBets()} method.
     */
    private boolean brokerDoesNotAllowToProceedToHaveIWon = true;

    /**
     * Condition variable for noticing the number of winning Spectators who arrived at this Betting Centre.
     * <br>
     * This is a condition variable of the {@link BettingCentre#goCollectTheGains()} and it is reset on the
     * {@link BettingCentre#areThereAnyWinners(int)} method.
     */
    private int winnersArrived = 0;

    /**
     * The {@link GeneralInformationRepository} instance where all this region's actions will be reported.
     */
    private GeneralInformationRepositoryInterface repository;

    /**
     * The created instance of this class
     */
    private static BettingCentre instance;
}
