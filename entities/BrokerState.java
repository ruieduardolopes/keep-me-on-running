package entities;

/**
 * Enumeration of all Broker's states, given on its lifecycle.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Broker#run()
 * @version 1.0
 */
public enum BrokerState {
    /**
     * Initial Broker's state. This is a state of transition.
     */
    OPENING_THE_EVENT,
    /**
     * This is a <strong>blocking state</strong>. Here, the Broker is waken up by the operation
     * {@link hippodrome.Paddock#goCheckHorses()} of the last {@link Spectator} to reach the
     * {@link hippodrome.Paddock}.
     */
    ANNOUNCING_NEXT_RACE,
    /**
     * This is a <strong>blocking state</strong> with transition. Here, the broker is waken up
     * by the operation {@link hippodrome.BettingCentre#placeABet(Spectator, int, HorseJockey)}
     * of each of the spectators ({@link Spectator}) and <strong>blocks again</strong> after the bet is accepted.
     *
     * The transition only occurs after the betting of all spectators is considered done.
     */
    WAITING_FOR_BETS,
    /**
     * This is a <strong>blocking state</strong>. Here, the broker is waken up by the operation
     * {@link hippodrome.RacingTrack#makeAMove(HorseJockey)} of the last pair Horse/Jockey ({@link HorseJockey})
     * crossing the finishing line.
     */
    SUPERVISING_THE_RACE,
    /**
     * This is a <strong>blocking state</strong> with transition. Here, the broker is waken up by the
     * operation {@link hippodrome.BettingCentre#goCollectTheGains(Spectator)} of each winning {@link Spectator}
     * and <strong>blocks again</strong> after honouring the bet.
     *
     * The transition only occurs when all spectators have been paid.
     */
    SETTLING_ACCOUNTS,
    /**
     * This is the final Broker's state. This is a state of transition.
     */
    PLAYING_HOST_AT_THE_BAR
}
