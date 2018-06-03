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
 * @version 2.0
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
     * @throws InterruptedException if the communication channel is busy.
     */
    public Broker(int numberOfRaces, BettingCentreInterface bettingCentre, ControlCentreInterface controlCentre, RacingTrackInterface racingTrack, StableInterface stable, GeneralInformationRepositoryInterface repository) throws InterruptedException {
        this.bettingCentre = bettingCentre;
        this.controlCentre = controlCentre;
        this.racingTrack = racingTrack;
        this.stable = stable;
        this.repository = repository;
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
            for (int raceNumber = 0; raceNumber < totalOfRaces; raceNumber++) {         // for each race on the set of races for today:
                stable.summonHorsesToPaddock(raceNumber);                               //   call every horse of the race raceNumber on Stable to Paddock;
                setBrokerState(controlCentre.summonHorsesToPaddock().getBrokerState()); //   having all the horses being called, announce the next race;
                setBrokerState(bettingCentre.acceptTheBets().getBrokerState());         //   go to the Betting Centre and accept the Spectators' bets;
                racingTrack.startTheRace();                                             //   go then to the Racing Track and make the signal to start the race;
                setBrokerState(controlCentre.startTheRace().getBrokerState());          //   prepare the Control Centre to the current race which has started;
                int winner = controlCentre.reportResults();                             //   as the race is finished, report its results;
                if (bettingCentre.areThereAnyWinners(winner)) {                         //   if there are any bet winners at the Betting Centre:
                    setBrokerState(bettingCentre.honourTheBets().getBrokerState());     //     then i should honour the bets and retrieve its money;
                }                                                                       //
                if (raceNumber < totalOfRaces-1) {                                      //
                    setRace(raceNumber+1);                                              //
                }                                                                       //
                repository.raceIsOver();                                                //
            }                                                                           //
            setBrokerState(controlCentre.entertainTheGuests().getBrokerState());        // as the races are over, then i should go entertain the guests.
            repository.newSnapshot(true);                                               //
            //shutdown();                                                               // TODO : shutdown invocation
        } catch (InterruptedException ie) {                                             //
            ie.printStackTrace();                                                       //
            System.exit(4);                                                             //
        }                                                                               //
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
    }

    /**
     * Sets the Entities's current Racing Track's instance {@link RacingTrack}.
     * @param identification the identification of the race to set.
     * @throws InterruptedException if the communication channel is busy.
     */
    public synchronized void setRace(int identification) throws InterruptedException {
        try {
            this.racingTrack.setRace(new Race(SimulationConfigurations.NUMBER_OF_TRACKS, identification, Race.generateDistance()));
        } catch (InterruptedException e) {
            throw new InterruptedException();
        }
    }

    /**
     * Send request to shutdown the hippodrome regions, since anything more is needed after then.
     * TODO : perform a shutdown.
     *
     * @throws InterruptedException if the communication channel cannot be used.
     */
    /*private void shutdown() throws InterruptedException {
        try {
            bettingCentre.shutdown();
            controlCentre.shutdown();
            repository.shutdown();
            new PaddockStub().shutdown();
            racingTrack.shutdown();
            stable.shutdown();
        } catch (InterruptedException e) {
            throw new InterruptedException();
        }
    }*/

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
    private BettingCentreInterface bettingCentre;

    /**
     * The {@link ControlCentre} instance where this {@link Broker} will perform its actions.
     */
    private ControlCentreInterface controlCentre;

    /**
     * The {@link RacingTrack} instance where this {@link Broker} will perform its actions.
     */
    private RacingTrackInterface racingTrack;

    /**
     * The {@link Stable} instance where this {@link Broker} will perform its actions.
     */
    private StableInterface stable;

    /**
     * The {@link GeneralInformationRepository} instance where all the {@link Broker}'s actions will be reported.
     */
    private GeneralInformationRepositoryInterface repository;
}
