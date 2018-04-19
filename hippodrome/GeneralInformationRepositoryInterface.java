package hippodrome;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.rollfilm.UnknownHorseJockeyException;
import hippodrome.rollfilm.UnknownSpectatorException;

public interface GeneralInformationRepositoryInterface {
    void newSnapshot(boolean nullable);

    void setRaceNumber(int number);

    void setRaceDistance(int distance);

    void setBrokerStatus(BrokerState status);

    void setSpectatorStatus(int spectatorId, SpectatorState status) throws UnknownSpectatorException;

    void setSpectatorAmountOfMoney(int spectatorId, int amount) throws UnknownSpectatorException;

    void setSpectatorBetSelection(int spectatorId, int selection) throws UnknownSpectatorException;

    void setSpectatorBetAmount(int spectatorId, int bet) throws UnknownSpectatorException;

    void setHorseJockeyStatus(int horseJockeyId, HorseJockeyState status) throws UnknownHorseJockeyException;

    void setHorseJockeyAbility(int horseJockeyId, int ability) throws UnknownHorseJockeyException;

    void setHorseJockeyProbabilityToWin(int horseJockeyId, int probability) throws UnknownHorseJockeyException;

    void setHorseJockeyNumberOfIncrementsDid(int horseJockeyId, int iterations) throws UnknownHorseJockeyException;

    int getHorseJockeyNumberOfIncrementsDid(int horseJockeyId) throws UnknownHorseJockeyException;

    void setHorseJockeyPositionOnTrack(int horseJockeyId, int position) throws UnknownHorseJockeyException;

    void setHorseJockeyFinalStandPosition(int horseJockeyId, int position) throws UnknownHorseJockeyException;

    void setWereWaitingTheHorses(boolean value);

    int getRaceNumber();

    int getCurrentRaceDistance();

    void raceIsOver();
}
