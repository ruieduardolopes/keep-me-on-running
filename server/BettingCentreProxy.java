package server;

import communications.Message;
import communications.MessageType;
import hippodrome.BettingCentre;
import lib.logging.Logger;

/**
 * Betting Centre endpoint representation on the server side.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class BettingCentreProxy implements Server {
    /**
     * Creates a Betting Centre Proxy instance.
     */
    public BettingCentreProxy() {
        bettingCentre = BettingCentre.getInstance();
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
                break;
            case BETTING_CENTRE_SET_ABILITY:
                Logger.printInformation("Received a request %s to the horse %d with ability %d", message.getType(), message.getHorseID(), message.getAbility());
                bettingCentre.setAbility(message.getHorseID(), message.getAbility());
                reply = new Message(MessageType.OK);
                break;
            case BETTING_CENTRE_SHUTDOWN:
                reply = new Message(MessageType.OK);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return reply;
    }

    /**
     * A Betting Centre instance.
     */
    private final BettingCentre bettingCentre;
}
