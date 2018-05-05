package server;

import communications.Message;
import communications.MessageType;
import hippodrome.BettingCentre;
import lib.logging.Logger;

public class BettingCentreProxy implements Server {
    public BettingCentreProxy() {
        bettingCentre = BettingCentre.getInstance();
    }

    @Override
    public Message processAndAnswerRequest(Message message) throws Exception {
        Logger.printWarning("I'm inside the ProcessNRequest of Betting Centre");
        Message reply = null;
        switch (message.getType()) {
            case BETTING_CENTRE_ACCEPT_THE_BETS:
                bettingCentre.acceptTheBets();
                reply = new Message(MessageType.REPLY_BETTING_CENTRE_ACCEPT_THE_BETS);
                break;
            case BETTING_CENTRE_ARE_THERE_ANY_WINNERS:
                boolean result = bettingCentre.areThereAnyWinners(message.getWinner());
                reply = new Message(MessageType.REPLY_BETTING_CENTRE_ARE_THERE_ANY_WINNERS, result);
                break;
            case BETTING_CENTRE_GET_NUMBER_OF_HORSES:
                int number = bettingCentre.getNumberOfHorses();
                reply = new Message(MessageType.REPLY_BETTING_CENTRE_GET_NUMBER_OF_HORSES, number);
                break;
            case BETTING_CENTRE_GO_COLLECT_THE_GAINS:
                int gains = bettingCentre.goCollectTheGains();
                reply = new Message(MessageType.REPLY_BETTING_CENTRE_GO_COLLECT_THE_GAINS, gains);
                break;
            case BETTING_CENTRE_PLACE_A_BET:
                int bet = bettingCentre.placeABet(message.getSpectatorID(), message.getBet(), message.getHorseID());
                reply = new Message(MessageType.REPLY_BETTING_CENTRE_PLACE_A_BET, bet);
                break;
            case BETTING_CENTRE_HAVE_I_WON:
                boolean haveIWon = bettingCentre.haveIWon(message.getSpectatorID());
                reply = new Message(MessageType.REPLY_BETTING_CENTRE_HAVE_I_WON, haveIWon);
                break;
            case BETTING_CENTRE_HONOUR_THE_BETS:
                bettingCentre.honourTheBets();
                reply = new Message(MessageType.REPLY_BETTING_CENTRE_HONOUR_THE_BETS);
                Logger.printError("hhhhhhhhhhhhhhhhh");
                break;
            case BETTING_CENTRE_SET_ABILITY:
                Logger.printInformation("Received a request %s to the horse %d with ability %d", message.getType(), message.getHorseID(), message.getAbility());
                bettingCentre.setAbility(message.getHorseID(), message.getAbility());
                reply = new Message(MessageType.OK);
                break;
            default:
                // TODO : handle this case
                break;
        }
        return reply;
    }

    private final BettingCentre bettingCentre;
}
