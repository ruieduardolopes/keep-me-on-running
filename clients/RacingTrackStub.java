package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.HorseJockey;
import hippodrome.RacingTrackInterface;
import hippodrome.actions.Race;
import lib.communication.ClientCom;
import lib.logging.Logger;

import static configurations.ServerConfigurations.RACING_TRACK_HOST;
import static configurations.ServerConfigurations.RACING_TRACK_PORT;
import static configurations.ServerConfigurations.RACING_TRACK_TIME_TO_SLEEP;

public class RacingTrackStub implements RacingTrackInterface {
    @Override
    public void proceedToStartLine() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_PROCEED_TO_START_LINE);
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
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

    @Override
    public void makeAMove(int horseId) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_MAKE_A_MOVE, horseId);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
        messageToSend.setAbility(((HorseJockey)Thread.currentThread()).getAbility());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_RACING_TRACK_MAKE_A_MOVE) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(messageReceived.getHorseJockeyState());
        connection.close();
    }

    @Override
    public boolean hasFinishLineBeenCrossed(int horseJockeyId) throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_HAS_FINISH_LINE_BEEN_CROSSED, horseJockeyId);
        if (horseJockeyId != 0) Logger.printNotification("Sending %s message to server for Horse with identification %d", messageToSend.getType(), horseJockeyId);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message for Horse with identification %d", messageReceived.getType(), horseJockeyId);
        connection.close();
        return messageReceived.getValue();
    }

    @Override
    public void startTheRace() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_START_THE_RACE);
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

    @Override
    public Race getRace() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_GET_RACE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        connection.close();
        return messageReceived.getRace();
    }

    @Override
    public void setRace(Race race) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_SET_RACE, race);
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

    @Override
    public int getWinner() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_GET_WINNER);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        connection.close();
        return messageReceived.getWinner();
    }

    public void shutdown() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.RACING_TRACK_SHUTDOWN);
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
        ClientCom connection = new ClientCom(RACING_TRACK_HOST, RACING_TRACK_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(RACING_TRACK_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {

                throw new InterruptedException("The Racing Track createConnectionWithServer() has been interrupted on its sleep().");
            }
        }
        return connection;
    }
}
