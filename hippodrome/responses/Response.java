package hippodrome.responses;

import entities.Spectator;
import entities.SpectatorState;
import java.io.Serializable;

public class Response implements Serializable {
    public Response(ResponseType responseType, int value, SpectatorState newState) {
        if (responseType == ResponseType.BETTING_CENTRE_PLACE_A_BET) {
            bet = value;
            newSpectatorState = newState;
        }
    }

    public int bet;
    public SpectatorState newSpectatorState;
}
