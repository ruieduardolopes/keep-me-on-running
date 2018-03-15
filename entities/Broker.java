package entities;

import hippodrome.BettingCentre;
import hippodrome.ControlCentre;
import hippodrome.Paddock;
import hippodrome.Stable;

/**
 * Implementation of a Broker, an essential character on a horse race action. This
 * entity, belonging to the {@link entities} package, has the responsibility of
 * controlling the races and of honouring and accepting the spectator's bets (for
 * more information about the {@code spectator}, please, consult {@link Spectator}).
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Spectator
 * @see HorseJockey
 * @since 0.1
 * @version 0.1
 */
public class Broker implements Runnable {
    /**
     * Creates a Broker.
     */
    public Broker() {

    }

    /**
     * Definition of the Broker's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code Broker}, since its displacement from the
     * {@link hippodrome.BettingCentre}, to the {@link hippodrome.ControlCentre}.
     */
    @Override
    public void run() {
        for (int raceNumber = 0; raceNumber < totalOfRaces; raceNumber++) { // for each race planned on the agenda
            Stable.summonHorsesToPaddock(raceNumber);                       //    a call must be done to Stable, asking Horses to go to Paddock
            ControlCentre.summonHorsesToPaddock();                          //    after which the Broker can proceed;
            BettingCentre.acceptTheBets();                                  // the Broker must accept all the bets on the Betting Center;
            ControlCentre.startTheRace(raceNumber);                         // then alerting everybody that the race starts (on the Control Centre);
            ControlCentre.reportResults();                                  // as soon as it is over, the results must be reported at the same place;
            if (BettingCentre.areThereAnyWinners()) {                       // if there are any betting winners then
                BettingCentre.honourTheBets();                              //    these bets must be honoured;
            }                                                               //
        }                                                                   //
        ControlCentre.entertainTheGuests();                                 // Having the races concluded, the Broker should entertain the guests.
    }

    /**
     * Returns the Broker state representation given by the {@link BrokerState}
     * enumeration.
     *
     * @return the current broker {@link BrokerState}.
     * @see BrokerState
     */
    public BrokerState getBrokerState() {
        return state;
    }

    /**
     * Sets the Broker's state, from the possible available {@link BrokerState}
     * enumeration.
     *
     * @param state Enumeration value represented by {@link BrokerState}
     */
    public void setBrokerState(BrokerState state) {
        this.state = state;
    }

    /**
     * A representation of the Broker's state given by the {@link BrokerState}
     * enumeration.
     */
    private BrokerState state = BrokerState.OPENING_THE_EVENT;

    /**
     * Broker's knowledge of how many races are about to be performed today.
     */
    private int totalOfRaces;
}
