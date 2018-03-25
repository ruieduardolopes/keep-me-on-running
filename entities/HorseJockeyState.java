package entities;

import hippodrome.ControlCentre;
import hippodrome.Paddock;

/**
 * Enumeration of all pair Horse/Jockey's states, given on its lifecycle.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see HorseJockey#run()
 * @since 0.1
 * @version 0.1
 */
public enum HorseJockeyState {
    /**
     * This is a <strong>blocking state</strong>. Here, the pair Horse/Jockey ({@link HorseJockey}) is waken up
     * by one of the following operations of the {@link Broker}: {@link hippodrome.Stable#summonHorsesToPaddock(int)}
     * during the races, or {@link ControlCentre#entertainTheGuests()}, at the end.
     */
    AT_THE_STABLE,
    /**
     * This is a <strong>blocking state</strong>. Here, the pair Horse/Jockey is waken up by the
     * operation {@link Paddock#goCheckHorses()} of the last {@link Spectator} to reach the {@link Paddock}.
     */
    AT_THE_PADDOCK,
    /**
     * This is a <strong>blocking state</strong>. Here, the pair Horse/Jockey is waken up by the
     * operation {@link ControlCentre#startTheRace()} of the {@link Broker} (the first) or by the
     * operation {@link hippodrome.RacingTrack#makeAMove(int)} of another pair Horse/Jockey.
     */
    AT_THE_START_LINE,
    /**
     * This is a <strong>blocking state</strong> with transition. Here, the pair Horse/Jockey
     * <strong>blocks</strong> after carrying out a position increment, unless he crosses the finishing line.
     *
     * <strong>Note that he always wakes up the next pair Horse/Jockey that has not completed the race yet!</strong>
     */
    RUNNING,
    /**
     * This is a transition state.
     */
    AT_THE_FINNISH_LINE
}
