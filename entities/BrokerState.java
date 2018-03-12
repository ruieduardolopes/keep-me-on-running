package entities;

/**
 * Enumeration of all Broker's states, given on its lifecycle.
 *
 * @see Broker#run()
 */
public enum BrokerState {
    OPENING_THE_EVENT,
    ANNOUNCING_NEXT_RACE,
    WAITING_FOR_BETS,
    SUPERVISING_THE_RACE,
    SETTLING_ACCOUNTS,
    PLAYING_HOST_AT_THE_BAR
}
