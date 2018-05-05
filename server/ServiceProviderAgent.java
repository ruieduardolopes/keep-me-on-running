package server;

import communications.Message;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import lib.communication.ServerCom;
import lib.logging.Logger;

public class ServiceProviderAgent extends Thread {
    public ServiceProviderAgent(ServerCom connection, Server server) {
        this.connection = connection;
        this.server = server;
    }

    @Override
    public void run() {
        Message reply = null;
        Message message = (Message) connection.readObject();
        switch (message.getType()) {
            case CONTROL_CENTRE_MAKE_A_MOVE:
                horseJockeyIdentification = message.getHorseID();
                break;
            case CONTROL_CENTRE_START_THE_RACE:
                break;
            case CONTROL_CENTRE_ENTERTAIN_THE_GUESTS:
                break;
            case CONTROL_CENTRE_WAIT_FOR_THE_NEXT_RACE:
                spectatorIdentification = message.getSpectatorID();
                break;
            case CONTROL_CENTRE_RELAX_A_BIT:
                spectatorIdentification = message.getSpectatorID();
                break;
            case CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK:
                break;
            case CONTROL_CENTRE_PROCEED_TO_PADDOCK:
                horseJockeyIdentification = message.getHorseID();
                break;
            case RACING_TRACK_MAKE_A_MOVE:
                horseJockeyIdentification = message.getHorseID();
                horseJockeyAgility = message.getAbility();
                break;
            case RACING_TRACK_PROCEED_TO_START_LINE:
                horseJockeyIdentification = message.getHorseID();
                break;
            case BETTING_CENTRE_ACCEPT_THE_BETS:
                break;
            case BETTING_CENTRE_HONOUR_THE_BETS:
                Logger.printError("1234567890987654321");
                break;
            case BETTING_CENTRE_PLACE_A_BET:
                break;
            case BETTING_CENTRE_GO_COLLECT_THE_GAINS:
                spectatorIdentification = message.getSpectatorID();
                break;
            case PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR:
                spectatorIdentification = message.getSpectatorID();
                break;
            case PADDOCK_PROCEED_TO_START_LINE:
                horseJockeyIdentification = message.getHorseID();
                break;
            case STABLE_PROCEED_TO_STABLE:
                horseJockeyIdentification = message.getHorseID();
                break;
            case STABLE_PROCEED_TO_STABLE_WITH_RACE_ID:
                horseJockeyIdentification = message.getHorseID();
                break;
            case CONTROL_CENTRE_GO_WATCH_THE_RACE:
                spectatorIdentification = message.getSpectatorID();
                break;
        }
        Logger.printDebug("%s",message.getType());
        Logger.printDebug("iqowdjioqwdjq %s",server.getClass().getName());
        try {
            reply = server.processAndAnswerRequest(message);
        } catch (Exception e) {
            // TODO : Handle this exception
        }
        Logger.printWarning("The type is %s", reply.getType());
        switch (reply.getType()) {
            case REPLY_CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK:
                reply.setBrokerState(brokerState);
                break;
            case REPLY_BETTING_CENTRE_ACCEPT_THE_BETS:
                reply.setBrokerState(brokerState);
                break;
            case REPLY_BETTING_CENTRE_HONOUR_THE_BETS:
                reply.setBrokerState(brokerState);
                Logger.printError("OI!OIJIO!JWOI!JW!");
                break;
            case REPLY_CONTROL_CENTRE_START_THE_RACE:
                reply.setBrokerState(brokerState);
                break;
            case REPLY_CONTROL_CENTRE_ENTERTAIN_THE_GUESTS:
                reply.setBrokerState(brokerState);
                break;
            case REPLY_CONTROL_CENTRE_RELAX_A_BIT:
                reply.setSpectatorState(spectatorState);
                break;
            case REPLY_CONTROL_CENTRE_PROCEED_TO_PADDOCK:
                reply.setHorseJockeyState(horseJockeyState);
                break;
            case REPLY_PADDOCK_PROCEED_TO_START_LINE:
                reply.setHorseJockeyState(horseJockeyState);
                break;
            case REPLY_RACING_TRACK_MAKE_A_MOVE:
                reply.setHorseJockeyState(horseJockeyState);
                break;
            case REPLY_STABLE_PROCEED_TO_STABLE:
                reply.setHorseJockeyState(horseJockeyState);
                break;
            case REPLY_STABLE_PROCEED_TO_STABLE_WITH_RACE_ID:
                reply.setHorseJockeyState(horseJockeyState);
                break;
            case REPLY_CONTROL_CENTRE_GO_WATCH_THE_RACE:
                reply.setSpectatorState(spectatorState);
                break;
            case REPLY_CONTROL_CENTRE_WAIT_FOR_NEXT_RACE:
                reply.setSpectatorState(spectatorState);
                break;
            case REPLY_BETTING_CENTRE_PLACE_A_BET:
                reply.setSpectatorState(spectatorState);
                break;
            case REPLY_BETTING_CENTRE_GO_COLLECT_THE_GAINS:
                reply.setSpectatorState(spectatorState);
                break;
            case REPLY_PADDOCK_GO_CHECK_HORSES_WITH_LAST_SPECTATOR:
                reply.setSpectatorState(spectatorState);
                break;
            default:
                // TODO : handle this case
                break;
        }
        Logger.printDebug("%s",reply.getType());
        connection.writeObject(reply);
        connection.close();
    }

    public int getSpectatorAmountOfMoney() {
        return spectatorAmountOfMoney;
    }

    public void setSpectatorAmountOfMoney(int spectatorAmountOfMoney) {
        this.spectatorAmountOfMoney = spectatorAmountOfMoney;
    }

    public int getSpectatorIdentification() {
        return spectatorIdentification;
    }

    public void setSpectatorIdentification(int spectatorIdentification) {
        this.spectatorIdentification = spectatorIdentification;
    }

    public int getHorseJockeyOdd() {
        return horseJockeyOdd;
    }

    public void setHorseJockeyOdd(int horseJockeyOdd) {
        this.horseJockeyOdd = horseJockeyOdd;
    }

    public int getHorseJockeyIdentification() {
        return horseJockeyIdentification;
    }

    public void setHorseJockeyIdentification(int horseJockeyIdentification) {
        this.horseJockeyIdentification = horseJockeyIdentification;
    }

    public int getHorseJockeyAgility() {
        return horseJockeyAgility;
    }

    public void setHorseJockeyAgility(int horseJockeyAgility) {
        this.horseJockeyAgility = horseJockeyAgility;
    }

    public HorseJockeyState getHorseJockeyState() {
        return horseJockeyState;
    }

    public void setHorseJockeyState(HorseJockeyState horseJockeyState) {
        this.horseJockeyState = horseJockeyState;
    }

    public SpectatorState getSpectatorState() {
        return spectatorState;
    }

    public void setSpectatorState(SpectatorState spectatorState) {
        this.spectatorState = spectatorState;
    }

    public BrokerState getBrokerState() {
        return brokerState;
    }

    public void setBrokerState(BrokerState brokerState) {
        this.brokerState = brokerState;
    }

    private int spectatorAmountOfMoney;
    private int spectatorIdentification;
    private int horseJockeyOdd;
    private int horseJockeyIdentification;
    private int horseJockeyAgility;
    private HorseJockeyState horseJockeyState;
    private SpectatorState spectatorState;
    private BrokerState brokerState;
    private ServerCom connection;
    private Server server;
}
