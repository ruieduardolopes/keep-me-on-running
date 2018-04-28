package clients;

import communications.Message;
import communications.MessageType;
import hippodrome.PaddockInterface;
import lib.communication.ClientCom;

import static clients.configurations.Paddock.*;

public class PaddockStub implements PaddockInterface {
    @Override
    public void proceedToPaddock(int raceNumber) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_PROCEED_TO_PADDOCK, raceNumber);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void goCheckHorses(boolean isTheLastSpectator) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR, isTheLastSpectator);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void proceedToStartLine() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_PROCEED_TO_START_LINE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public boolean goCheckHorses() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_GO_CHECK_HORSES);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getValue();
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
