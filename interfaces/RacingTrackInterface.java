package interfaces;

import communications.Message;
import communications.MessageType;
import hippodrome.RacingTrack;
import hippodrome.actions.Race;
import server.Server;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Racing Track endpoint representation on the server side.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class RacingTrackInterface implements Server {
    /**
     * Creates a Racing Track Proxy instance.
     * @throws InterruptedException if a communication channel could not be established.
     */
    public RacingTrackInterface() throws InterruptedException {
        try {
            racingTrack = RacingTrack.getInstance();
        } catch (Exception e) {
            throw new InterruptedException();
        }
    }

    /**
     * Implementation of the {@link Server}'s method to process and answer the requests.
     *
     * @param message the message to process and to reply on.
     * @return the reply message to the {@code message} given as input.
     * @throws Exception if a unexpected message type is given.
     */
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
                throw new IllegalArgumentException();
        }
        return reply;
    }

    /**
     * A Racing Track instance.
     */
    private final RacingTrack racingTrack;
}
