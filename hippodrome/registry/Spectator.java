package hippodrome.registry;

import entities.SpectatorState;

/**
 * Class which saves a snapshot of a Spectator.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see HorseJockey
 * @version 0.1
 * @since 0.1
 */
public class Spectator {
    /**
     * Get the status of a {@code Spectator}.
     *
     * @return a representation (abbreviated) of this {@link Spectator}'s state.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get the current amount of money collected by this {@link Spectator}.
     *
     * @return the total amount of money that this {@link Spectator} has available to spend.
     */
    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    /**
     * Get the current bet selection performed by this {@link Spectator} on a
     * pair Horse/Jockey which runs at the racing track.
     *
     * @return the identification number of the pair Horse/Jockey running.
     */
    public int getBetSelection() {
        return betSelection;
    }

    /**
     * Get the current amount of money spent betting on a pair Horse/Jockey.
     *
     * @return the amount of money bet.
     */
    public int getBetAmount() {
        return betAmount;
    }

    /**
     * Sets a new amount of money of a spectator.
     *
     * @param amountOfMoney new amount of money available to bet.
     */
    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * Sets the current status of this {@link Spectator}.
     *
     * @param status the new status (Enumeration of the type {@link entities.SpectatorState} of this {@link Spectator}.
     */
    public void setStatus(SpectatorState status) {
        this.status = "";
        for (String word : status.name().split("_")) {
            this.status += word.charAt(0);
        }
    }

    /**
     * Sets the new bet on a pair Horse/Jockey which runs at the racing track.
     *
     * @param horse the number identification of the horse to be betting on.
     */
    public void setBetSelection(int horse) {
        this.betSelection = horse;
    }

    /**
     * Sets the new amount of money spent on a new bet.
     *
     * @param amount the amount of money spent on a new bet (as an integer).
     */
    public void setBetAmount(int amount) {
        this.betAmount = amount;
    }

    /**
     * Current status of this {@link Spectator}.
     */
    private String status = "";

    /**
     * Current amount of money of this {@link Spectator}.
     */
    private int amountOfMoney;


    /**
     * Current {@link Spectator}'s bet selection of pair Horse/Jockey on run.
     */
    private int betSelection;

    /**
     * Current amount of money spent on the current bet.
     */
    private int betAmount;
}
