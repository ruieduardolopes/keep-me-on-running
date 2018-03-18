package hippodrome.registry;

import entities.SpectatorState;

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
     * Set a new amount of money of a spectator.
     *
     * @param amountOfMoney new amount of money available to bet.
     */
    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * Set the current status of this {@link Spectator}.
     *
     * @param status the new status (Enumeration of the type {@link entities.SpectatorState} of this {@link Spectator}.
     */
    public void setStatus(SpectatorState status) {
        for (String word : status.name().split("_")) {
            this.status += word.charAt(0);
        }
    }

    /**
     * Current status of this {@link Spectator}.
     */
    private String status = "";

    /**
     * Current amount of money of this {@link Spectator}.
     */
    private int amountOfMoney;
}
