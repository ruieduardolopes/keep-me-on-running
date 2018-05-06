package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.Broker;
import entities.Spectator;
import hippodrome.BettingCentreInterface;
import hippodrome.rollfilm.UnknownHorseJockeyException;
import lib.communication.ClientCom;
import lib.logging.Logger;

import static configurations.ServerConfigurations.BETTING_CENTRE_HOST;
import static configurations.ServerConfigurations.BETTING_CENTRE_PORT;
import static configurations.ServerConfigurations.BETTING_CENTRE_TIME_TO_SLEEP;

public class BettingCentreStub implements BettingCentreInterface {
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
