package hippodrome.registry;

import entities.HorseJockeyState;

/**
 * Class which saves a snapshot of a pair Horse/Jockey statuses.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Spectator
 * @version 1.0
 * @since 0.1
 */
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
     * Get the current probability to win of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @return the probability (from 0.0 to 1.0 - {@code double}) of this pair Horse/Jockey ({@link HorseJockey}) win.
     */
    public double getProbabilityToWin() {
        return probabilityToWin;
    }

    /**
     * Get the current number of increments (iterations) of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @return the number of iterations this pair Horse/Jockey ({@link HorseJockey}) to reach the current position on track.
     */
    public int getNumberOfIncrementsDone() {
        return numberOfIncrementsDid;
    }

    /**
     * Get the current position on track of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @return the number representing the position on track of this pair Horse/Jockey ({@link HorseJockey}).
     */
    public int getPositionOnTrack() {
        return positionOnTrack;
    }

    /**
     * Get the final standing position of this pair Horse/Jockey ({@link HorseJockey}) on the run, as it terminates.
     *
     * @return the final standing position this pair Horse/Jockey ({@link HorseJockey}).
     */
    public int getFinalStandPosition() {
        return finalStandPosition;
    }

    /**
     * Set the current status of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @param status the new status (Enumeration of the type {@link entities.HorseJockeyState} of this pair Horse/Jockey ({@link HorseJockey}).
     */
    public void setStatus(HorseJockeyState status) {
        this.status = "";
        for (String word : status.name().split("_")) {
            if (word.length() != 1) {
                this.status += word.charAt(0);
            }
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
     * Set the probability to win of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @param probabilityToWin the new probability to win of this pair Horse/Jockey ({@link HorseJockey}).
     */
    public void setProbabilityToWin(double probabilityToWin) {
        this.probabilityToWin = probabilityToWin;
    }

    /**
     * Set the number of increments (iterations) of this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @param numberOfIncrementsDid the new number of increments of this pair Horse/Jockey ({@link HorseJockey}).
     */
    public void setNumberOfIncrementsDid(int numberOfIncrementsDid) {
        this.numberOfIncrementsDid = numberOfIncrementsDid;
    }

    /**
     * Set the new position on track this pair Horse/Jockey ({@link HorseJockey}).
     *
     * @param positionOnTrack the new position on track of this pair Horse/Jockey ({@link HorseJockey}).
     */
    public void setPositionOnTrack(int positionOnTrack) {
        this.positionOnTrack = positionOnTrack;
    }

    /**
     * Set the final standing position of this pair Horse/Jockey ({@link HorseJockey}), as the race terminates.
     *
     * @param finalStandPosition the final standing position of this pair Horse/Jockey ({@link HorseJockey}), as the race terminates.
     */
    public void setFinalStandPosition(int finalStandPosition) {
        this.finalStandPosition = finalStandPosition;
    }

    /**
     * Current status of this pair Horse/Jockey ({@link HorseJockey}).
     */
    private String status = "";

    /**
     * Current ability of this pair Horse/Jockey ({@link HorseJockey}).
     */
    private int ability;

    /**
     * Current probability to win of this pair Horse/Jockey.
     */
    private double probabilityToWin;

    /**
     * Current number of iterations (increments) did in order to reach the current position on track.
     */
    private int numberOfIncrementsDid;

    /**
     * Current position on track of this pair Horse/Jockey.
     */
    private int positionOnTrack;

    /**
     * Current race position, applicable when the race is over. When the race is still happening,
     * then its value should be 0 (zero).
     */
    private int finalStandPosition;
}
