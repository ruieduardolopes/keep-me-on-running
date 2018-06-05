package entities;

import hippodrome.ControlCentre;
import hippodrome.RacingTrack;

/**
 * Enumeration of all Spectator's states, given on its lifecycle.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Spectator#run()
 * @since 0.1
 * @version 2.0
 */
public enum SpectatorState {
    /**
     * This is a <strong>blocking state</strong>. Here, the {@link Spectator} is waken up by the operation
     * {@link hippodrome.Paddock#proceedToPaddock(int)} of the last pair Horse/Jockey to reach the {@link hippodrome.Paddock}.
     */
    WAITING_FOR_A_RACE_TO_START,
    /**
     * This is a <strong>blocking state</strong>. Here, the {@link Spectator} is waken up by the operation
     * {@link RacingTrack#proceedToStartLine(int)} of the pair Horse/Jockey to leave the {@link hippodrome.Paddock}.
     */
    APPRAISING_THE_HORSES,
    /**
     * This is a <strong>blocking state</strong> with transition. Here, the {@link Spectator} blocks in queue
     * while waiting to place the bet.
     *
     * Note that he or she is waken up by the {@link Broker} when the bet is done.
     */
    PLACING_A_BET,
    /**
     * This is a <strong>blocking state</strong>. Here, the {@link Spectator} is waken up by the operation
     * {@link ControlCentre#reportResults()} of the broker
     */
    WATCHING_A_RACE,
    /**
     * This is a <strong>blocking state</strong> with transition. Here, the {@link Spectator} blocks in queue
     * while waiting to receive the dividends.
     *
     * He or she is waken up by the {@link Broker} when the transaction is completed.
     */
    COLLECTING_THE_GAINS,
    /**
     * This is the final state. This is a transition state.
     */
    CELEBRATING
}
