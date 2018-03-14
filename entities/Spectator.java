package entities;

import hippodrome.BettingCentre;
import hippodrome.ControlCentre;
import hippodrome.Paddock;

/**
 * Implementation of a Spectator which will be watching an horse race and betting on it. This entity,
 * belonging to the {@link entities} package, must coordinate its actions throughout several shared
 * memory regions, while interacting with the {@link Broker} (which controlls its bets) and with the
 * {@link HorseJockey} (in which each Spectator will bet).
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Broker
 * @see HorseJockey
 * @since 1.0
 */
public class Spectator implements Runnable {
    /**
     * Definition of the Spectator's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code Spectator}, since its displacement from the
     * {@link hippodrome.BettingCentre}, to the {@link hippodrome.Paddock}, passing by the {@link hippodrome.Stable}.
     */
    @Override
    public void run() {
        while (ControlCentre.waitForTheNextRace(raceNumber)) {  // while the next race has not started yet
            boolean isLastSpectator = Paddock.goCheckHorses();  //       the Paddock rises an alert for Spectators to go check the horses
            Paddock.goCheckHorses(isLastSpectator);             //       the Paddock also wants to know if there is more Spectators to come
            if (isLastSpectator) {                              //       if this is the last Spectator to come
                ControlCentre.goCheckHorses();                  //          the Broker on the Control Centre can proceed
            }                                                   //           its actions;
            BettingCentre.placeABet(this, bet, horse);          //       on the Betting Centre the Spectator can place its bet
            ControlCentre.goWatchTheRace(raceNumber);           //       then we go to the Control Centre (Watching Stand) to watch the race
            if (ControlCentre.haveIWon(this) && !tired) {       //       if the Control Centre approves that I won and if I am not tired
                BettingCentre.goCollectTheGains(this);          //          then I must go to the Betting Centre and collect my gains;
            } else if (ControlCentre.haveIWon(this) && tired) { //       if the Control Centre approves that I won and if I am tired
                BettingCentre.goCollectTheGains(this);          //          then I must go to the Betting Centre collect my gains
                break;                                          //          stop for this round and relax...
            } else {                                            //       if I have not won
                if (tired) {                                    //          but I feel tired
                    break;                                      //              then I should stop;
                }                                               //
            }                                                   //
        }                                                       // ;
        ControlCentre.relaxABit();                              // having all set, then I must relax;
    }

    /**
     * Returns the Spectator state representation given by the {@link SpectatorState}
     * enumeration.
     *
     * @return the current spectator {@link SpectatorState}.
     * @see SpectatorState
     */
    public SpectatorState getState() {
        return state;
    }

    /**
     * Returns the Spectator numerical identification between all the others Spectators.
     *
     * @return a numerical identification represented as an integer.
     */
    public int getIdentification() {
        return identification;
    }

    /**
     * Returns the amount of current money that this Spectator has available for new bets.
     *
     * @return the current amount of money, as an integer.
     */
    public int getMoney() {
        return money;
    }

    /**
     * A representation of the Spectator's state given by the {@link SpectatorState}
     * enumeration.
     */
    private SpectatorState state = SpectatorState.WAITING_FOR_A_RACE_TO_START;

    /**
     * Proper Spectator numerical identification.
     */
    private int identification;

    /**
     * Current amount of this Spectator's money to spend on new bets.
     */
    private int money;

    /**
     * Level of fatigue of this {@link Spectator}.
     */
    private boolean tired;

    /**
     * Amount of money prepared to be launched over a bet. TODO - probably is to remove...
     */
    private int bet;

    /**
     * Identification of the horse on which a bet will be made. TODO - probably is to remove...
     */
    private int horse;

    /**
     * The identification of the race which the Spectators want to bet and watch. TODO - probably is to remove...
     */
    private int raceNumber;
}
