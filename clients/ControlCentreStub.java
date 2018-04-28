package clients;

import communications.Message;
import communications.MessageType;
import hippodrome.ControlCentreInterface;
import lib.communication.ClientCom;

import static clients.configurations.ControlCentre.*;

public class ControlCentreStub implements ControlCentreInterface {
    @Override
    public void startTheRace() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_START_THE_RACE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void entertainTheGuests() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_ENTERTAIN_THE_GUESTS);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public boolean waitForTheNextRace() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_WAIT_FOR_THE_NEXT_RACE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getValue();
    }

    @Override
    public void goWatchTheRace() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_GO_WATCH_THE_RACE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void relaxABit() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_RELAX_A_BIT);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public int reportResults() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_REPORT_RESULTS);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getResults();
    }

    @Override
    public void summonHorsesToPaddock() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void proceedToPaddock() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_PROCEED_TO_PADDOCK);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void goCheckHorses() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_GO_CHECK_HORSES);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void makeAMove() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_MAKE_A_MOVE);
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
