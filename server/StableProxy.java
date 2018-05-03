package server;

import communications.Message;
import communications.MessageType;
import hippodrome.Stable;

public class StableProxy implements Server {
    public StableProxy() {
        stable = Stable.getInstance();
    }

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
            default:
                // TODO : handle this case
                break;
        }
        return reply;
    }

    private final Stable stable;
}
