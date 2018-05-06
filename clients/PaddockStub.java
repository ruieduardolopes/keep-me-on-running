package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.HorseJockey;
import entities.Spectator;
import hippodrome.PaddockInterface;
import lib.communication.ClientCom;
import lib.logging.Logger;

import static configurations.ServerConfigurations.PADDOCK_HOST;
import static configurations.ServerConfigurations.PADDOCK_PORT;
import static configurations.ServerConfigurations.PADDOCK_TIME_TO_SLEEP;

/**
 * Representation of a place where the {@link HorseJockey}s show themselves to the {@link Spectator}s.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class PaddockStub implements PaddockInterface {

    /**
     * Stub entity to represent the will of a {@link HorseJockey} to Proceed to the {@link hippodrome.Paddock}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void proceedToPaddock(int raceNumber) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_PROCEED_TO_PADDOCK, raceNumber);
        Logger.printNotification("Sending %s message to server with argument (raceNumber: %d)", messageToSend.getType(), raceNumber);
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
     * Stub entity to represent the will of the {@link Spectator}s to go Check The {@link HorseJockey}s on the {@link hippodrome.Paddock}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void goCheckHorses(boolean isTheLastSpectator) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR, isTheLastSpectator);
        Logger.printNotification("Sending %s message to server with argument (isTheLastSpectator: %s)", messageToSend.getType(), isTheLastSpectator);
        messageToSend.setSpectatorID(((Spectator)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((Spectator)Thread.currentThread()).setSpectatorState(messageReceived.getSpectatorState());
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link HorseJockey}s to
     * Proceed to the Starting Line of the {@link hippodrome.RacingTrack}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void proceedToStartLine() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_PROCEED_TO_START_LINE);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        messageToSend.setHorseID(((HorseJockey)Thread.currentThread()).getIdentification());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        if (messageReceived.getType() != MessageType.REPLY_PADDOCK_PROCEED_TO_START_LINE) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(messageReceived.getHorseJockeyState());
        connection.close();
    }

    /**
     * Stub entity to represent the will of the last {@link Spectator} to go Check The {@link HorseJockey}s on the {@link hippodrome.Paddock}.
     *
     * @return a boolean representing the this is the last {@link Spectator}.
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public boolean goCheckHorses() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_GO_CHECK_HORSES);
        Logger.printNotification("Sending %s message to server", messageToSend.getType());
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        Logger.printInformation("Received a %s message", messageReceived.getType());
        connection.close();
        return messageReceived.getValue();
    }

    /**
     * Stub entity to shutdown the premises of the {@link hippodrome.Paddock}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     *         unexpected reply message has been received.
     */
    public void shutdown() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.PADDOCK_SHUTDOWN);
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
     * Creates a connection to the correspondent server (meaning the {@link hippodrome.Paddock}).
     *
     * @return the communication with the server, already defined over a {@link ClientCom} object.
     * @throws InterruptedException if a connection could not be established with success.
     */
    private ClientCom createConnectionWithServer() throws InterruptedException {
        ClientCom connection = new ClientCom(PADDOCK_HOST, PADDOCK_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(PADDOCK_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                throw new InterruptedException("The Paddock createConnectionWithServer() has been interrupted on its sleep().");
            }
        }
        return connection;
    }
}
