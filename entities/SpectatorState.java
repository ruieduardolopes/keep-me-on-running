package entities;

/**
 * Enumeration of all Spectator's states, given on its lifecycle.
 *
 * @see Spectator#run()
 */
public enum SpectatorState {
    WAITING_FOR_A_RACE_TO_START,
    APPRAISING_THE_HORSES,
    PLACING_A_BET,
    WATCHING_A_RACE,
    COLLECTING_THE_GAINS,
    CELEBRATING
}
