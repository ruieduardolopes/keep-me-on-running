package hippodrome;

import concepts.Race;
import entities.HorseJockey;

/**
 * Place where the races take place. Here each race is represented by an element
 * of the class {@link Race}, described by a given number of tracks, an identification
 * and a distance.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 1.0
 */
public class RacingTrack {
    /**
     * Signal given by the {@link entities.Broker} in order to call all the paradded pairs Horse/Jockey
     * on the {@link Paddock} to the Racing Track's start line.
     */
    public void proceedToStartLine() {

    }

    /**
     * Let a pair Horse/Jockey {@code horse} make a move on the track, accordingly to its abilities to move.
     *
     * @param horse the pair Horse/Jockey which wants to make a move.
     */
    public void makeAMove(HorseJockey horse) {

    }

    /**
     * Verification if the pair Horse/Jockey {@code horse} has crossed the finish line.
     *
     * @param horse the pair Horse/Jockey which we want to verify if had crossed the finish line.
     *
     * @return {@code true} if the pair Horse/Jockey had crossed the finish line; otherwise it will return {@code false}.
     */
    public boolean hasFinishLineBeenCrossed(HorseJockey horse) {
        return false;
    }

    /**
     * A representation of a race with an identification, a distance and a number of tracks. This is made using the class
     * {@link Race}.
     */
    private Race race = null;
}
