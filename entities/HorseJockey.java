package entities;

import hippodrome.ControlCentre;
import hippodrome.Paddock;
import hippodrome.RacingTrack;
import hippodrome.Stable;

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
public class HorseJockey implements Runnable {
    /**
     * Creates a pair Horse/Jockey with a given {@code identification} and an {@code ability} to run.
     *
     * @param identification number which identifies this pair Horse/Jockey.
     * @param ability number with characterizes the Horse ability to run.
     */
    public HorseJockey(int identification, int ability) {
        this.identification = identification;
        this.ability = ability;
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
        Stable.proceedToStable();                                   // Stable's call for this pair Horse/Jockey;
        Stable.proceedToPaddock();                                  // alarm the Horses on Stable to go to the Paddock;
        boolean isLastPair = Paddock.proceedToPaddock(raceNumber);  // verify if this Horse is the last on his go;
        if (isLastPair) {                                           // if this is the last Horse to go to Paddock then
            ControlCentre.proceedToPaddock();                       //    the Control Centre must know it can proceed;
        }                                                           //
        RacingTrack.proceedToStartLine();                           // Racing track's call for horses on the start line;
        while (!RacingTrack.hasFinishLineBeenCrossed(this)) {       // while this horse has not crossed the finish line
            RacingTrack.makeAMove(this, isLastPair);                //    it should make a move forward, to reach it;
        }                                                           //
        Stable.proceedToStable();                                   // finished the run, the pair must return to Stable.
    }

    /**
     * Returns the pair Horse/Jockey state representation given by the {@link HorseJockeyState}
     * enumeration.
     *
     * @return the current spectator {@link HorseJockeyState}.
     * @see HorseJockeyState
     */
    public HorseJockeyState getHorseJockeyState() {
        return state;
    }

    /**
     * Sets the HorseJockey's state, from the possible available {@link HorseJockeyState}
     * enumeration.
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
    public int getIdentification() {
        return identification;
    }

    /**
     * Returns the pair Horse/Jockey ability to move forward on its track of the Racing Track.
     *
     * @return an integer representation of the ability.
     */
    public int getAbility() {
        return ability;
    }

    /**
     * Returns the pair Horse/Jockey race number identification, in order to compete in the Racing Track.
     *
     * @return an integer representing the identification.
     */
    public int getRaceNumber() {
        return raceNumber;
    }

    /**
     * Sets a new race number identification for this pair Horse/Jockey, appliable when this same entity runs in
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
}
