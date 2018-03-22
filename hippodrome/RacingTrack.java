package hippodrome;

import entities.Broker;
import entities.BrokerState;
import entities.HorseJockeyState;
import hippodrome.actions.Race;
import entities.HorseJockey;

/**
 * Place where the races take place. Here each race is represented by an element
 * of the class {@link Race}, described by a given number of tracks, an identification
 * and a distance.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 0.1
 */
public class RacingTrack {
    public RacingTrack(Race race, GeneralInformationRepository repository) {
        this.race = race;
        this.repository = repository;
        this.numberOfHorses = race.getNumberOfTracks();
        this.currentHorsesPositions = new int[this.numberOfHorses];
    }

    /**
     * Signal given by the {@link entities.Broker} in order to call all the paradded pairs Horse/Jockey
     * on the {@link Paddock} to the Racing Track's start line.
     */
    public synchronized void proceedToStartLine() {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
        // wait for Broker to start the race with STR()
        // change the HJ's state to ATSL
    }

    /**
     * Let a pair Horse/Jockey {@code horse} make a move on the track, accordingly to its abilities to move.
     *
     * @param horseId the identification of the pair Horse/Jockey which wants to make a move.
     */
    public synchronized void makeAMove(int horseId) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNING);
        // wait while this thread ID is different than the currentIndex;
        // if this horse crossed the line, change the state to ATFL
        // increment our position on array currentPosition and increment the index on modulo.
        // i.e. currentIndex += (currentIndex + 1) % currentHorsesPosition.length;
        // notify the next horse
    }

    /**
     * Verification if the pair Horse/Jockey {@code horse} has crossed the finish line.
     *
     * @param horseJockeyId the pair Horse/Jockey which we want to verify if had crossed the finish line.
     *
     * @return {@code true} if the pair Horse/Jockey had crossed the finish line; otherwise it will return {@code false}.
     */
    public synchronized boolean hasFinishLineBeenCrossed(int horseJockeyId) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINNISH_LINE);
        // if displacement of this horse is equal to race distance return true; otherwise false. check array
        return false;
    }

    public synchronized void startTheRace() {
        // notify first horse to unlock the starting line (ATSL state)
    }

    public synchronized Race getRace() {
        return race;
    }

    /**
     * A representation of a race with an identification, a distance and a number of tracks. This is made using the class
     * {@link Race}.
     */
    private Race race = null;

    /**
     *
     */
    private GeneralInformationRepository repository;

    private int[] currentHorsesPositions;

    private int numberOfHorses;

    private int currentIndex;
}
