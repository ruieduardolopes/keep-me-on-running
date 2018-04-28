package communications;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;

public class Message {
    public Message(MessageType type) {
        this.type = type;
    }

    public Message(MessageType type, boolean value) {
        this.type = type;
        switch (type) {
            case GENERAL_INFORMATION_REPOSITORY_SET_WERE_WAITING_THE_HORSES:
                this.value = value;
                break;
            case PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR:
                this.value = value;
                break;
            default:
                // TODO : Handle this case
                break;
        }
    }

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
            case STABLE_SUMMON_HORSES_TO_PADDOCK:
                raceNumber = value;
                break;
            default:
                // TODO : Handle this case
                break;
        }
    }

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
                // TODO : Handle this case
                break;
        }
    }

    public Message(MessageType type, int value1, int value2, int value3) {
        this.type = type;
        switch (type) {
            case BETTING_CENTRE_PLACE_A_BET:
                spectatorID = value1;
                bet = value2;
                horseID = value3;
                break;
            default:
                // TODO : Handle this case
                break;
        }
    }

    public Message(MessageType type, BrokerState state) {
        this.type = type;
        brokerState = state;
    }

    public Message(MessageType type, int identification, HorseJockeyState state) {
        this.type = type;
        this.identification = identification;
        this.horseJockeyState = state;
    }

    public Message(MessageType type, int identification, SpectatorState state) {
        this.type = type;
        this.identification = identification;
        this.spectatorState = state;
    }

    public MessageType getType() {
        return type;
    }

    public BrokerState getBrokerState() {
        return brokerState;
    }

    public HorseJockeyState getHorseJockeyState() {
        return horseJockeyState;
    }

    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    public int getIdentification() {
        return identification;
    }

    public int getSpectatorID() {
        return spectatorID;
    }

    public int getBet() {
        return bet;
    }

    public int getHorseID() {
        return horseID;
    }

    public int getWinner() {
        return winner;
    }

    public int getAbility() {
        return ability;
    }

    public int getRaceNumber() {
        return raceNumber;
    }

    public int getRaceDistance() {
        return raceDistance;
    }

    public int getSpectatorAmountOfMoney() {
        return spectatorAmountOfMoney;
    }

    public int getSpectatorBetSelection() {
        return spectatorBetSelection;
    }

    public int getHorseProbability() {
        return horseProbability;
    }

    public int getHorseIteration() {
        return horseIteration;
    }

    public int getHorsePosition() {
        return horsePosition;
    }

    public boolean getValue() {
        return value;
    }

    private MessageType type;
    private BrokerState brokerState;
    private HorseJockeyState horseJockeyState;
    private SpectatorState spectatorState;
    private int identification;
    private int spectatorID;
    private int bet;
    private int horseID;
    private int winner;
    private int ability;
    private int raceNumber;
    private int raceDistance;
    private int spectatorAmountOfMoney;
    private int spectatorBetSelection;
    private int horseProbability;
    private int horseIteration;
    private int horsePosition;
    private boolean value;
}
