package communications;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.*;
import hippodrome.actions.Race;

/**
 * Definition of the various types of the Messages processed in {@link Message}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public enum MessageType {
    /** Message representing that everything is fine */
    OK,

    /** Message representing a shutdown of the Betting Centre */
    BETTING_CENTRE_SHUTDOWN,

    /** Message representing a shutdown of the Control Centre */
    CONTROL_CENTRE_SHUTDOWN,

    /** Message representing a shutdown of the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SHUTDOWN,

    /** Message representing a shutdown of the Paddock */
    PADDOCK_SHUTDOWN,

    /** Message representing a shutdown of the Racing Track */
    RACING_TRACK_SHUTDOWN,

    /** Message representing a shutdown of the Stable */
    STABLE_SHUTDOWN,

    /** Message representing the {@link BettingCentre#acceptTheBets()} function on the Betting Centre */
    BETTING_CENTRE_ACCEPT_THE_BETS,

    /** Message representing the {@link BettingCentre#honourTheBets()} function on the Betting Centre */
    BETTING_CENTRE_HONOUR_THE_BETS,

    /** Message representing the {@link BettingCentre#placeABet(int, int, int)} function on the Betting Centre */
    BETTING_CENTRE_PLACE_A_BET,

    /** Message representing the {@link BettingCentre#goCollectTheGains()} function on the Betting Centre */
    BETTING_CENTRE_GO_COLLECT_THE_GAINS,

    /** Message representing the {@link BettingCentre#haveIWon(int)} function on the Betting Centre */
    BETTING_CENTRE_HAVE_I_WON,

    /** Message representing the {@link BettingCentre#areThereAnyWinners(int)} function on the Betting Centre */
    BETTING_CENTRE_ARE_THERE_ANY_WINNERS,

    /** Message representing the {@link BettingCentre#getNumberOfHorses()} function on the Betting Centre */
    BETTING_CENTRE_GET_NUMBER_OF_HORSES,

    /** Message representing the {@link BettingCentre#setAbility(int, int)} function on the Betting Centre */
    BETTING_CENTRE_SET_ABILITY,

    /** Message representing the {@link ControlCentre#startTheRace()} function on the Control Centre */
    CONTROL_CENTRE_START_THE_RACE,

    /** Message representing the {@link ControlCentre#entertainTheGuests()} function on the Control Centre */
    CONTROL_CENTRE_ENTERTAIN_THE_GUESTS,

    /** Message representing the {@link ControlCentre#waitForTheNextRace()} function on the Control Centre */
    CONTROL_CENTRE_WAIT_FOR_THE_NEXT_RACE,

    /** Message representing the {@link ControlCentre#goWatchTheRace()} function on the Control Centre */
    CONTROL_CENTRE_GO_WATCH_THE_RACE,

    /** Message representing the {@link ControlCentre#relaxABit()} function on the Control Centre */
    CONTROL_CENTRE_RELAX_A_BIT,

    /** Message representing the {@link ControlCentre#reportResults()} function on the Control Centre */
    CONTROL_CENTRE_REPORT_RESULTS,

    /** Message representing the {@link ControlCentre#summonHorsesToPaddock()} function on the Control Centre */
    CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK,

    /** Message representing the {@link ControlCentre#proceedToPaddock()} function on the Control Centre */
    CONTROL_CENTRE_PROCEED_TO_PADDOCK,

    /** Message representing the {@link ControlCentre#goCheckHorses()} function on the Control Centre */
    CONTROL_CENTRE_GO_CHECK_HORSES,

    /** Message representing the {@link ControlCentre#makeAMove()} function on the Control Centre */
    CONTROL_CENTRE_MAKE_A_MOVE,

    /** Message representing the {@link GeneralInformationRepository#newSnapshot()} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_NEW_SNAPSHOT,

    /** Message representing the {@link GeneralInformationRepository#setRaceNumber(int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_RACE_NUMBER,

    /** Message representing the {@link GeneralInformationRepository#setRaceDistance(int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_RACE_DISTANCE,

    /** Message representing the {@link GeneralInformationRepository#setBrokerStatus(BrokerState)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_BROKER_STATUS,

    /** Message representing the {@link GeneralInformationRepository#setSpectatorStatus(int, SpectatorState)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_STATUS,

    /** Message representing the {@link GeneralInformationRepository#setSpectatorAmountOfMoney(int, int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_AMOUNT_OF_MONEY,

    /** Message representing the {@link GeneralInformationRepository#setSpectatorBetSelection(int, int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_SELECTION,

    /** Message representing the {@link GeneralInformationRepository#setSpectatorBetAmount(int, int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_AMOUNT,

    /** Message representing the {@link GeneralInformationRepository#setHorseJockeyStatus(int, HorseJockeyState)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_STATUS,

    /** Message representing the {@link GeneralInformationRepository#setHorseJockeyAbility(int, int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_ABILITY,

    /** Message representing the {@link GeneralInformationRepository#setHorseJockeyProbabilityToWin(int, int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_PROBABILITY_TO_WIN,

    /** Message representing the {@link GeneralInformationRepository#setHorseJockeyNumberOfIncrementsDid(int, int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID,

    /** Message representing the {@link GeneralInformationRepository#getHorseJockeyNumberOfIncrementsDid(int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_GET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID,

    /** Message representing the {@link GeneralInformationRepository#setHorseJockeyPositionOnTrack(int, int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_POSITION_ON_TRACK,

    /** Message representing the {@link GeneralInformationRepository#setHorseJockeyFinalStandPosition(int, int)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_FINAL_STAND_POSITION,

    /** Message representing the {@link GeneralInformationRepository#setWereWaitingTheHorses(boolean)} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_SET_WERE_WAITING_THE_HORSES,

    /** Message representing the {@link GeneralInformationRepository#getRaceNumber()} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_GET_RACE_NUMBER,

    /** Message representing the {@link GeneralInformationRepository#getCurrentRaceDistance()} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_GET_CURRENT_RACE_DISTANCE,

    /** Message representing the {@link GeneralInformationRepository#raceIsOver()} function on the General Repository of Information */
    GENERAL_INFORMATION_REPOSITORY_RACE_IS_OVER,

    /** Message representing the {@link Paddock#proceedToPaddock(int)} function on the Paddock */
    PADDOCK_PROCEED_TO_PADDOCK,

    /** Message representing the {@link Paddock#goCheckHorses(boolean)} function on the Paddock */
    PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR,

    /** Message representing the {@link Paddock#proceedToStartLine()} function on the Paddock */
    PADDOCK_PROCEED_TO_START_LINE,

    /** Message representing the {@link Paddock#goCheckHorses()} function on the Paddock */
    PADDOCK_GO_CHECK_HORSES,

    /** Message representing the {@link RacingTrack#proceedToStartLine()} function on the Racing Track */
    RACING_TRACK_PROCEED_TO_START_LINE,

    /** Message representing the {@link RacingTrack#makeAMove(int)} function on the Racing Track */
    RACING_TRACK_MAKE_A_MOVE,

    /** Message representing the {@link RacingTrack#hasFinishLineBeenCrossed(int)} function on the Racing Track */
    RACING_TRACK_HAS_FINISH_LINE_BEEN_CROSSED,

    /** Message representing the {@link RacingTrack#startTheRace()} function on the Racing Track */
    RACING_TRACK_START_THE_RACE,

    /** Message representing the {@link RacingTrack#getRace()} function on the Racing Track */
    RACING_TRACK_GET_RACE,

    /** Message representing the {@link RacingTrack#setRace(Race)} function on the Racing Track */
    RACING_TRACK_SET_RACE,

    /** Message representing the {@link RacingTrack#getWinner()} function on the Racing Track */
    RACING_TRACK_GET_WINNER,

    /** Message representing the {@link Stable#proceedToStable(int)} function on the Stable */
    STABLE_PROCEED_TO_STABLE_WITH_RACE_ID,

    /** Message representing the {@link Stable#proceedToStable()} function on the Stable */
    STABLE_PROCEED_TO_STABLE,

    /** Message representing the {@link Stable#summonHorsesToPaddock(int)} function on the Stable */
    STABLE_SUMMON_HORSES_TO_PADDOCK,

    /** Message representing the reply of the {@link BettingCentre#placeABet(int, int, int)} function on the Betting Centre */
    REPLY_BETTING_CENTRE_PLACE_A_BET,

    /** Message representing the reply of the {@link BettingCentre#areThereAnyWinners(int)} function on the Betting Centre */
    REPLY_BETTING_CENTRE_ARE_THERE_ANY_WINNERS,

    /** Message representing the reply of the {@link BettingCentre#getNumberOfHorses()} function on the Betting Centre */
    REPLY_BETTING_CENTRE_GET_NUMBER_OF_HORSES,

    /** Message representing the reply of the {@link BettingCentre#goCollectTheGains()} function on the Betting Centre */
    REPLY_BETTING_CENTRE_GO_COLLECT_THE_GAINS,

    /** Message representing the reply of the {@link BettingCentre#haveIWon(int)} function on the Betting Centre */
    REPLY_BETTING_CENTRE_HAVE_I_WON,

    /** Message representing the reply of the {@link ControlCentre#reportResults()} function on the Control Centre */
    REPLY_CONTROL_CENTRE_REPORT_RESULTS,

    /** Message representing the reply of the {@link ControlCentre#waitForTheNextRace()} function on the Control Centre */
    REPLY_CONTROL_CENTRE_WAIT_FOR_NEXT_RACE,

    /** Message representing the reply of the {@link GeneralInformationRepository#getCurrentRaceDistance()} function on the General Repository of Information */
    REPLY_GENERAL_INFORMATION_REPOSITORY_GET_CURRENT_RACE_DISTANCE,

    /** Message representing the reply of the {@link GeneralInformationRepository#getHorseJockeyNumberOfIncrementsDid(int)} function on the General Repository of Information */
    REPLY_GENERAL_INFORMATION_REPOSITORY_GET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID,

    /** Message representing the reply of the {@link GeneralInformationRepository#getRaceNumber()} function on the General Repository of Information */
    REPLY_GENERAL_INFORMATION_REPOSITORY_GET_RACE_NUMBER,

    /** Message representing the reply of the {@link Paddock#goCheckHorses(boolean)} function on the Paddock */
    REPLY_PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR,

    /** Message representing the reply of the {@link RacingTrack#getRace()} function on the Racing Track */
    REPLY_RACING_TRACK_GET_RACE,

    /** Message representing the reply of the {@link RacingTrack#hasFinishLineBeenCrossed(int)} function on the Racing Track */
    REPLY_RACING_TRACK_HAS_FINISH_LINE_BEEN_CROSSED,

    /** Message representing the reply of the {@link RacingTrack#getWinner()} function on the Racing Track */
    REPLY_RACING_TRACK_GET_WINNER,

    /** Message representing the reply of the {@link ControlCentre#summonHorsesToPaddock()} function on the Control Centre */
    REPLY_CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK,

    /** Message representing the reply of the {@link BettingCentre#acceptTheBets()} function on the Betting Centre */
    REPLY_BETTING_CENTRE_ACCEPT_THE_BETS,

    /** Message representing the reply of the {@link BettingCentre#honourTheBets()} function on the Betting Centre */
    REPLY_BETTING_CENTRE_HONOUR_THE_BETS,

    /** Message representing the reply of the {@link ControlCentre#startTheRace()} function on the Control Centre */
    REPLY_CONTROL_CENTRE_START_THE_RACE,

    /** Message representing the reply of the {@link ControlCentre#entertainTheGuests()} function on the Control Centre */
    REPLY_CONTROL_CENTRE_ENTERTAIN_THE_GUESTS,

    /** Message representing the reply of the {@link ControlCentre#relaxABit()} function on the Control Centre */
    REPLY_CONTROL_CENTRE_RELAX_A_BIT,

    /** Message representing the reply of the {@link ControlCentre#proceedToPaddock()} function on the Control Centre */
    REPLY_CONTROL_CENTRE_PROCEED_TO_PADDOCK,

    /** Message representing the reply of the {@link Paddock#proceedToStartLine()} function on the Paddock */
    REPLY_PADDOCK_PROCEED_TO_START_LINE,

    /** Message representing the reply of the {@link RacingTrack#makeAMove(int)} function on the Racing Track */
    REPLY_RACING_TRACK_MAKE_A_MOVE,

    /** Message representing the reply of the {@link Stable#proceedToStable()} function on the Stable */
    REPLY_STABLE_PROCEED_TO_STABLE,

    /** Message representing the reply of the {@link Stable#proceedToStable(int)} function on the Stable */
    REPLY_STABLE_PROCEED_TO_STABLE_WITH_RACE_ID,

    /** Message representing the reply of the {@link ControlCentre#reportResults()} function on the Control Centre */
    REPLY_CONTROL_CENTRE_GO_WATCH_THE_RACE

}
