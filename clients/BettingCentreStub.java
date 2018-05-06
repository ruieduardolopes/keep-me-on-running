package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.Broker;
import entities.Spectator;
import hippodrome.BettingCentreInterface;
import lib.communication.ClientCom;
import lib.logging.Logger;

import static configurations.ServerConfigurations.BETTING_CENTRE_HOST;
import static configurations.ServerConfigurations.BETTING_CENTRE_PORT;
import static configurations.ServerConfigurations.BETTING_CENTRE_TIME_TO_SLEEP;

/**
 * Representation of a place where the {@link Spectator}s place their bets on the winning horse. As they come here
 * to place their bets, they also come here to collect his (or hers) further gains, if such a
 * scenario applies on a given race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class BettingCentreStub implements BettingCentreInterface {
    /**
     * Stub entity to represent the will of a {@link Broker} to Accept the Bets on
     * the {@link hippodrome.BettingCentre}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void acceptTheBets() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_ACCEPT_THE_BETS);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_BETTING_CENTRE_ACCEPT_THE_BETS) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    /**
     * Stub entity to represent the will of a {@link Broker} to Honour the Bets on
     * the {@link hippodrome.BettingCentre}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void honourTheBets() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_HONOUR_THE_BETS);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_BETTING_CENTRE_HONOUR_THE_BETS) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    /**
     * Stub entity to represent the will of a {@link Spectator} to Place a Bet on
     * the {@link hippodrome.BettingCentre}.
     *
     * @return the amount of money bet.
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public int placeABet(int spectator, int bet, int horse) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_PLACE_A_BET, spectator, bet, horse);
        Logger.printNotification("Sending %s message to server with arguments (id:%d, bet:%d, horse:%d)", messageToSend.getType(), spectator, bet, horse);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_BETTING_CENTRE_PLACE_A_BET) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
        return messageReceived.getBet();
    }

    /**
     * Stub entity to represent the will of a {@link Spectator} to Collect the Gains on
     * the {@link hippodrome.BettingCentre}.
     *
     * @return the gains made with the bet, which the Spectator wants to collect.
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public int goCollectTheGains() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_GO_COLLECT_THE_GAINS);
        messageToSend.setSpectatorID(((Spectator)Thread.currentThread()).getIdentification());
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_BETTING_CENTRE_GO_COLLECT_THE_GAINS) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
        return messageReceived.getGains();
    }

    /**
     * Stub entity to represent the will of a {@link Spectator} to check if they've won a bet on
     * the {@link hippodrome.BettingCentre}.
     *
     * @return {@code true} if the Spectator {@code spectatorId} has won; otherwise {@code false}.
     * @throws InterruptedException if a connection could not be established with success.
     */
    @Override
    public boolean haveIWon(int spectatorId) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_HAVE_I_WON, spectatorId);
        Logger.printNotification("Sending %s message to server (spectatorID:%d)", messageToSend.getType(), spectatorId);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message saying: Have I won? %s", messageReceived.getType(), messageReceived.getValue());
        connection.close();
        return messageReceived.getValue();
    }

    /**
     * Stub entity to represent the will of a {@link Broker} to check if anyone has won a bet on
     * the {@link hippodrome.BettingCentre}.
     *
     * @return {@code true} if there is a bet winner; otherwise {@code false}.
     * @throws InterruptedException if a connection could not be established with success.
     */
    @Override
    public boolean areThereAnyWinners(int winner) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_ARE_THERE_ANY_WINNERS, winner);
        Logger.printNotification("Sending %s message to server with argument (winner:%d)", messageToSend.getType(), winner);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        connection.close();
        return messageReceived.getValue();
    }

    /**
     * Stub entity to get the number of pairs Horse/Jockey, as registered in the {@link hippodrome.BettingCentre}.
     *
     * @return the number of pairs Horse/Jockey.
     * @throws InterruptedException if a connection could not be established with success.
     */
    @Override
    public int getNumberOfHorses() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_GET_NUMBER_OF_HORSES);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        connection.close();
        return messageReceived.getHorses();
    }

    /**
     * Stub entity to set the ability of a pair Horse/Jockey, to be registered in the {@link hippodrome.BettingCentre}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     *         unexpected reply message has been received.
     */
    @Override
    public void setAbility(int horse, int ability) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_SET_ABILITY, horse, ability);
        Logger.printNotification("Sending %s message to server with arguments (horse:%d, ability:%d)", messageToSend.getType(), horse, ability);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to shutdown the premises of the {@link hippodrome.BettingCentre}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     *         unexpected reply message has been received.
     */
    public void shutdown() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_SHUTDOWN);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Creates a connection to the correspondent server (meaning the {@link hippodrome.BettingCentre}).
     *
     * @return the communication with the server, already defined over a {@link ClientCom} object.
     * @throws InterruptedException if a connection could not be established with success.
     */
    private ClientCom createConnectionWithServer() throws InterruptedException {
        ClientCom connection = new ClientCom(BETTING_CENTRE_HOST, BETTING_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(BETTING_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                throw new InterruptedException("The Betting Centre createConnectionWithServer() has been interrupted on its sleep().");
            }
        }
        return connection;
    }
}
