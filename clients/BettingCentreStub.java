package clients;

import communications.Message;
import communications.MessageType;
import entities.Broker;
import entities.Spectator;
import hippodrome.BettingCentreInterface;
import hippodrome.rollfilm.UnknownHorseJockeyException;
import lib.communication.ClientCom;

import static configurations.ServerConfigurations.BETTING_CENTRE_HOST;
import static configurations.ServerConfigurations.BETTING_CENTRE_PORT;
import static configurations.ServerConfigurations.BETTING_CENTRE_TIME_TO_SLEEP;

public class BettingCentreStub implements BettingCentreInterface {
    @Override
    public void acceptTheBets() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_ACCEPT_THE_BETS);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.REPLY_BETTING_CENTRE_ACCEPT_THE_BETS) {
            // TODO : Handle this error
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    @Override
    public void honourTheBets() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_HONOUR_THE_BETS);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.REPLY_BETTING_CENTRE_HONOUR_THE_BETS) {
            // TODO : Handle this error
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    @Override
    public int placeABet(int spectator, int bet, int horse) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_PLACE_A_BET, spectator, bet, horse);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.REPLY_BETTING_CENTRE_PLACE_A_BET) {
            // TODO : Handle this error
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
        return messageReceived.getBet();
    }

    @Override
    public int goCollectTheGains() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_GO_COLLECT_THE_GAINS);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.REPLY_BETTING_CENTRE_GO_COLLECT_THE_GAINS) {
            // TODO : Handle this error
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
        return messageReceived.getGains();
    }

    @Override
    public boolean haveIWon(int spectatorId) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_HAVE_I_WON);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getValue();
    }

    @Override
    public boolean areThereAnyWinners(int winner) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_ARE_THERE_ANY_WINNERS);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getValue();
    }

    @Override
    public int getNumberOfHorses() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_GET_NUMBER_OF_HORSES);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getHorses();
    }

    @Override
    public void setAbility(int horse, int ability) throws UnknownHorseJockeyException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_SET_ABILITY, horse, ability);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    private ClientCom createConnectionWithServer() {
        ClientCom connection = new ClientCom(BETTING_CENTRE_HOST, BETTING_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(BETTING_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                // TODO : Handle this exception
            }
        }
        return connection;
    }
}
