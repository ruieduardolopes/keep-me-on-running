package hippodrome;

import entities.Spectator;
import hippodrome.actions.Race;

/**
 * Place where the {@link Spectator}s go to enjoy the race (at a Watching Stand) and the {@link entities.Broker}
 * controls the races and publishes its results.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 1.0
 */
public class ControlCentre {
    /**
     * Signal given by the {@link entities.Broker} to summon all the horses from the {@link Stable} to the
     * {@link Paddock}. Such horses, representation of pairs Horse/Jockey's, must be identified by a race
     * number identification, such as the {@link entities.Broker}'s call should only be relative to some of the
     * pairs - the ones which will run side-by-side on the same {@link Race}, on the same {@link RacingTrack}.
     *
     * @param raceNumber number identification of the next {@link Race}.
     */
    public static void summonHorsesToPaddock(int raceNumber) {

    }

    /**
     * Signal given by the {@link entities.Broker} to start a {@link Race} identified by {@code raceNumber}.
     *
     * @param raceNumber number idenitification of the next {@link Race} to be started as of this instant.
     */
    public static void startTheRace(int raceNumber) {

    }

    /**
     * Start entertaining the guests (representation of the {@link Spectator}s), as the {@link entities.Broker}'s actions
     * can be considered as terminated.
     */
    public static void entertainTheGuests() {

    }

    /**
     * Wait for the next signal of the {@link entities.Broker} in order to prepare a new {@link Race}, identified
     * by the number {@code raceNumber}.
     *
     * @param raceNumber number identification of the next {@link Race} to begin.
     *
     * @return {@code true} if the next race is still not prepared to begin; otherwise {@code false}.
     */
    public static boolean waitForTheNextRace(int raceNumber) {
        return false;
    }

    /**
     * Signal set out to all the {@link Spectator}s in order to watch the {@link Race} number {@code raceNumber}.
     *
     * @param raceNumber number identification of the {@link Race} which is about to start.
     */
    public static void goWatchTheRace(int raceNumber) {

    }

    /**
     * Verification if a given {@code spectator} has won his (or hers) bet.
     *
     * @param spectator {@link Spectator} who has placed a bet earlier and wants to verify if it did win indeed or not.
     *
     * @return {@code true} if {@code spectator} has won his (or hers) bet; otherwise, it will return {@code false}.
     */
    public static boolean haveIWon(Spectator spectator) {
        return false;
    }

    /**
     * Relax a bit from the games, as this is could be the final transition of a {@link Spectator} lifecycle.
     */
    public static void relaxABit() {

    }

    /**
     * Publishing of the results by the {@link entities.Broker} performing its job.
     */
    public static void reportResults() {

    }

    /**
     * Verification of bet's winners.
     *
     * @return {@code true} if anybody had won indeed; otherwise it will return {@code false}.
     */
    public static boolean areThereAnyWinners() {
        return false;
    }

    /**
     * Signal given by the {@link entities.Broker} to summon all the horses from the {@link Stable} to the
     * {@link Paddock}.
     */
    public static void summonHorsesToPaddock() {

    }

    /**
     * Signal given by the last pair Horse/Jockey which exits the {@link Stable} in direction to the {@link Paddock}.
     */
    public static void proceedToPaddock() {

    }

    /**
     * Signal given by the last {@link Spectator} which arrives at the {@link Paddock} to watch the horses, before placing
     * a bet.
     */
    public static void goCheckHorses() {

    }
}
