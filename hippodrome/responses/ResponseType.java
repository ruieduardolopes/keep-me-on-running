package hippodrome.responses;

/**
 * Class that provides the encoding to the various type of responses.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 3.0
 * @version 3.0
 */
public enum ResponseType {

    /**
     * Response of the Betting Centre to do Accept the Bets.
     */
    BETTING_CENTRE_ACCEPT_THE_BETS,

    /**
     * Response of the Betting Centre to do Place a Bet.
     */
    BETTING_CENTRE_PLACE_A_BET,

    /**
     * Response of the Betting Centre to do Honour the Bets.
     */
    BETTING_CENTRE_HONOUR_THE_BETS,

    /**
     * Response of the Betting Centre to do Go Collect the Gains.
     */
    BETTING_CENTRE_GO_COLLECT_THE_GAINS,

    /**
     * Response of the Control Centre to do Start the Race.
     */
    CONTROL_CENTRE_START_THE_RACE,

    /**
     * Response of the Control Centre to do Entertain the Guests.
     */
    CONTROL_CENTRE_ENTERTAIN_THE_RACE,

    /**
     * Response of the Control Centre to do Wait for the Next Race.
     */
    CONTROL_CENTRE_WAIT_FOR_THE_NEXT_RACE,

    /**
     * Response of the Control Centre to do Go Watch the Race.
     */
    CONTROL_CENTRE_GO_WATCH_THE_RACE,

    /**
     * Response of the Control Centre to do Relax a Bit.
     */
    CONTROL_CENTRE_RELAX_A_BIT,

    /**
     * Response of the Control Centre to do Summon Horses to Paddock.
     */
    CONTROL_CENTRE_SUMMON_HORSES_TO_PADDOCK,

    /**
     * Response of the Control Centre to do Proceed to Paddock.
     */
    CONTROL_CENTRE_PROCEED_TO_PADDOCK,

    /**
     * Response of the Paddock to do Go Check Horses.
     */
    PADDOCK_GO_CHECK_HORSES,

    /**
     * Response of the Paddock to do Proceed to Start Line.
     */
    PADDOCK_PROCEED_TO_START_LINE,

    /**
     * Response of the Racing Track to do Make a Move.
     */
    RACING_TRACK_MAKE_A_MOVE,

    /**
     * Response of the Stable to do Proceed to Stable.
     */
    STABLE_PROCEED_TO_STABLE,

    /**
     * Response of the Stable to do Proceed to Stable with a Race Identification.
     */
    STABLE_PROCEED_TO_STABLE_WITH_RACE_ID,
}
