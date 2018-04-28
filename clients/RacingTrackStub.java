package clients;

import communications.Message;
import communications.MessageType;
import hippodrome.RacingTrackInterface;
import hippodrome.actions.Race;
import lib.communication.ClientCom;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static clients.configurations.RacingTrack.*;

public class RacingTrackStub implements RacingTrackInterface {
    @Override
    public void proceedToStartLine() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_PROCEED_TO_START_LINE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void makeAMove(int horseId) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_MAKE_A_MOVE, horseId);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public boolean hasFinishLineBeenCrossed(int horseJockeyId) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_HAS_FINISH_LINE_BEEN_CROSSED, horseJockeyId);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getValue();
    }

    @Override
    public void startTheRace() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_START_THE_RACE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public Race getRace() {
        Race race = null;
        ByteArrayInputStream objectByteArray = null;
        ObjectInputStream deseriablizedObject = null;
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_GET_RACE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        objectByteArray = new ByteArrayInputStream(messageReceived.getSerializedObject());
        try {
            deseriablizedObject = new ObjectInputStream(objectByteArray);
            race = (Race) deseriablizedObject.readObject();
        } catch (Exception e) {
            // TODO : handle this exception
        } finally {
            try {
                if (objectByteArray != null) {
                    objectByteArray.close();
                }
            } catch (IOException ioe) {
                // TODO : handle this exception
            }
        }
        return race;
    }

    @Override
    public int getWinner() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_GET_WINNER);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getWinner();
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
