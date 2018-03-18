package hippodrome.registry;

import entities.HorseJockeyState;

public class HorseJockey {
    /**
     * Get the status of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @return a representation (abbreviated) of this pair Horse/Jockey ({@link HorseJockey})'s state.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get the current ability of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @return the number representing the current ability if this pair Horse/Jockey ({@link HorseJockey}).
     */
    public int getAbility() {
        return ability;
    }

    /**
     * Set the current status of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @param status the new status (Enumeration of the type {@link entities.HorseJockeyState} of this pair Horse/Jockey ({@link HorseJockey}).
     */
    public void setStatus(HorseJockeyState status) {
        for (String word : status.name().split("_")) {
            this.status += word.charAt(0);
        }
    }

    /**
     * Set the current ability of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @param ability the new ability value of this pair Horse/Jockey ({@link HorseJockey}).
     */
    public void setAbility(int ability) {
        this.ability = ability;
    }

    /**
     * Current status of this pair Horse/Jockey ({@link HorseJockey}).
     */
    private String status = "";

    /**
     * Current ability of this pair Horse/Jockey ({@link HorseJockey}).
     */
    private int ability;
}
