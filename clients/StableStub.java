package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.HorseJockey;
import hippodrome.StableInterface;
import lib.communication.ClientCom;
import lib.logging.Logger;

import static configurations.ServerConfigurations.STABLE_HOST;
import static configurations.ServerConfigurations.STABLE_PORT;
import static configurations.ServerConfigurations.STABLE_TIME_TO_SLEEP;

public class StableStub implements StableInterface {
    @Override
    public void proceedToStable(int raceNumber) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.STABLE_PROCEED_TO_STABLE_WITH_RACE_ID, raceNumber);
        Logger.printNotification("Sending %s message to server with argument (raceNumber:%d)", messageToSend.getType(), raceNumber);
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_STABLE_PROCEED_TO_STABLE_WITH_RACE_ID) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(messageReceived.getHorseJockeyState());
        connection.close();
    }

    @Override
    public void proceedToStable() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.STABLE_PROCEED_TO_STABLE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_STABLE_PROCEED_TO_STABLE) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(messageReceived.getHorseJockeyState());
        connection.close();
    }

    @Override
    public void summonHorsesToPaddock(int raceNumber) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.STABLE_SUMMON_HORSES_TO_PADDOCK, raceNumber);
        Logger.printNotification("Sending %s message to server with argument (race:%d)", messageToSend.getType(), raceNumber);
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
        Message messageToSend = new Message(MessageType.STABLE_SHUTDOWN);
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
        ClientCom connection = new ClientCom(STABLE_HOST, STABLE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(STABLE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                throw new InterruptedException("The Stable createConnectionWithServer() has been interrupted on its sleep().");
            }
        }
        return connection;
    }
}
