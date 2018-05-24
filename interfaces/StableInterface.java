package interfaces;

import communications.Message;
import communications.MessageType;
import hippodrome.Stable;
import lib.logging.Logger;
import server.Server;

/**
 * Stable endpoint representation on the server side.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class StableInterface implements Server {
    /**
     * Creates a Stable Proxy instance.
     */
    public StableInterface() {
        stable = Stable.getInstance();
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
        switch (message.getType()) {
            case STABLE_PROCEED_TO_STABLE:
                stable.proceedToStable();

                reply = new Message(MessageType.REPLY_STABLE_PROCEED_TO_STABLE);
                break;
            case STABLE_PROCEED_TO_STABLE_WITH_RACE_ID:
                stable.proceedToStable(message.getRaceNumber());
                reply = new Message(MessageType.REPLY_STABLE_PROCEED_TO_STABLE_WITH_RACE_ID);
                break;
            case STABLE_SUMMON_HORSES_TO_PADDOCK:
                stable.summonHorsesToPaddock(message.getRaceNumber());
                reply = new Message(MessageType.OK);
                break;
            case STABLE_SHUTDOWN:
                reply = new Message(MessageType.OK);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return reply;
    }

    /**
     * A Stable instance.
     */
    private final Stable stable;
}
