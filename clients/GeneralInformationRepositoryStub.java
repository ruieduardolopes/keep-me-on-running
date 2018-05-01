package clients;

import communications.Message;
import communications.MessageType;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.GeneralInformationRepositoryInterface;
import hippodrome.rollfilm.UnknownHorseJockeyException;
import hippodrome.rollfilm.UnknownSpectatorException;
import lib.communication.ClientCom;

import static configurations.ServerConfiguration.GENERAL_INFORMATION_REPOSITORY_HOST;
import static configurations.ServerConfiguration.GENERAL_INFORMATION_REPOSITORY_PORT;
import static configurations.ServerConfiguration.GENERAL_INFORMATION_REPOSITORY_TIME_TO_SLEEP;

public class GeneralInformationRepositoryStub implements GeneralInformationRepositoryInterface {
    @Override
    public void newSnapshot(boolean nullable) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_NEW_SNAPSHOT);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setRaceNumber(int number) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_RACE_NUMBER, number);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setRaceDistance(int distance) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_RACE_DISTANCE, distance);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setBrokerStatus(BrokerState status) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_BROKER_STATUS, status);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setSpectatorStatus(int spectatorId, SpectatorState status) throws UnknownSpectatorException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_STATUS, spectatorId, status);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setSpectatorAmountOfMoney(int spectatorId, int amount) throws UnknownSpectatorException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_AMOUNT_OF_MONEY, spectatorId, amount);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setSpectatorBetSelection(int spectatorId, int selection) throws UnknownSpectatorException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_SELECTION, spectatorId, selection);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setSpectatorBetAmount(int spectatorId, int bet) throws UnknownSpectatorException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_AMOUNT, spectatorId, bet);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setHorseJockeyStatus(int horseJockeyId, HorseJockeyState status) throws UnknownHorseJockeyException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_STATUS, horseJockeyId, status);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setHorseJockeyAbility(int horseJockeyId, int ability) throws UnknownHorseJockeyException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_ABILITY, horseJockeyId, ability);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setHorseJockeyProbabilityToWin(int horseJockeyId, int probability) throws UnknownHorseJockeyException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_PROBABILITY_TO_WIN, horseJockeyId, probability);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setHorseJockeyNumberOfIncrementsDid(int horseJockeyId, int iterations) throws UnknownHorseJockeyException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID, horseJockeyId, iterations);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public int getHorseJockeyNumberOfIncrementsDid(int horseJockeyId) throws UnknownHorseJockeyException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_GET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID, horseJockeyId);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getIncrements();
    }

    @Override
    public void setHorseJockeyPositionOnTrack(int horseJockeyId, int position) throws UnknownHorseJockeyException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_POSITION_ON_TRACK, horseJockeyId, position);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setHorseJockeyFinalStandPosition(int horseJockeyId, int position) throws UnknownHorseJockeyException {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_FINAL_STAND_POSITION, horseJockeyId, position);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public void setWereWaitingTheHorses(boolean value) {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_SET_WERE_WAITING_THE_HORSES, value);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    @Override
    public int getRaceNumber() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_GET_RACE_NUMBER);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getRaceNumber();
    }

    @Override
    public int getCurrentRaceDistance() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_GET_CURRENT_RACE_DISTANCE);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        connection.close();
        return messageReceived.getRaceDistance();
    }

    @Override
    public void raceIsOver() {
        ClientCom connection = createConnectionWithServer();
        Message messageToSend = new Message(MessageType.GENERAL_INFORMATION_REPOSITORY_RACE_IS_OVER);
        connection.writeObject(messageToSend);
        Message messageReceived = (Message) connection.readObject();
        if (messageReceived.getType() != MessageType.OK) {
            // TODO : Handle this error
        }
        connection.close();
    }

    private ClientCom createConnectionWithServer() {
        ClientCom connection = new ClientCom(GENERAL_INFORMATION_REPOSITORY_HOST, GENERAL_INFORMATION_REPOSITORY_PORT);
        while (!connection.open()) {
            try {
                Thread.sleep(GENERAL_INFORMATION_REPOSITORY_TIME_TO_SLEEP);
            } catch (InterruptedException ie) {
                // TODO : Handle this exception
            }
        }
        return connection;
    }
}
