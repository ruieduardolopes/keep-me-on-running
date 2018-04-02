package entities;

import hippodrome.*;

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
 * @version 1.0
 */
public class Broker extends Thread {

    /**
     * Creates a Broker.
     * <br>
     * This constructor creates a Broker giving the reference of the {@link BettingCentre}, {@link ControlCentre},
     * {@link RacingTrack} and {@link Stable} where is about to work on. More, the Broker is also created having in mind
     * a General Repository of Information ({@link GeneralInformationRepository}) - where all the actions must be reported
     * and logged - and a given number of races ({@code numberOfRaces}).
     *
     * @param numberOfRaces The number of races in which this Broker will work on.
     * @param bettingCentre An instance of a {@link BettingCentre} where this Broker will work on.
     * @param controlCentre An instance of a {@link ControlCentre} where this Broker will work on.
     * @param racingTrack An instance of a {@link RacingTrack} where this Broker will work on.
     * @param stable An instance of a {@link Stable} where this Broker will work on.
     * @param repository An instance of a {@link GeneralInformationRepository} in order to report all the actions and
     *                   log each and every moment.
     */
    public Broker(int numberOfRaces, BettingCentre bettingCentre, ControlCentre controlCentre, RacingTrack racingTrack, Stable stable, GeneralInformationRepository repository) {
        this.bettingCentre = bettingCentre;
        this.controlCentre = controlCentre;
        this.racingTrack = racingTrack;
        this.stable = stable;
        this.repository = repository;
        this.totalOfRaces = numberOfRaces;
        repository.setBrokerStatus(state);
        repository.newSnapshot();
    }

    /**
     * Definition of the Broker's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code Broker}, since its displacement from the
     * {@link hippodrome.BettingCentre}, to the {@link hippodrome.ControlCentre},
     * passing by the {@link hippodrome.Stable} and the {@link hippodrome.RacingTrack}.
     */
    @Override
    public void run() {
        try {
            for (int raceNumber = 0; raceNumber < totalOfRaces; raceNumber++) {     // for each race on the set of races for today:
                stable.summonHorsesToPaddock(raceNumber);                           //   call every horse of the race raceNumber on Stable to Paddock;
                controlCentre.summonHorsesToPaddock();                              //   having all the horses being called, announce the next race;
                bettingCentre.acceptTheBets();                                      //   go to the Betting Centre and accept the Spectators' bets;
                racingTrack.startTheRace();                                         //   go then to the Racing Track and make the signal to start the race;
                controlCentre.startTheRace();                                       //   prepare the Control Centre to the current race which has started;
                int winner = controlCentre.reportResults();                         //   as the race is finished, report its results;
                if (bettingCentre.areThereAnyWinners(winner)) {                     //   if there are any bet winners at the Betting Centre:
                    bettingCentre.honourTheBets();                                  //     then i should honour the bets and retrieve its money;
                }                                                                   //
            }                                                                       //
            controlCentre.entertainTheGuests();                                     // as the races are over, then i should go entertain the guests.
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.exit(4);
        }
    }

    /**
     * Sets the Broker's state, from the possible available {@link BrokerState}
     * enumeration. Here all the actions are reported to the General Repository of
     * Information and a new snapshot is created.
     *
     * @param state Enumeration value represented by {@link BrokerState}
     */
    public synchronized void setBrokerState(BrokerState state) {
        this.state = state;
        repository.setBrokerStatus(state);
        repository.newSnapshot();
    }

    /**
     * Sets the Broker's current Racing Track's instance {@link RacingTrack}.
     *
     * @param racingTrack the new instance of a {@link RacingTrack}.
     */
    public synchronized void setRacingTrack(RacingTrack racingTrack) {
        this.racingTrack = racingTrack;
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

    /**
     * The {@link BettingCentre} instance where this {@link Broker} will perform its actions.
     */
    private BettingCentre bettingCentre;

    /**
     * The {@link ControlCentre} instance where this {@link Broker} will perform its actions.
     */
    private ControlCentre controlCentre;

    /**
     * The {@link RacingTrack} instance where this {@link Broker} will perform its actions.
     */
    private RacingTrack racingTrack;

    /**
     * The {@link Stable} instance where this {@link Broker} will perform its actions.
     */
    private Stable stable;

    /**
     * The {@link GeneralInformationRepository} instance where all the {@link Broker}'s actions will be reported.
     */
    private GeneralInformationRepository repository;
}
