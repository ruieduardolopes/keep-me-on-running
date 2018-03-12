package entities;

/**
 * Enumeration of all pair Horse/Jockey's states, given on its lifecycle.
 *
 * @see HorseJockey#run()
 */
public enum HorseJockeyState {
    AT_THE_STABLE,
    AT_THE_PADDOCK,
    AT_THE_START_LINE,
    RUNNING,
    AT_THE_FINNISH_LINE
}
