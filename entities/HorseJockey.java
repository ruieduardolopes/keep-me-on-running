package entities;

/**
 * Implementation of a pair Horse/Jockey, the main horse race character. This entity,
 * belonging to the {@link entities} package, represents one of N pairs in a competition,
 * being the bets target (by the {@link Spectator}) and having its conduct coordinated by the
 * {@link Broker}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Broker
 * @see Spectator
 * @since 1.0
 */
public class HorseJockey implements Runnable {
    /**
     * Definition of the HorseJockey's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code HorseJockey} pair, since its displacement from the
     * {@link hippodrome.Paddock}, to the {@link hippodrome.Stable}, passing by the {@link hippodrome.RacingTrack}.
     */
    @Override
    public void run() {

    }
}
