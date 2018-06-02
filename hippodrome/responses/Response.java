package hippodrome.responses;

import entities.BrokerState;
import entities.SpectatorState;

public class Response {
    public Response(ResponseType type, BrokerState state) {
        this.type = type;
        this.brokerState = state;
    }

    public Response(ResponseType type, SpectatorState state, int spectatorId, int value) {
        this.type = type;
        switch (this.type) {
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

    private int bet = 0;
    private int spectatorId = 0;
    private ResponseType type = null;
    private BrokerState brokerState = null;
    private SpectatorState spectatorState = null;
}
