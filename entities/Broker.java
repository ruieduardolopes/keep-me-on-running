package entities;

import clients.*;
import configurations.SimulationConfigurations;
import hippodrome.*;
import hippodrome.actions.Race;

/**
 * Implementation of a Entities, an essential character on a horse race action. This
 * entity, belonging to the {@link entities} package, has the responsibility of
 * controlling the races and of honouring and accepting the spectator's bets (for
 * more information about the {@code spectator}, please, consult {@link Spectator}).
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Spectator
 * @see HorseJockey
 * @since 0.1
 * @version 1.1
 */
public class Broker extends Thread {

    /**
     * Creates a Entities.
     * <br>
     * This constructor creates a Entities giving the reference of the {@link BettingCentre}, {@link ControlCentre},
     * {@link RacingTrack} and {@link Stable} where is about to work on. More, the Entities is also created having in mind
     * a General Repository of Information ({@link GeneralInformationRepository}) - where all the actions must be reported
     * and logged - and a given number of races ({@code numberOfRaces}).
     *
     * @param numberOfRaces The number of races in which this Entities will work on.
     *
     */
    public Broker(int numberOfRaces) {
        this.bettingCentre = new BettingCentreStub();
        this.controlCentre = new ControlCentreStub();
        this.racingTrack = new RacingTrackStub();
        this.stable = new StableStub();
        this.repository = new GeneralInformationRepositoryStub();
        this.totalOfRaces = numberOfRaces;
        this.repository.setBrokerStatus(state);
    }

    /**
     * Definition of the Entities's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code Entities}, since its displacement from the
     * {@link hippodrome.BettingCentre}, to the {@link hippodrome.ControlCentre},
     * passing by the {@link hippodrome.Stable} and the {@link hippodrome.RacingTrack}.
     */
    @Override
    public void run() {
        try {
            for (int raceNumber = 0; raceNumber < totalOfRaces; raceNumber++) {     // for each race on the set of races for today:
                stable.summonHorsesToPaddock(raceNumber);                           //x   call every horse of the race raceNumber on Stable to Paddock;
                controlCentre.summonHorsesToPaddock();                              //   having all the horses being called, announce the next race;
                bettingCentre.acceptTheBets();                                      //   go to the Betting Centre and accept the Spectators' bets;
                racingTrack.startTheRace();                                         //   go then to the Racing Track and make the signal to start the race;
                controlCentre.startTheRace();                                       //   prepare the Control Centre to the current race which has started;
                int winner = controlCentre.reportResults();                         //   as the race is finished, report its results;
                if (bettingCentre.areThereAnyWinners(winner)) {                     //   if there are any bet winners at the Betting Centre:
                    bettingCentre.honourTheBets();                                  //     then i should honour the bets and retrieve its money;
                }                                                                   //
                setRace(raceNumber+1);                                              //
                repository.raceIsOver();                                            //
            }                                                                       //
            controlCentre.entertainTheGuests();                                     // as the races are over, then i should go entertain the guests.
            repository.newSnapshot(true);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.exit(4);
        }
    }

    /**
     * Sets the Entities's state, from the possible available {@link BrokerState}
     * enumeration. Here all the actions are reported to the General Repository of
     * Information and a new snapshot is created.
     *
     * @param state Enumeration value represented by {@link BrokerState}
     */
    public synchronized void setBrokerState(BrokerState state) {
        this.state = state;
        //repository.setBrokerStatus(state);
    }

    /**
     * Sets the Entities's current Racing Track's instance {@link RacingTrack}.
     *
     */
    public synchronized void setRace(int identification) {
        this.racingTrack.setRace(new Race(SimulationConfigurations.NUMBER_OF_TRACKS, identification, Race.generateDistance()));
    }

    /**
     * A representation of the Entities's state given by the {@link BrokerState}
     * enumeration.
     */
    private BrokerState state = BrokerState.OPENING_THE_EVENT;

    /**
     * Entities's knowledge of how many races are about to be performed today.
     */
    private int totalOfRaces;

    /**
     * The {@link BettingCentre} instance where this {@link Broker} will perform its actions.
     */
    private BettingCentreStub bettingCentre;

    /**
     * The {@link ControlCentre} instance where this {@link Broker} will perform its actions.
     */
    private ControlCentreStub controlCentre;

    /**
     * The {@link RacingTrack} instance where this {@link Broker} will perform its actions.
     */
    private RacingTrackStub racingTrack;

    /**
     * The {@link Stable} instance where this {@link Broker} will perform its actions.
     */
    private StableStub stable;

    /**
     * The {@link GeneralInformationRepository} instance where all the {@link Broker}'s actions will be reported.
     */
    private GeneralInformationRepositoryStub repository;
}
