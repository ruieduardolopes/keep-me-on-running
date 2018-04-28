package clients;

import communications.Message;
import communications.MessageType;
import hippodrome.StableInterface;
import lib.communication.ClientCom;

import static clients.configurations.Stable.*;

public class StableStub implements StableInterface {
    @Override
    public void proceedToStable(int raceNumber) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.STABLE_PROCEED_TO_STABLE_WITH_RACE_ID, raceNumber);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void proceedToStable() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.STABLE_PROCEED_TO_STABLE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void summonHorsesToPaddock(int raceNumber) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.STABLE_SUMMON_HORSES_TO_PADDOCK, raceNumber);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    private ClientCom createConnectionWithServer() {
        ClientCom connection = new ClientCom(HOST, PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                // TODO : Handle this exception
            }
        }
        return connection;
    }
}
