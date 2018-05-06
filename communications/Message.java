package communications;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.actions.Race;

import java.io.Serializable;

/**
 * Definition of the processing of information relative to the Message's exchanges between clients
 * (also known as {@code entities}) and the servers (also known as {@code hippodrome regions}).
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class Message implements Serializable {
    /**
     * Constructs a message which is only defined by its type (as an object of type {@link MessageType}).
     *
     * @param type the type of this message.
     */
    public Message(MessageType type) {
        this.type = type;
    }

    /**
     * Constructs a message which is defined by its type (as an object of type {@link MessageType})
     * and a boolean value.
     *
     * @param type the type of this message.
     * @param value the {@code boolean} value which must be saved with the message.
     */
    public Message(MessageType type, boolean value) {
        this.type = type;
        switch (type) {
            case GENERAL_INFORMATION_REPOSITORY_SET_WERE_WAITING_THE_HORSES:
                this.value = value;
                break;
            case PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR:
                this.value = value;
                break;
            case REPLY_BETTING_CENTRE_ARE_THERE_ANY_WINNERS:
                this.value = value;
                break;
            case REPLY_BETTING_CENTRE_HAVE_I_WON:
                this.value = value;
                break;
            case REPLY_CONTROL_CENTRE_WAIT_FOR_NEXT_RACE:
                this.value = value;
                break;
            case REPLY_PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR:
                this.value = value;
                break;
            case REPLY_RACING_TRACK_HAS_FINISH_LINE_BEEN_CROSSED:
                this.value = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a message which is defined by its type (as an object of type {@link MessageType})
     * and one integer value.
     *
     * @param type the type of this message.
     * @param value the {@code integer} value which must be saved with the message.
     */
    public Message(MessageType type, int value) {
        this.type = type;
        switch (type) {
            case BETTING_CENTRE_HAVE_I_WON:
                spectatorID = value;
                break;
            case BETTING_CENTRE_ARE_THERE_ANY_WINNERS:
                winner = value;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_RACE_NUMBER:
                raceNumber = value;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_RACE_DISTANCE:
                raceDistance = value;
                break;
            case GENERAL_INFORMATION_REPOSITORY_GET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID:
                horseID = value;
                break;
            case PADDOCK_PROCEED_TO_PADDOCK:
                raceNumber = value;
                break;
            case RACING_TRACK_MAKE_A_MOVE:
                horseID = value;
                break;
            case RACING_TRACK_HAS_FINISH_LINE_BEEN_CROSSED:
                horseID = value;
                break;
            case STABLE_PROCEED_TO_STABLE:
                raceNumber = value;
                break;
            case STABLE_PROCEED_TO_STABLE_WITH_RACE_ID:
                raceNumber = value;
                break;
            case STABLE_SUMMON_HORSES_TO_PADDOCK:
                raceNumber = value;
                break;
            case REPLY_BETTING_CENTRE_GET_NUMBER_OF_HORSES:
                horses = value;
                break;
            case REPLY_BETTING_CENTRE_GO_COLLECT_THE_GAINS:
                gains = value;
                break;
            case REPLY_BETTING_CENTRE_PLACE_A_BET:
                bet = value;
                break;
            case REPLY_CONTROL_CENTRE_REPORT_RESULTS:
                results = value;
                break;
            case REPLY_GENERAL_INFORMATION_REPOSITORY_GET_CURRENT_RACE_DISTANCE:
                raceDistance = value;
                break;
            case REPLY_GENERAL_INFORMATION_REPOSITORY_GET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID:
                increments = value;
                break;
            case REPLY_GENERAL_INFORMATION_REPOSITORY_GET_RACE_NUMBER:
                raceNumber = value;
                break;
            case REPLY_RACING_TRACK_GET_WINNER:
                winner = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a message which is defined by its type (as an object of type {@link MessageType})
     * and two integer values.
     *
     * @param type the type of this message.
     * @param value1 the first {@code integer} value which must be saved with the message.
     * @param value2 the second {@code integer} value which must be saved with the message.
     */
    public Message(MessageType type, int value1, int value2) {
        this.type = type;
        switch (type) {
            case BETTING_CENTRE_SET_ABILITY:
                horseID = value1;
                ability = value2;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_AMOUNT_OF_MONEY:
                spectatorID = value1;
                spectatorAmountOfMoney = value2;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_SELECTION:
                spectatorID = value1;
                spectatorBetSelection = value2;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_AMOUNT:
                spectatorID = value1;
                bet = value2;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_ABILITY:
                horseID = value1;
                ability = value2;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_PROBABILITY_TO_WIN:
                horseID = value1;
                horseProbability = value2;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID:
                horseID = value1;
                horseIteration = value2;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_POSITION_ON_TRACK:
                horseID = value1;
                horsePosition = value2;
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_FINAL_STAND_POSITION:
                horseID = value1;
                horsePosition = value2;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a message which is defined by its type (as an object of type {@link MessageType})
     * and two integer values.
     *
     * @param type the type of this message.
     * @param value1 the first {@code integer} value which must be saved with the message.
     * @param value2 the second {@code integer} value which must be saved with the message.
     * @param value3 the third {@code integer} value which must be saved with the message.
     */
    public Message(MessageType type, int value1, int value2, int value3) {
        this.type = type;
        switch (type) {
            case BETTING_CENTRE_PLACE_A_BET:
                spectatorID = value1;
                bet = value2;
                horseID = value3;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a message which is defined by its type (as an object of type {@link MessageType})
     * and one Broker state value defined as {@link BrokerState}.
     *
     * @param type the type of this message.
     * @param state the {@code BrokerState} state of the Broker to be updated.
     */
    public Message(MessageType type, BrokerState state) {
        this.type = type;
        brokerState = state;
    }

    /**
     * Constructs a message which is defined by its type (as an object of type {@link MessageType}),
     * one pair Horse/Jockey identification and one pair Horse/Jockey state value defined as {@link HorseJockeyState}.
     *
     * @param type the type of this message.
     * @param horseID the identification of the pair Horse/Jockey.
     * @param state the {@code HorseJockeyState} state of the pair Horse/Jockey to be updated.
     */
    public Message(MessageType type, int horseID, HorseJockeyState state) {
        this.type = type;
        this.horseID = horseID;
        this.horseJockeyState = state;
    }

    /**
     * Constructs a message which is defined by its type (as an object of type {@link MessageType}),
     * one Spectator identification and one Spectator state value defined as {@link SpectatorState}.
     *
     * @param type the type of this message.
     * @param spectatorID the identification of the Spectator.
     * @param state the {@code SpectatorState} state of the Spectator to be updated.
     */
    public Message(MessageType type, int spectatorID, SpectatorState state) {
        this.type = type;
        this.spectatorID = spectatorID;
        this.spectatorState = state;
    }

    /**
     * Constructs a message which is defined by its type (as an object of type {@link MessageType}
     * and a {@link Race}.
     *
     * @param type the type of this message.
     * @param race the new Race.
     */
    public Message(MessageType type, Race race) {
        this.type = type;
        this.race = race;
    }

    /**
     * Gets the type of this Message.
     *
     * @return the type of this message as {@link MessageType}.
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Gets the state of the Broker.
     *
     * @return the state of the Broker as {@link BrokerState}.
     */
    public BrokerState getBrokerState() {
        return brokerState;
    }

    /**
     * Gets the state of the pair Horse/Jockey.
     *
     * @return the state of the pair Horse/Jockey as {@link HorseJockeyState}.
     */
    public HorseJockeyState getHorseJockeyState() {
        return horseJockeyState;
    }

    /**
     * Gets the state of the Spectator.
     *
     * @return the state of the Spectator as {@link SpectatorState}.
     */
    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    /**
     * Gets the identification of a Spectator.
     *
     * @return the identification as an {@code int}.
     */
    public int getSpectatorID() {
        return spectatorID;
    }

    /**
     * Gets the bet of a Spectator.
     *
     * @return the bet as an {@code int}.
     */
    public int getBet() {
        return bet;
    }

    /**
     * Gets the identification of a pair Horse/Jockey.
     *
     * @return the identification as an {@code int}.
     */
    public int getHorseID() {
        return horseID;
    }

    /**
     * Gets the identification of a Winner.
     *
     * @return the winner as an {@code int}.
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Gets the ability of a pair Horse/Jockey.
     *
     * @return the ability as an {@code int}.
     */
    public int getAbility() {
        return ability;
    }

    /**
     * Gets the identification of a Race.
     *
     * @return the race number as an {@code int}.
     */
    public int getRaceNumber() {
        return raceNumber;
    }

    /**
     * Gets the distance of a Race.
     *
     * @return the distance as an {@code int}.
     */
    public int getRaceDistance() {
        return raceDistance;
    }

    /**
     * Gets the amount of money of a Spectator.
     *
     * @return the amount of money as an {@code int}.
     */
    public int getSpectatorAmountOfMoney() {
        return spectatorAmountOfMoney;
    }

    /**
     * Gets the bet selection of a Spectator.
     *
     * @return the bet selection as an {@code int}.
     */
    public int getSpectatorBetSelection() {
        return spectatorBetSelection;
    }

    /**
     * Gets the odds of a pair Horse/Jockey.
     *
     * @return the odds as an {@code int}.
     */
    public int getHorseProbability() {
        return horseProbability;
    }

    /**
     * Gets the iterations of a pair Horse/Jockey.
     *
     * @return the iterations as an {@code int}.
     */
    public int getHorseIteration() {
        return horseIteration;
    }

    /**
     * Gets the position of a pair Horse/Jockey.
     *
     * @return the position as an {@code int}.
     */
    public int getHorsePosition() {
        return horsePosition;
    }

    /**
     * Gets the value of a boolean variable.
     *
     * @return the value as an {@code boolean}.
     */
    public boolean getValue() {
        return value;
    }

    /**
     * Gets the gains of a Spectator.
     *
     * @return the gains as an {@code int}.
     */
    public int getGains() {
        return gains;
    }

    /**
     * Gets the number of pairs Horse/Jockey.
     *
     * @return the number as an {@code int}.
     */
    public int getHorses() {
        return horses;
    }

    /**
     * Gets the gains of a Spectator.
     *
     * @return the gains as an {@code int}.
     */
    public int getResults() {
        return results;
    }

    /**
     * Gets the increments of a pair Horse/Jockey.
     *
     * @return the increments as an {@code int}.
     */
    public int getIncrements() {
        return increments;
    }

    /**
     * Gets a race.
     *
     * @return the race as an {@code Race}.
     */
    public Race getRace() {
        return race;
    }

    /**
     * Sets a new Broker state.
     *
     * @param brokerState the new state.
     */
    public void setBrokerState(BrokerState brokerState) {
        this.brokerState = brokerState;
    }

    /**
     * Sets a new pair Horse/Jockey state.
     *
     * @param horseJockeyState the new state.
     */
    public void setHorseJockeyState(HorseJockeyState horseJockeyState) {
        this.horseJockeyState = horseJockeyState;
    }

    /**
     * Sets a new Spectator state.
     *
     * @param spectatorState the new state.
     */
    public void setSpectatorState(SpectatorState spectatorState) {
        this.spectatorState = spectatorState;
    }

    /**
     * Sets a new Spectator identification.
     *
     * @param spectatorID the new identification.
     */
    public void setSpectatorID(int spectatorID) {
        this.spectatorID = spectatorID;
    }

    /**
     * Sets a new pair Horse/Jockey identification.
     *
     * @param horseID the new identification.
     */
    public void setHorseID(int horseID) {
        this.horseID = horseID;
    }

    /**
     * Sets a new pair Horse/Jockey ability.
     *
     * @param ability the new ability.
     */
    public void setAbility(int ability) {
        this.ability = ability;
    }

    /**
     * Sets a new race identification.
     *
     * @param raceNumber the new identification.
     */
    public void setRaceNumber(int raceNumber) {
        this.raceNumber = raceNumber;
    }

    /**
     * Sets a new boolean variable value.
     *
     * @param value the new value.
     */
    public void setValue(boolean value) {
        this.value = value;
    }

    /**
     * Sets a new Race.
     *
     * @param race the new race.
     */
    public void setRace(Race race) {
        this.race = race;
    }

    /** The type of this message */
    private MessageType type;

    /** The state of this message's Broker */
    private BrokerState brokerState;

    /** The state of this message's pair Horse/Jockey */
    private HorseJockeyState horseJockeyState;

    /** The state of this message's spectator */
    private SpectatorState spectatorState;

    /** This message's spectator identification */
    private int spectatorID;

    /** This message's bet */
    private int bet;

    /** This message's gains */
    private int gains;

    /** This message's number of horses */
    private int horses;

    /** This message's results */
    private int results;

    /** This message's increments */
    private int increments;

    /** This message's identification of a pair Horse/Jockey */
    private int horseID;

    /** This message's identification of the winner */
    private int winner;

    /** This message's pair Horse/Jockey's ability */
    private int ability;

    /** This message's number of the race */
    private int raceNumber;

    /** This message's distance of the race */
    private int raceDistance;

    /** This message's amount of money */
    private int spectatorAmountOfMoney;

    /** This message's bet selection */
    private int spectatorBetSelection;

    /** This message's odds for the pair Horse/Jockey */
    private int horseProbability;

    /** This message's pair Horse/Jockey iteration */
    private int horseIteration;

    /** This message's pair Horse/Jockey position */
    private int horsePosition;

    /** This message's boolean variable value */
    private boolean value;

    /** This message's race */
    private Race race;

}
