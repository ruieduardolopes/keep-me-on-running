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
 * @version 0.1
 */
public class HorseJockey extends Thread {
    /**
     * Creates a pair Horse/Jockey with a given {@code identification} and an {@code ability} to run.
     *
     * @param identification number which identifies this pair Horse/Jockey.
     * @param ability number with characterizes the Horse ability to run.
     * @param controlCentre the {@link ControlCentre} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     * @param paddock the {@link Paddock} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     * @param racingTrack the {@link RacingTrack} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
     * @param stable the {@link Stable} instance where this pair Horse/Jockey ({@link HorseJockey}) will perform its actions.
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
    }

    /**
     * Definition of the HorseJockey's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code HorseJockey} pair, since its displacement from the
     * {@link hippodrome.Paddock}, to the {@link hippodrome.Stable}, passing by the {@link hippodrome.RacingTrack}.
     */
    @Override
    public void run() {
        stable.proceedToStable();                                   // Stable's call for this pair Horse/Jockey;
        controlCentre.proceedToPaddock();                           // alarm the Horses on Stable to go to the Paddock;
        paddock.proceedToPaddock(raceNumber);                       // verify if this Horse is the last on his go;
        paddock.proceedToStartLine();                               //
        racingTrack.proceedToStartLine();                           // Racing track's call for horses on the start line;
        while (!racingTrack.hasFinishLineBeenCrossed(identification)) {       // while this horse has not crossed the finish line
            bettingCentre.setHorseJockeyWinner(racingTrack.getWinner());
            racingTrack.makeAMove(identification);                //    it should make a move forward, to reach it;
        }                                                           //
        controlCentre.makeAMove();
        stable.proceedToStable();                                   // finished the run, the pair must return to Stable.
    }

    /**
     * Returns the pair Horse/Jockey state representation given by the {@link HorseJockeyState}
     * enumeration.
     *
     * @return the current spectator {@link HorseJockeyState}.
     * @see HorseJockeyState
     */
    public synchronized HorseJockeyState getHorseJockeyState() {
        return state;
    }

    /**
     * Sets the HorseJockey's state, from the possible available {@link HorseJockeyState}
     * enumeration.
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
     * Returns the pair Horse/Jockey ability to move forward on its track of the Racing Track.
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

    public synchronized void setRacingTrack(RacingTrack racingTrack) {
        this.racingTrack = racingTrack;
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

    private GeneralInformationRepository repository;

    private BettingCentre bettingCentre;
}
