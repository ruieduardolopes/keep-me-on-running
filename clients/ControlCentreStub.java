package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.Broker;
import entities.HorseJockey;
import entities.Spectator;
import hippodrome.ControlCentreInterface;
import lib.communication.ClientCom;
import lib.logging.Logger;

import static configurations.ServerConfigurations.CONTROL_CENTRE_HOST;
import static configurations.ServerConfigurations.CONTROL_CENTRE_PORT;
import static configurations.ServerConfigurations.CONTROL_CENTRE_TIME_TO_SLEEP;

public class ControlCentreStub implements ControlCentreInterface {
    @Override
    public void startTheRace() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_START_THE_RACE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_START_THE_RACE) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    @Override
    public void entertainTheGuests() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_ENTERTAIN_THE_GUESTS);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_ENTERTAIN_THE_GUESTS) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    @Override
    public boolean waitForTheNextRace() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_WAIT_FOR_THE_NEXT_RACE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setSpectatorID(((Spectator)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_WAIT_FOR_NEXT_RACE) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
        return messageReceived.getValue();
    }

    @Override
    public void goWatchTheRace() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_GO_WATCH_THE_RACE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setSpectatorID(((Spectator)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message (spectator thread number %d)", messageReceived.getType(), ((Spectator)Thread.currentThread()).getIdentification());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_GO_WATCH_THE_RACE) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
    }

    @Override
    public void relaxABit() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_RELAX_A_BIT);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setSpectatorID(((Spectator)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_RELAX_A_BIT) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
    }

    @Override
    public int reportResults() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_REPORT_RESULTS);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        connection.close();
        return messageReceived.getResults();
    }

    @Override
    public void summonHorsesToPaddock() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    @Override
    public void proceedToPaddock() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_PROCEED_TO_PADDOCK);
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_PROCEED_TO_PADDOCK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(messageReceived.getHorseJockeyState());
        connection.close();
    }

    @Override
    public void goCheckHorses() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_GO_CHECK_HORSES);
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
    public void makeAMove() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_MAKE_A_MOVE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
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
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_SHUTDOWN);
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
        ClientCom connection = new ClientCom(CONTROL_CENTRE_HOST, CONTROL_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(CONTROL_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                throw new InterruptedException("The Control Centre createConnectionWithServer() has been interrupted on its sleep().");
            }
        }
        return connection;
    }
}
