package clients;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.GeneralInformationRepositoryInterface;
import hippodrome.rollfilm.UnknownHorseJockeyException;
import hippodrome.rollfilm.UnknownSpectatorException;
import lib.communication.ClientCom;

public class GeneralInformationRepositoryStub implements GeneralInformationRepositoryInterface {

    @Override
    public void newSnapshot(boolean nullable) {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setRaceNumber(int number) {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setRaceDistance(int distance) {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setBrokerStatus(BrokerState status) {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setSpectatorStatus(int spectatorId, SpectatorState status) throws UnknownSpectatorException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setSpectatorAmountOfMoney(int spectatorId, int amount) throws UnknownSpectatorException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setSpectatorBetSelection(int spectatorId, int selection) throws UnknownSpectatorException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setSpectatorBetAmount(int spectatorId, int bet) throws UnknownSpectatorException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setHorseJockeyStatus(int horseJockeyId, HorseJockeyState status) throws UnknownHorseJockeyException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setHorseJockeyAbility(int horseJockeyId, int ability) throws UnknownHorseJockeyException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setHorseJockeyProbabilityToWin(int horseJockeyId, int probability) throws UnknownHorseJockeyException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setHorseJockeyNumberOfIncrementsDid(int horseJockeyId, int iterations) throws UnknownHorseJockeyException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public int getHorseJockeyNumberOfIncrementsDid(int horseJockeyId) throws UnknownHorseJockeyException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
        return 0;
    }

    @Override
    public void setHorseJockeyPositionOnTrack(int horseJockeyId, int position) throws UnknownHorseJockeyException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setHorseJockeyFinalStandPosition(int horseJockeyId, int position) throws UnknownHorseJockeyException {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public void setWereWaitingTheHorses(boolean value) {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    @Override
    public int getRaceNumber() {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
        return 0;
    }

    @Override
    public int getCurrentRaceDistance() {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
        return 0;
    }

    @Override
    public void raceIsOver() {
        ClientCom communicationChannel = createConnection();

        communicationChannel.close();
    }

    private ClientCom createConnection() {
        ClientCom communicationChannel = new ClientCom("localhost", ClientConfiguration.GENERAL_INFORMATION_REPOSITORY_PORT);
        while (!communicationChannel.open()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                ie.printStackTrace(); // TODO: handle this exception
            }
        }
        return communicationChannel;
    }
}
