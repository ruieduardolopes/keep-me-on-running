package entities;

/**
 * Enumeration of all Entities's states, given on its lifecycle.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Broker#run()
 * @since 0.1
 * @version 2.0
 */
public enum BrokerState {
    /**
     * Initial Entities's state. This is a state of transition.
     */
    OPENING_THE_EVENT,
    /**
     * This is a <strong>blocking state</strong>. Here, the Entities is waken up by the operation
     * {@link hippodrome.Paddock#goCheckHorses()} of the last {@link Spectator} to reach the
     * {@link hippodrome.Paddock}.
     */
    ANNOUNCING_NEXT_RACE,
    /**
     * This is a <strong>blocking state</strong> with transition. Here, the broker is waken up
     * by the operation {@link hippodrome.BettingCentre#placeABet(int, int, int)}
     * of each of the spectators ({@link Spectator}) and <strong>blocks again</strong> after the bet is accepted.
     *
     * The transition only occurs after the betting of all spectators is considered done.
     */
    WAITING_FOR_BETS,
    /**
     * This is a <strong>blocking state</strong>. Here, the broker is waken up by the operation
     * {@link hippodrome.RacingTrack#makeAMove(int)} of the last pair Horse/Jockey ({@link HorseJockey})
     * crossing the finishing line.
     */
    SUPERVISING_THE_RACE,
    /**
     * This is a <strong>blocking state</strong> with transition. Here, the broker is waken up by the
     * operation {@link hippodrome.BettingCentre#goCollectTheGains()} of each winning {@link Spectator}
     * and <strong>blocks again</strong> after honouring the bet.
     *
     * The transition only occurs when all spectators have been paid.
     */
    SETTLING_ACCOUNTS,
    /**
     * This is the final Entities's state. This is a state of transition.
     */
    PLAYING_HOST_AT_THE_BAR
}
