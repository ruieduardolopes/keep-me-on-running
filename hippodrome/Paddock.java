package hippodrome;

import entities.HorseJockey;
import entities.HorseJockeyState;
import hippodrome.actions.Race;

/**
 * Place where the horses are paradded before the {@link entities.Spectator}s. As the
 * pairs Horse/Jockey comes here, it means that each and everyone of them is getting prepared
 * to be on the next race.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 0.1
 */
public class Paddock {
    /**
     * Signal given by the {@link entities.Broker} in order to proceed to here (the {@link Paddock}).
     * This signal is given calling only the pairs Horse/Jockey which are supposed to run on the next {@link Race},
     * identified by the number {@code raceNumber}.
     *
     * @param raceNumber number identification of the race which is about to begin.
     *
     * @return {@code true} if the last pair Horse/Jockey has been moved to the {@link Paddock}; otherwise
     * {@code false}.
     */
    public static boolean proceedToPaddock(int raceNumber) {

        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_PADDOCK);
        return false;
    }

    /**
     * Signal that the last {@link entities.Spectator} has reached the {@link Paddock}.
     *
     * @param isTheLastSpectator {@code boolean} variable which identifies when the last {@link entities.Spectator}
     *                                          has reached the premises.
     */
    public static void goCheckHorses(boolean isTheLastSpectator) {

    }

    /**
     * Signal given by the {@link entities.Broker} in order to the {@link entities.Spectator}s go check the
     * pairs Horse/Jockey and verify if there are more to come. This must be done in order to establish a
     * more accurate bet on the probable winner.
     *
     * @return {@code true} if the last {@link entities.Spectator} has moved to the {@link Paddock}; otherwise
     * {@code false}.
     */
    public static boolean goCheckHorses() {
        return false;
    }

}
