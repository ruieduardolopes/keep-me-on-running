package hippodrome;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of the class which represents an archive of all hippodrome's actions
 * and memorabilia. This item allows the entities playing at an
 * hippodrome to collect information about the various statuses
 * and snapshots all the instants while something is happening.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public interface GeneralInformationRepositoryInterface extends Remote {

    void newSnapshot(boolean nullable) throws Exception;

    void setRaceNumber(int number) throws Exception;

    void setRaceDistance(int distance) throws Exception;

    void setBrokerStatus(BrokerState status) throws Exception;

    void setSpectatorStatus(int spectatorId, SpectatorState status) throws Exception;

    void setSpectatorAmountOfMoney(int spectatorId, int amount) throws Exception;

    void setSpectatorBetSelection(int spectatorId, int selection) throws Exception;

    void setSpectatorBetAmount(int spectatorId, int bet) throws Exception;

    void setHorseJockeyStatus(int horseJockeyId, HorseJockeyState status) throws Exception;

    void setHorseJockeyAbility(int horseJockeyId, int ability) throws Exception;

    void setHorseJockeyProbabilityToWin(int horseJockeyId, int probability) throws Exception;

    void setHorseJockeyNumberOfIncrementsDid(int horseJockeyId, int iterations) throws Exception;

    int getHorseJockeyNumberOfIncrementsDid(int horseJockeyId) throws Exception;

    void setHorseJockeyPositionOnTrack(int horseJockeyId, int position) throws Exception;

    void setHorseJockeyFinalStandPosition(int horseJockeyId, int position) throws Exception;

    void setWereWaitingTheHorses(boolean value) throws Exception;

    int getRaceNumber() throws Exception;

    int getCurrentRaceDistance() throws Exception;

    void raceIsOver() throws Exception;
}
