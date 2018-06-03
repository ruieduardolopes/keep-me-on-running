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
        switch (this.type) {
            case CONTROL_CENTRE_GO_WATCH_THE_RACE:
                this.spectatorState = state;
                this.spectatorId = spectatorId;
                break;
            default:
                // TODO : handle this case
                break;
        }
    }

    public Response(ResponseType type, SpectatorState state, int spectatorId, int value) {
        this.type = type;
        switch (this.type) { // TODO : do the other cases
            case BETTING_CENTRE_PLACE_A_BET:
                this.spectatorState = state;
                this.spectatorId = spectatorId;
                this.bet = value;
                break;
            default:
                // TODO : handle this case
                break;
        }
    }

    public Response(ResponseType type, SpectatorState state, int spectatorId, boolean value) {
        this.type = type;
        switch (this.type) { // TODO : verify the cases
            case BETTING_CENTRE_PLACE_A_BET:
                this.spectatorState = state;
                this.spectatorId = spectatorId;
                this.booleanValue = value;
                break;
            default:
                // TODO : handle this case
                break;
        }
    }

    public Response(ResponseType type, HorseJockeyState state, int horseJockeyId) {
        this.type = type;
        switch (this.type) {
            case CONTROL_CENTRE_PROCEED_TO_PADDOCK:
                this.horseJockeyState = state;
                this.horseJockeyId = horseJockeyId;
                break;
            default:
                break;
        }
    }

    private int bet = 0;
    private int spectatorId = 0;
    private int horseJockeyId = 0;
    private ResponseType type = null;
    private boolean booleanValue = false;
    private BrokerState brokerState = null;
    private SpectatorState spectatorState = null;
    private HorseJockeyState horseJockeyState = null;
}
