package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.Broker;
import entities.HorseJockey;
import entities.Spectator;
import hippodrome.RacingTrackInterface;
import hippodrome.actions.Race;
import lib.communication.ClientCom;
import lib.logging.Logger;

import static configurations.ServerConfigurations.RACING_TRACK_HOST;
import static configurations.ServerConfigurations.RACING_TRACK_PORT;
import static configurations.ServerConfigurations.RACING_TRACK_TIME_TO_SLEEP;

/**
 * Representation of a place where the {@link HorseJockey}s run to win!
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class RacingTrackStub implements RacingTrackInterface {

    /**
     * Stub entity to represent the will of a {@link HorseJockey} to Proceed to Start Line of
     * the {@link hippodrome.RacingTrack}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to represent the will of a {@link HorseJockey} to Make a Move on the
     * the {@link hippodrome.RacingTrack}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to represent whether if a {@link HorseJockey} Finished the Race on the
     * the {@link hippodrome.RacingTrack}.
     *
     * @return a boolean representing whether if the the finishing line has been crossed.
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to represent the will of the {@link Broker} to Start the Race on
     * the {@link hippodrome.RacingTrack}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to represent the will of a {@link HorseJockey} to Get the current {@link Race}
     * the {@link hippodrome.RacingTrack}.
     *
     * @return the current {@link Race} happening on the {@link hippodrome.RacingTrack}
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to represent the will of a {@link HorseJockey} to Set the current {@link Race}
     * the {@link hippodrome.RacingTrack}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to represent the will of a {@link HorseJockey} to Get the winner identification of the {@link Race}
     * the {@link hippodrome.RacingTrack}.
     *
     * @return the identification of the winner of the {@link Race} as an int.
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
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

    /**
     * Stub entity to shutdown the premises of the {@link hippodrome.RacingTrack}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     *         unexpected reply message has been received.
     */
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

    /**
     * Creates a connection to the correspondent server (meaning the {@link hippodrome.RacingTrack}).
     *
     * @return the communication with the server, already defined over a {@link ClientCom} object.
     * @throws InterruptedException if a connection could not be established with success.
     */
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
