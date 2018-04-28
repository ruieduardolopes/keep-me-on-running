package clients;

import communications.Message;
import communications.MessageType;
import hippodrome.BettingCentreInterface;
import hippodrome.rollfilm.UnknownHorseJockeyException;
import lib.communication.ClientCom;

public class BettingCentreStub implements BettingCentreInterface {
    @Override
    public void acceptTheBets() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_ACCEPT_THE_BETS);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void honourTheBets() throws InterruptedException {

    }

    @Override
    public int placeABet(int spectator, int bet, int horse) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.BETTING_CENTRE_PLACE_A_BET, spectator, bet, horse);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        return messageReceived.getBet();
    }

    @Override
    public int goCollectTheGains() throws InterruptedException {
        return 0;
    }

    @Override
    public boolean haveIWon(int spectatorId) {
        return false;
    }

    @Override
    public boolean areThereAnyWinners(int winner) {
        return false;
    }

    @Override
    public int getNumberOfHorses() {
        return 0;
    }

    @Override
    public void setAbility(int horse, int ability) throws UnknownHorseJockeyException {

    }

    private ClientCom createConnectionWithServer() {
        ClientCom connection = new ClientCom(config.host, config.repoServerPort);
        while (!connection.open()) {
            try {
                Thread.sleep(config.timeSleep);
            } catch (InterruptedException ie) {
                // TODO : Handle this exception
            }
        }
        return connection;
    }
}
