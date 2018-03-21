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
    }

    /**
     * Signal given by the {@link entities.Broker} in order to call all the paradded pairs Horse/Jockey
     * on the {@link Paddock} to the Racing Track's start line.
     */
    public synchronized void proceedToStartLine() {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_START_LINE);
    }

    /**
     * Let a pair Horse/Jockey {@code horse} make a move on the track, accordingly to its abilities to move.
     *
     * @param horse the pair Horse/Jockey which wants to make a move.
     * @param isLastPairHorseJockey boolean value which validates if {@code horse} is the last one arriving.
     */
    public synchronized void makeAMove(HorseJockey horse, boolean isLastPairHorseJockey) {
        if (isLastPairHorseJockey) {
            ((Broker) Thread.currentThread()).setBrokerState(BrokerState.SETTLING_ACCOUNTS);
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNING);
    }

    /**
     * Verification if the pair Horse/Jockey {@code horse} has crossed the finish line.
     *
     * @param horse the pair Horse/Jockey which we want to verify if had crossed the finish line.
     *
     * @return {@code true} if the pair Horse/Jockey had crossed the finish line; otherwise it will return {@code false}.
     */
    public synchronized boolean hasFinishLineBeenCrossed(HorseJockey horse) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINNISH_LINE);

        return false;
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
}
