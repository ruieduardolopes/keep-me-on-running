package interfaces;

import communications.Message;
import communications.MessageType;
import hippodrome.Paddock;
import server.Server;

/**
 * Paddock endpoint representation on the server side.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class PaddockInterface implements Server {
    /**
     * Creates a Paddock Proxy instance.
     */
    public PaddockInterface() {
        paddock = Paddock.getInstance();
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
            case PADDOCK_GO_CHECK_HORSES:
                boolean result = paddock.goCheckHorses();
                reply = new Message(MessageType.REPLY_PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR, result);
                break;
            case PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR:
                paddock.goCheckHorses(message.getValue());
                reply = new Message(MessageType.REPLY_PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR);
                break;
            case PADDOCK_PROCEED_TO_PADDOCK:
                paddock.proceedToPaddock(message.getRaceNumber());
                reply = new Message(MessageType.OK);
                break;
            case PADDOCK_PROCEED_TO_START_LINE:
                paddock.proceedToStartLine();
                reply = new Message(MessageType.REPLY_PADDOCK_PROCEED_TO_START_LINE);
                break;
            case PADDOCK_SHUTDOWN:
                reply = new Message(MessageType.OK);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return reply;
    }

    /**
     * A Paddock instance.
     */
    private final Paddock paddock;
}
