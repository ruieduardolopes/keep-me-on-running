package clients;

import communications.Message;
import communications.MessageType;
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
    public void startTheRace() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_START_THE_RACE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_START_THE_RACE) {
            // TODO : Handle this error
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    @Override
    public void entertainTheGuests() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_ENTERTAIN_THE_GUESTS);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_ENTERTAIN_THE_GUESTS) {
            // TODO : Handle this error
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    @Override
    public boolean waitForTheNextRace() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_WAIT_FOR_THE_NEXT_RACE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setSpectatorID(((Spectator)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_WAIT_FOR_NEXT_RACE) {
            // TODO : Handle this error
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
        return messageReceived.getValue();
    }

    @Override
    public void goWatchTheRace() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_GO_WATCH_THE_RACE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setSpectatorID(((Spectator)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message (spectator thread number %d)", messageReceived.getType(), ((Spectator)Thread.currentThread()).getIdentification());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_GO_WATCH_THE_RACE) {
            // TODO : Handle this error
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();

    //
    }

    @Override
    public void relaxABit() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_RELAX_A_BIT);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setSpectatorID(((Spectator)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_RELAX_A_BIT) {
            // TODO : Handle this error
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
    }

    @Override
    public int reportResults() {
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
    public void summonHorsesToPaddock() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK) {
            // TODO : Handle this error
        }
        ((Broker)Thread.currentThread()).setBrokerState(messageReceived.getBrokerState());
        connection.close();
    }

    @Override
    public void proceedToPaddock() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_PROCEED_TO_PADDOCK);
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_CONTROL_CENTRE_PROCEED_TO_PADDOCK) {
            // TODO : Handle this error
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(messageReceived.getHorseJockeyState());
        connection.close();
    }

    @Override
    public void goCheckHorses() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_GO_CHECK_HORSES);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void makeAMove() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.CONTROL_CENTRE_MAKE_A_MOVE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    private ClientCom createConnectionWithServer() {
        ClientCom connection = new ClientCom(CONTROL_CENTRE_HOST, CONTROL_CENTRE_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(CONTROL_CENTRE_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                // TODO : Handle this exception
            }
        }
        return connection;
    }
}
