package hippodrome;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.rollfilm.UnknownHorseJockeyException;
import hippodrome.rollfilm.UnknownSpectatorException;

public interface GeneralInformationRepositoryInterface {
    void newSnapshot(boolean nullable) throws InterruptedException;

    void setRaceNumber(int number) throws InterruptedException;

    void setRaceDistance(int distance) throws InterruptedException;

    void setBrokerStatus(BrokerState status) throws InterruptedException, RuntimeException;

    void setSpectatorStatus(int spectatorId, SpectatorState status) throws InterruptedException, RuntimeException;

    void setSpectatorAmountOfMoney(int spectatorId, int amount) throws InterruptedException, RuntimeException;

    void setSpectatorBetSelection(int spectatorId, int selection) throws InterruptedException, RuntimeException;

    void setSpectatorBetAmount(int spectatorId, int bet) throws InterruptedException, RuntimeException;

    void setHorseJockeyStatus(int horseJockeyId, HorseJockeyState status) throws InterruptedException, RuntimeException;

    void setHorseJockeyAbility(int horseJockeyId, int ability) throws InterruptedException, RuntimeException;

    void setHorseJockeyProbabilityToWin(int horseJockeyId, int probability) throws InterruptedException, RuntimeException;

    void setHorseJockeyNumberOfIncrementsDid(int horseJockeyId, int iterations) throws InterruptedException, RuntimeException;

    int getHorseJockeyNumberOfIncrementsDid(int horseJockeyId) throws InterruptedException, RuntimeException;

    void setHorseJockeyPositionOnTrack(int horseJockeyId, int position) throws InterruptedException, RuntimeException;

    void setHorseJockeyFinalStandPosition(int horseJockeyId, int position) throws InterruptedException, RuntimeException;

    void setWereWaitingTheHorses(boolean value) throws InterruptedException;

    int getRaceNumber() throws InterruptedException;

    int getCurrentRaceDistance() throws InterruptedException;

    void raceIsOver() throws InterruptedException;
}
