package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.Broker;
import entities.HorseJockey;
import entities.Spectator;
import hippodrome.StableInterface;
import lib.communication.ClientCom;
import lib.logging.Logger;

import static configurations.ServerConfigurations.STABLE_HOST;
import static configurations.ServerConfigurations.STABLE_PORT;
import static configurations.ServerConfigurations.STABLE_TIME_TO_SLEEP;

/**
 * Representation of a place where the {@link HorseJockey}s rest before and after each {@link hippodrome.actions.Race}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class StableStub implements StableInterface {

    /**
     * Stub entity to represent the will of a {@link HorseJockey} to Proceed to
     * the {@link hippodrome.Stable}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to represent the will of the last {@link HorseJockey} to Proceed to
     * the {@link hippodrome.Stable}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to represent the will of the {@link Broker} to Proceed to the Paddock on
     * the {@link hippodrome.Stable}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to shutdown the premises of the {@link hippodrome.Stable}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     *         unexpected reply message has been received.
     */
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

    /**
     * Creates a connection to the correspondent server (meaning the {@link hippodrome.Stable}).
     *
     * @return the communication with the server, already defined over a {@link ClientCom} object.
     * @throws InterruptedException if a connection could not be established with success.
     */
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
