package server;

import communications.Message;
import communications.MessageType;
import hippodrome.Paddock;

public class PaddockProxy implements Server {
    public PaddockProxy() {
        paddock = Paddock.getInstance();
    }

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
                // TODO : handle this case
                break;
        }
        return reply;
    }

    private final Paddock paddock;
}
