package hippodrome.responses;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import java.io.Serializable;

/**
 * Class that provides the needed marshalling options to enable the correct return values for each entity's state
 * changes and actual return values.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 3.0
 * @version 3.0
 */
public class Response implements Serializable {
    /**
     * Constructs a response given a type and a Broker state.
     * @param type the identification of the message to return.
     * @param state the state to be updated.
     */
    public Response(ResponseType type, BrokerState state) {
        this.type = type;
        this.brokerState = state;
    }

    /**
     *
     * @param type the identification of the message to return.
     * @param state the state to be updated.
     * @param spectatorId the identification of the spectator to be changed.
     */
    public Response(ResponseType type, SpectatorState state, int spectatorId) {
        this.type = type;
        this.spectatorState = state;
        this.spectatorId = spectatorId;
    }

    /**
     *
     * @param type the identification of the message to return.
     * @param state the state to be updated.
     * @param spectatorId the identification of the spectator to be changed.
     * @param value the value to be returned.
     */
    public Response(ResponseType type, SpectatorState state, int spectatorId, int value) {
        this.type = type;
        this.spectatorState = state;
        this.spectatorId = spectatorId;
        this.valueInt = value;
    }

    /**
     *
     * @param type the identification of the message to return.
     * @param state the state to be updated.
     * @param spectatorId the identification of the spectator to be changed.
     * @param value the value to be returned.
     */
    public Response(ResponseType type, SpectatorState state, int spectatorId, boolean value) {
        this.type = type;
        this.spectatorState = state;
        this.spectatorId = spectatorId;
        this.booleanValue = value;
    }

    /**
     *
     * @param type the identification of the message to return.
     * @param state the state to be updated.
     * @param horseJockeyId the identification of the pair Horse/Jockey to be changed.
     */
    public Response(ResponseType type, HorseJockeyState state, int horseJockeyId) {
        this.type = type;
        this.horseJockeyState = state;
        this.horseJockeyId = horseJockeyId;
    }

    /**
     * Gets the integer value to return.
     * @return the value to return.
     */
    public int getValueInt() {
        return valueInt;
    }

    /**
     * Gets the spectator's identification.
     * @return the identification of a spectator.
     */
    public int getSpectatorId() {
        return spectatorId;
    }

    /**
     * Gets the pair horse/jockey's identification.
     * @return the identification of a pair horse/jockey.
     */
    public int getHorseJockeyId() {
        return horseJockeyId;
    }

    /**
     * Gets the type to return.
     * @return the type to return.
     */
    public ResponseType getType() {
        return type;
    }

    /**
     * Gets the boolean value to return.
     * @return the value to return.
     */
    public boolean getBooleanValue() {
        return booleanValue;
    }

    /**
     * Gets the broker state value to return.
     * @return the state to return.
     */
    public BrokerState getBrokerState() {
        return brokerState;
    }

    /**
     * Gets the spectator state value to return.
     * @return the state to return.
     */
    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    /**
     * Gets the pair horse/jockey state value to return.
     * @return the state to return.
     */
    public HorseJockeyState getHorseJockeyState() {
        return horseJockeyState;
    }

    /** The integer value to be returned value */
    private int valueInt = 0;

    /** The identification of a spectator value */
    private int spectatorId = 0;

    /** The identification of a pair horse/jockey value */
    private int horseJockeyId = 0;

    /** The type of a response value */
    private ResponseType type = null;

    /** The boolean value to be returned */
    private boolean booleanValue = false;

    /** The broker state value */
    private BrokerState brokerState = null;

    /** The spectator state value */
    private SpectatorState spectatorState = null;

    /** The pair horse/jockey value */
    private HorseJockeyState horseJockeyState = null;
}
