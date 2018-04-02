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
 * @version 1.0
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
     */
    public HorseJockey(int identification, int ability, BettingCentre bettingCentre, ControlCentre controlCentre, Paddock paddock, RacingTrack racingTrack, Stable stable, GeneralInformationRepository repository) {
        this.identification = identification;
        repository.setHorseJockeyStatus(identification, state);
        this.ability = ability;
        repository.setHorseJockeyAbility(this.identification, this.ability);
        this.controlCentre = controlCentre;
        this.paddock = paddock;
        this.racingTrack = racingTrack;
        this.raceNumber = this.racingTrack.getRace().getIdentification();
        this.stable = stable;
        this.repository = repository;
        this.bettingCentre = bettingCentre;
        repository.newSnapshot();
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
            bettingCentre.setAbility(identification, ability);                  // Set my ability on the Betting Centre;
            stable.proceedToStable(getRaceNumber());                            // I receive a call to go to the Paddock and I'll go if I'm from this race;
            controlCentre.proceedToPaddock();                                   // I should retrieve a signal to the Control Centre as I'm moved to the Paddock;
            paddock.proceedToPaddock(raceNumber);                               // Then I should change my own state to At the Paddock;
            paddock.proceedToStartLine();                                       // If every other Jockeys are at the Paddock and the Spectators saw us, then
            racingTrack.proceedToStartLine();                                   //   we must proceed to the start line and change my state to At the Start Line;
            while (!racingTrack.hasFinishLineBeenCrossed(identification)) {     // While the finish line is not crossed by me:
                racingTrack.makeAMove(identification);                          //   I should make a move on the track;
            }                                                                   //
            controlCentre.makeAMove();                                          // As I crossed the line I must advance one step further to get off the line;
            stable.proceedToStable();                                           // Then I should go to the Stable and rest till the next round, if that applies.
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.exit(2);
        }
    }

    /**
     * Sets the HorseJockey's state, from the possible available {@link HorseJockeyState}
     * enumeration. Here all the actions are reported to the General Repository of
     * Information and a new snapshot is created.
     *
     * @param state Enumeration value represented by {@link HorseJockeyState}
     */
    public synchronized void setHorseJockeyState(HorseJockeyState state) {
        this.state = state;
        repository.setHorseJockeyStatus(identification, state);
        repository.newSnapshot();
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
    public synchronized void setRaceNumber(int raceNumber) {
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
    private ControlCentre controlCentre;

    /**
     * The {@link Paddock} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private Paddock paddock;

    /**
     * The {@link RacingTrack} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private RacingTrack racingTrack;

    /**
     * The {@link Stable} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private Stable stable;

    /**
     * The {@link BettingCentre} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     */
    private BettingCentre bettingCentre;

    /**
     * The {@link GeneralInformationRepository} instance where all this pair Horse/Jockey's actions will be reported.
     */
    private GeneralInformationRepository repository;
}
