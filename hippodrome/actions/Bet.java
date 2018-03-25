package hippodrome.actions;

/**
 * Representation of a bet, which is done on the {@link hippodrome.BettingCentre}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 1.0
 */
public class Bet {
    /**
     * Creates a bet, given an identification {@code horseJockeyId} and an amount of money {@code money}.
     *
     * @param horseJockeyId the identification of the pair Horse/Jockey to bet on.
     * @param amount the amount of money (as an Integer) which we want to put on.
     */
    public Bet(int horseJockeyId, int amount) {
        this.horseJockeyId = horseJockeyId;
        this.amount = amount;
    }

    /**
     * Gets the number identification of a specific pair Horse/Jockey on this bet.
     *
     * @return the number identification of this bet's pair Horse/Jockey.
     */
    public int getHorseJockeyId() {
        return horseJockeyId;
    }

    /**
     * Gets the amount of money spent on this bet.
     *
     * @return the amount of money spent on this bet.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Internal structure referring to the number identification of a given pair Horse/Jockey.
     */
    private int horseJockeyId;

    /**
     * Internal structure referring to the amount of money spent on this bet.
     */
    private int amount;
}
