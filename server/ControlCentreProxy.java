package server;

import communications.Message;
import communications.MessageType;
import hippodrome.ControlCentre;
import lib.logging.Logger;

public class ControlCentreProxy implements Server {
    public ControlCentreProxy() {
        controlCentre = ControlCentre.getInstance();
    }

    @Override
    public Message processAndAnswerRequest(Message message) throws Exception {
        Message reply = null;
        switch (message.getType()) {
            case CONTROL_CENTRE_ENTERTAIN_THE_GUESTS:
                controlCentre.entertainTheGuests();
                reply = new Message(MessageType.REPLY_CONTROL_CENTRE_ENTERTAIN_THE_GUESTS);
                break;
            case CONTROL_CENTRE_GO_CHECK_HORSES:
                controlCentre.goCheckHorses();
                reply = new Message(MessageType.OK);
                break;
            case CONTROL_CENTRE_GO_WATCH_THE_RACE:
                controlCentre.goWatchTheRace();
                reply = new Message(MessageType.REPLY_CONTROL_CENTRE_GO_WATCH_THE_RACE);
                break;
            case CONTROL_CENTRE_MAKE_A_MOVE:
                controlCentre.makeAMove();
                reply = new Message(MessageType.OK);
                break;
            case CONTROL_CENTRE_PROCEED_TO_PADDOCK:
                controlCentre.proceedToPaddock();
                reply = new Message(MessageType.REPLY_CONTROL_CENTRE_PROCEED_TO_PADDOCK);
                break;
            case CONTROL_CENTRE_RELAX_A_BIT:
                controlCentre.relaxABit();
                reply = new Message(MessageType.REPLY_CONTROL_CENTRE_RELAX_A_BIT);
                break;
            case CONTROL_CENTRE_REPORT_RESULTS:
                int result = controlCentre.reportResults();

                reply = new Message(MessageType.REPLY_CONTROL_CENTRE_REPORT_RESULTS, result);
                break;
            case CONTROL_CENTRE_START_THE_RACE:
                controlCentre.startTheRace();
                reply = new Message(MessageType.REPLY_CONTROL_CENTRE_START_THE_RACE);
                break;
            case CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK:
                controlCentre.summonHorsesToPaddock();
                reply = new Message(MessageType.REPLY_CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK);
                break;
            case CONTROL_CENTRE_WAIT_FOR_THE_NEXT_RACE:
                boolean thereIsANextRace = controlCentre.waitForTheNextRace();
                reply = new Message(MessageType.REPLY_CONTROL_CENTRE_WAIT_FOR_NEXT_RACE, thereIsANextRace);
                break;
            case CONTROL_CENTRE_SHUTDOWN:
                reply = new Message(MessageType.OK);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return reply;
    }

    private final ControlCentre controlCentre;
}
