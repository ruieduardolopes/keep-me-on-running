package clients;

import communications.Message;
import communications.MessageType;
import communications.UnexpectedReplyTypeException;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.GeneralInformationRepositoryInterface;
import lib.communication.ClientCom;
import lib.logging.Logger;
import static configurations.ServerConfigurations.GENERAL_INFORMATION_REPOSITORY_HOST;
import static configurations.ServerConfigurations.GENERAL_INFORMATION_REPOSITORY_PORT;
import static configurations.ServerConfigurations.GENERAL_INFORMATION_REPOSITORY_TIME_TO_SLEEP;

/**
 * Representation of a place where the {@link entities} log their state transitions.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class GeneralInformationRepositoryStub implements GeneralInformationRepositoryInterface {

    /**
     * Stub entity to represent the will of the {@link entities.Broker} to issue a New Snapshot on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void newSnapshot(boolean nullable) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_NEW_SNAPSHOT);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link hippodrome.RacingTrack} to issue a New Race Number on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setRaceNumber(int number) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_RACE_NUMBER, number);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link hippodrome.RacingTrack} to issue a New Race Distance on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setRaceDistance(int distance) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_RACE_DISTANCE, distance);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.Broker},{@link hippodrome.BettingCentre}
     * and {@link hippodrome.ControlCentre}
     * to issue a New {@link entities.BrokerState} on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setBrokerStatus(BrokerState status) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_BROKER_STATUS, status);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.Spectator},{@link hippodrome.BettingCentre},
     * {@link hippodrome.ControlCentre} and {@link hippodrome.Paddock}
     * to issue a New {@link entities.SpectatorState} on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setSpectatorStatus(int spectatorId, SpectatorState status) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_STATUS, spectatorId, status);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.Spectator} to Set its Amount of Money on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setSpectatorAmountOfMoney(int spectatorId, int amount) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_AMOUNT_OF_MONEY, spectatorId, amount);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.Spectator} to Set its Bet Selection of Money on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setSpectatorBetSelection(int spectatorId, int selection) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_SELECTION, spectatorId, selection);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.Spectator} to Set its Bet Amount of Money on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setSpectatorBetAmount(int spectatorId, int bet) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_AMOUNT, spectatorId, bet);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.HorseJockey},{@link hippodrome.Stable},
     * {@link hippodrome.ControlCentre}, {@link hippodrome.RacingTrack} and {@link hippodrome.Paddock}
     * to issue a New {@link entities.HorseJockeyState} on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setHorseJockeyStatus(int horseJockeyId, HorseJockeyState status) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_STATUS, horseJockeyId, status);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.HorseJockey} to Set its Ability on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setHorseJockeyAbility(int horseJockeyId, int ability) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_ABILITY, horseJockeyId, ability);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.HorseJockey} to Set its Probability to Win on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setHorseJockeyProbabilityToWin(int horseJockeyId, int probability) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_PROBABILITY_TO_WIN, horseJockeyId, probability);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.HorseJockey} to Set its Number of Increments performed on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setHorseJockeyNumberOfIncrementsDid(int horseJockeyId, int iterations) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID, horseJockeyId, iterations);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.HorseJockey} to Get its Number of Increments performed on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @return an int representing the Number of Increments performed by the passed {@link entities.HorseJockey} id.
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public int getHorseJockeyNumberOfIncrementsDid(int horseJockeyId) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_GET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID, horseJockeyId);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getIncrements();
    }

    /**
     * Stub entity to represent the will of the {@link entities.HorseJockey} to Set its Position on the {@link hippodrome.RacingTrack} on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setHorseJockeyPositionOnTrack(int horseJockeyId, int position) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_POSITION_ON_TRACK, horseJockeyId, position);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.HorseJockey} to Set its Final Position on the {@link hippodrome.RacingTrack} on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setHorseJockeyFinalStandPosition(int horseJockeyId, int position) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_FINAL_STAND_POSITION, horseJockeyId, position);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the will of the {@link entities.HorseJockey} to wait For All the Horses on
     * the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void setWereWaitingTheHorses(boolean value) throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_WERE_WAITING_THE_HORSES, value);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to represent the get Race Number provided by the {@link hippodrome.GeneralInformationRepository}.
     *
     * @return an int representing the current {@link hippodrome.actions.Race} number.
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public int getRaceNumber() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_GET_RACE_NUMBER);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getRaceNumber();
    }

    /**
     * Stub entity to represent the get Current Race Distance provided by the {@link hippodrome.GeneralInformationRepository}.
     *
     * @return an int representing the current {@link hippodrome.actions.Race} distance.
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public int getCurrentRaceDistance() throws InterruptedException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_GET_CURRENT_RACE_DISTANCE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getRaceDistance();
    }

    /**
     * Stub entity to represent the affirmation that the Race the is Over provided by the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     * unexpected reply message has been received.
     */
    @Override
    public void raceIsOver() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_RACE_IS_OVER);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            Logger.printError("Received a unexpected reply type (%s)", messageReceived.getType());
            throw new UnexpectedReplyTypeException(messageReceived.getType());
        }
        connection.close();
    }

    /**
     * Stub entity to shutdown the premises of the {@link hippodrome.GeneralInformationRepository}.
     *
     * @throws InterruptedException if a connection could not be established with success.
     * @throws RuntimeException (more precisely a {@link UnexpectedReplyTypeException}) if a
     *         unexpected reply message has been received.
     */
    public void shutdown() throws InterruptedException, RuntimeException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SHUTDOWN);
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
     * Creates a connection to the correspondent server (meaning the {@link hippodrome.GeneralInformationRepository}).
     *
     * @return the communication with the server, already defined over a {@link ClientCom} object.
     * @throws InterruptedException if a connection could not be established with success.
     */
    private ClientCom createConnectionWithServer() throws InterruptedException {
        ClientCom connection = new ClientCom(GENERAL_INFORMATION_REPOSITORY_HOST, GENERAL_INFORMATION_REPOSITORY_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(GENERAL_INFORMATION_REPOSITORY_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                throw new InterruptedException("The Repository createConnectionWithServer() has been interrupted on its sleep().");
            }
        }
        return connection;
    }
}
