package entities;

import hippodrome.*;

/**
 * Implementation of a pair Horse/Jockey, the main horse race character. This entity,
 * belonging to the {@link entities} package, represents one of N pairs in a competition,
 * being the bets target (by the {@link Spectator}) and having its conduct coordinated by the
 * {@link Broker}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Broker
 * @see Spectator
 * @since 0.1
 * @version 2.0
 */
public class HorseJockey extends Thread {

    /**
     * Creates a pair Horse/Jockey.
     * <br>
     * This constructor creates a pair Horse/Jockey giving the reference of the {@link BettingCentre}, {@link ControlCentre},
     * {@link RacingTrack}, the {@link Paddock} and {@link Stable} where is about to work on. More, the pair Horse/Jockey is also
     * created having in mind a General Repository of Information ({@link GeneralInformationRepository}) - where all the actions
     * must be reported and logged - and a given maximum ability {@code ability} and identification {@code identification}.
     *
     * @param identification number which identifies this pair Horse/Jockey.
     * @param ability number with characterizes the Horse maximum ability to run.
     * @param bettingCentre An instance of a {@link BettingCentre} where this pair Horse/Jockey will work on.
     * @param controlCentre An instance of a {@link ControlCentre} where this pair Horse/Jockey will work on.
     * @param paddock An instance of a {@link Paddock} where this pair Horse/Jockey will work on.
     * @param racingTrack An instance of a {@link RacingTrack} where this pair Horse/Jockey will work on.
     * @param stable An instance of a {@link Stable} where this Broker pair Horse/Jockey work on.
     * @param repository An instance of a {@link GeneralInformationRepository} in order to report all the actions and
     *                   log each and every moment.
     * @throws Exception if an illegal argument is given.
     */
    public HorseJockey(int identification, int ability, BettingCentreInterface bettingCentre, ControlCentreInterface controlCentre, PaddockInterface paddock, RacingTrackInterface racingTrack, StableInterface stable, GeneralInformationRepositoryInterface repository) throws Exception {
        try {
            this.repository = repository;
            this.identification = identification;
            this.ability = ability;
            this.repository.setHorseJockeyAbility(this.identification, this.ability);
            this.repository.setWereWaitingTheHorses(true);
            this.repository.setHorseJockeyStatus(identification, state);
            this.controlCentre = controlCentre;
            this.paddock = paddock;
            this.racingTrack = racingTrack;
            this.raceNumber = this.racingTrack.getRace().getIdentification();
            this.stable = stable;
            this.bettingCentre = bettingCentre;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * Definition of the HorseJockey's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code HorseJockey} pair, since its displacement from the
     * {@link hippodrome.Paddock}, to the {@link hippodrome.Stable}, passing by the {@link hippodrome.RacingTrack}
     * and the {@link ControlCentre}.
     */
    @Override
    public void run() {
        try {
            bettingCentre.setAbility(identification, ability);                                              // Set my ability on the Betting Centre to evaluate odds;
            setHorseJockeyState(stable.proceedToStable(identification, raceNumber).getHorseJockeyState());  // I receive a call to go to the Paddock and I'll go if I'm from this race;
            setHorseJockeyState(controlCentre.proceedToPaddock(identification).getHorseJockeyState());      // I should retrieve a signal to the Control Centre as I moved to the Paddock;
            paddock.proceedToPaddock(raceNumber);                                                           // Then I should change my own state to At the Paddock;
            setHorseJockeyState(paddock.proceedToStartLine(identification).getHorseJockeyState());          // If every other Jockeys are at the Paddock and the Spectators saw us, then
            racingTrack.proceedToStartLine(identification);                                                 //   we must proceed to the start line and change my state to At the Start Line;
            while (!racingTrack.hasFinishLineBeenCrossed(identification)) {                                 // While the finish line is not crossed by me:
                setHorseJockeyState(racingTrack.makeAMove(identification, ability).getHorseJockeyState());  //   I should make a move on the track;
            }                                                                                               //
            controlCentre.makeAMove(identification);                                                        // As I crossed the line I must advance one step further to get off the line;
            setHorseJockeyState(stable.proceedToStable(identification).getHorseJockeyState());              // Then I should go to the Stable and rest till the next round, if that applies.
        } catch (Exception e) {                                                                             //
            e.printStackTrace();                                                                            //
            System.exit(2);                                                                                 //
        }                                                                                                   //
    }

    /**
     * Sets the HorseJockey's state, from the possible available {@link HorseJockeyState}
     * enumeration. Here all the actions are reported to the General Repository of
     * Information and a new snapshot is created.
     *
     * @param state Enumeration value represented by {@link HorseJockeyState}
     */
    public void setHorseJockeyState(HorseJockeyState state) {
        this.state = state;
    }

    /**
     * Returns the pair Horse/Jockey number identification.
     *
     * @return a number represented by an integer.
     */
    public synchronized int getIdentification() {
        return identification;
    }

    /**
     * Returns the pair Horse/Jockey maximum ability to move forward on its track of the Racing Track.
     *
     * @return an integer representation of the ability.
     */
    public synchronized int getAbility() {
        return ability;
    }

    /**
     * Returns the pair Horse/Jockey race number identification, in order to compete in the Racing Track.
     *
     * @return an integer representing the identification.
     */
    public synchronized int getRaceNumber() {
        return raceNumber;
    }

    /**
     * Sets a new race number identification for this pair Horse/Jockey, applicable when this same entity runs in
     * different occasions.
     *
     * @param raceNumber an integer representing the identification.
     */
    public void setRaceNumber(int raceNumber) {
        this.raceNumber = raceNumber;
    }

    /**
     * A representation of the pair Horse/Jockey's state given by the {@link HorseJockeyState}
     * enumeration.
     */
    private HorseJockeyState state = HorseJockeyState.AT_THE_STABLE;

    /**
     * Proper pair Horse/Jockey identification number.
     */
    private int identification;

    /**
     * Pair Horse/Jockey ability to run. This value represents how easily this pair Horse/Jockey
     * can advance on its track of the Racing Track.
     */
    private int ability;

    /**
     * Pair Horse/Jockey race identification number. This number identifies which race they are going to
     * compete. This value can be updated via its setter method {@link HorseJockey#setRaceNumber}.
     */
    private int raceNumber;

    /**
     * The {@link ControlCentre} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private ControlCentreInterface controlCentre;

    /**
     * The {@link Paddock} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private PaddockInterface paddock;

    /**
     * The {@link RacingTrack} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private RacingTrackInterface racingTrack;

    /**
     * The {@link Stable} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private StableInterface stable;

    /**
     * The {@link BettingCentre} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private BettingCentreInterface bettingCentre;

    /**
     * The {@link GeneralInformationRepository} instance where all this pair Horse/Jockey's actions will be reported.
     */
    private GeneralInformationRepositoryInterface repository;
}
