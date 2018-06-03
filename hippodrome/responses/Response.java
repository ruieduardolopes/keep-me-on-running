package hippodrome.responses;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import java.io.Serializable;

public class Response implements Serializable {
    public Response(ResponseType type, BrokerState state) {
        this.type = type;
        this.brokerState = state;
    }

    public Response(ResponseType type, SpectatorState state, int spectatorId) {
        this.type = type;
        this.spectatorState = state;
        this.spectatorId = spectatorId;
    }

    public Response(ResponseType type, SpectatorState state, int spectatorId, int value) {
        this.type = type;
        this.spectatorState = state;
        this.spectatorId = spectatorId;
        this.valueInt = value;
    }

    public Response(ResponseType type, SpectatorState state, int spectatorId, boolean value) {
        this.type = type;
        this.spectatorState = state;
        this.spectatorId = spectatorId;
        this.booleanValue = value;
    }

    public Response(ResponseType type, HorseJockeyState state, int horseJockeyId) {
        this.type = type;
        this.horseJockeyState = state;
        this.horseJockeyId = horseJockeyId;
    }

    public int getValueInt() {
        return valueInt;
    }

    public int getSpectatorId() {
        return spectatorId;
    }

    public int getHorseJockeyId() {
        return horseJockeyId;
    }

    public ResponseType getType() {
        return type;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public BrokerState getBrokerState() {
        return brokerState;
    }

    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    public HorseJockeyState getHorseJockeyState() {
        return horseJockeyState;
    }

    private int valueInt = 0;
    private int spectatorId = 0;
    private int horseJockeyId = 0;
    private ResponseType type = null;
    private boolean booleanValue = false;
    private BrokerState brokerState = null;
    private SpectatorState spectatorState = null;
    private HorseJockeyState horseJockeyState = null;
}
