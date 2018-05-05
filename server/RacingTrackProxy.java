package server;

import communications.Message;
import communications.MessageType;
import hippodrome.RacingTrack;
import hippodrome.actions.Race;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class RacingTrackProxy implements Server {
    public RacingTrackProxy() {
        racingTrack = RacingTrack.getInstance();
    }

    @Override
    public Message processAndAnswerRequest(Message message) throws Exception {
        Message reply = null;
        ByteArrayOutputStream objectByteArray = new ByteArrayOutputStream();
        ObjectOutputStream serializedObject = null;
        switch (message.getType()) {
            case RACING_TRACK_GET_RACE:
                Race race = racingTrack.getRace();
                reply = new Message(MessageType.REPLY_RACING_TRACK_GET_RACE, race);
                break;
            case RACING_TRACK_GET_WINNER:
                int winner = racingTrack.getWinner();
                reply = new Message(MessageType.REPLY_RACING_TRACK_GET_WINNER, winner);
                break;
            case RACING_TRACK_HAS_FINISH_LINE_BEEN_CROSSED:
                boolean crossed = racingTrack.hasFinishLineBeenCrossed(message.getHorseID());
                reply = new Message(MessageType.REPLY_RACING_TRACK_HAS_FINISH_LINE_BEEN_CROSSED, crossed);
                break;
            case RACING_TRACK_MAKE_A_MOVE:
                racingTrack.makeAMove(message.getHorseID());
                reply = new Message(MessageType.REPLY_RACING_TRACK_MAKE_A_MOVE);
                break;
            case RACING_TRACK_PROCEED_TO_START_LINE:
                racingTrack.proceedToStartLine();
                reply = new Message(MessageType.OK);
                break;
            case RACING_TRACK_START_THE_RACE:
                racingTrack.startTheRace();
                reply = new Message(MessageType.OK);
                break;
            case RACING_TRACK_SET_RACE:
                racingTrack.setRace(message.getRace());
                reply = new Message(MessageType.OK);
                break;
            case RACING_TRACK_SHUTDOWN:
                reply = new Message(MessageType.OK);
                break;
            default:
                // TODO : handle this case
                break;
        }
        return reply;
    }

    private final RacingTrack racingTrack;
}
