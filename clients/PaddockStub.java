package clients;

import communications.Message;
import communications.MessageType;
import entities.HorseJockey;
import entities.Spectator;
import hippodrome.PaddockInterface;
import lib.communication.ClientCom;

import static configurations.ServerConfigurations.PADDOCK_HOST;
import static configurations.ServerConfigurations.PADDOCK_PORT;
import static configurations.ServerConfigurations.PADDOCK_TIME_TO_SLEEP;

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
        if (messageReceived.getType() != MessageType.REPLY_PADDOCK_GO_CHECK_HORSES) {
            // TODO : Handle this error
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
    }

    @Override
    public void proceedToStartLine() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_PROCEED_TO_START_LINE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.REPLY_PADDOCK_PROCEED_TO_START_LINE) {
            // TODO : Handle this error
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(messageReceived.getHorseJockeyState());
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
        ClientCom connection = new ClientCom(PADDOCK_HOST, PADDOCK_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(PADDOCK_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                // TODO : Handle this exception
            }
        }
        return connection;
    }
}
