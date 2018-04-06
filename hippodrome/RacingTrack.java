package hippodrome;

import entities.HorseJockeyState;
import hippodrome.actions.Race;
import entities.HorseJockey;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Place where the races take place. Here each race is represented by an element
 * of the class {@link Race}, described by a given number of tracks, an identification
 * and a distance.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 1.1
 */
public class RacingTrack {
    /**
     * Creates a Racing Track.
     * <br>
     * This constructor creates a Racing Track giving a race. Plus, an instance of the
     * repository is also given in order to report status changes on the course of its actions.
     *
     * @param race A race to be executed over this Racing Track.
     * @param repository An instance of a {@link GeneralInformationRepository} in order to report all the actions and
     *                   log each and every moment.
     */
    public RacingTrack(Race race, GeneralInformationRepository repository) {
        this.race = race;
        this.repository = repository;
        this.currentHorsesPositions = new int[race.getNumberOfTracks()];
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Start Line ({@code ATSL}) and waits till the last
     * {@link RacingTrack#startTheRace()} is performed by the Broker.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void proceedToStartLine() throws InterruptedException {
        horsesToRun.add(((HorseJockey)Thread.currentThread()).getIdentification());
        numberOfFinishedHorses = 0;
        repository.setHorseJockeyFinalStandPosition(((HorseJockey)Thread.currentThread()).getIdentification(), 0);
        while (brokerDidNotOrderToStartTheRace) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The proceedToStartLine() has been interrupted on its wait().");
            }
        }
    }

    /**
     * Let a pair Horse/Jockey {@code horse} make a move on the track, accordingly to its abilities to move.
     * <br>
     * This methods waits till the {@link RacingTrack#makeAMove(int)} of the last pair Horse/Jockey. Plus, it also
     * notifies when the first pair Horse/Jockey has crossed the finish line.
     *
     * @param horseId the identification of the pair Horse/Jockey which wants to make a move.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void makeAMove(int horseId) throws InterruptedException {
        while (horsesToRun.peek() != horseId) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The makeAMove() has been interrupted on its wait().");
            }
        }
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNING);
        int thisHorse = horsesToRun.remove();
        currentHorsesPositions[thisHorse] += (int)(Math.random()*((HorseJockey) Thread.currentThread()).getAbility()) + 1;
        repository.setHorseJockeyPositionOnTrack(horseId, currentHorsesPositions[thisHorse]);
        repository.setHorseJockeyNumberOfIncrementsDid(horseId, repository.getHorseJockeyNumberOfIncrementsDid(horseId)+1);
        if (currentHorsesPositions[thisHorse] >= race.getDistance()) {
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINNISH_LINE);
            hasFirstHorseCrossedTheFinishLine = true;
        } else {
            horsesToRun.add(thisHorse);
        }
        notifyAll();
        if (hasFirstHorseCrossedTheFinishLine && raceIsAboutToEnd) {
            raceIsAboutToEnd = false;
            winner = ((HorseJockey)Thread.currentThread()).getIdentification();
        }
    }

    /**
     * Verification if the pair Horse/Jockey {@code horse} has crossed the finish line.
     * <br>
     * This method reset the condition variable of {@link RacingTrack#proceedToStartLine()}.
     *
     * @param horseJockeyId the pair Horse/Jockey which we want to verify if had crossed the finish line.
     *
     * @return {@code true} if the pair Horse/Jockey had crossed the finish line; otherwise it will return {@code false}.
     */
    public synchronized boolean hasFinishLineBeenCrossed(int horseJockeyId) {
        if (currentHorsesPositions[horseJockeyId] >= race.getDistance()) {
            brokerDidNotOrderToStartTheRace = true;
            markFinalPosition(horseJockeyId);
            return true;
        }
        return false;
    }

    /**
     * Signal that the race is about to start, by the {@link entities.Broker} performing its job.
     * <br>
     * Note that this method changes the value of the condition variable to the {@link RacingTrack#proceedToStartLine()}
     * wait condition, notifying its changes.
     */
    public synchronized void startTheRace() {
        brokerDidNotOrderToStartTheRace = false;
        notifyAll();
    }

    /**
     * Gets the current Race happening on this Racing Track.
     *
     * @return the Race of this Racing Track.
     */
    public synchronized Race getRace() {
        return race;
    }

    /**
     * Gets the current winner of a race on this Racing Track.
     *
     * @return the current winner as the identification of a pair Horse/Jockey; otherwise {@code -1} if the race is not over yet.
     */
    public synchronized int getWinner() {
        return winner;
    }

    /**
     * Marks the final position of the pair Horse/Jockey identified with {@code horse}.
     *
     * @param horse the identification of the pair Horse/Jockey which has finished its race.
     */
    private void markFinalPosition(int horse) {
        numberOfFinishedHorses++;
        repository.setHorseJockeyFinalStandPosition(horse, numberOfFinishedHorses);
    }

    /**
     * A representation of a race with an identification, a distance and a number of tracks. This is made using the class
     * {@link Race}.
     */
    private Race race;

    /**
     * A {@link Queue} representing the movement order of the pairs Horse/Jockey on field. This have an implementation of the
     * {@link LinkedBlockingQueue} of Java's Collections Library.
     */
    private Queue<Integer> horsesToRun = new LinkedBlockingQueue<>();

    /**
     * Internal structure correspondent to the horses which have completed the race.
     */
    private int numberOfFinishedHorses;

    /**
     * An array with all the positions of the pairs Horse/Jockeys currently on action.
     */
    private int[] currentHorsesPositions;

    /**
     * The current winner of this race as the identification of the pair Horse/Jockey which has crossed the finish line first.
     * <br>
     * Note that this winner, if the race is not finished, has its value of {@code -1}.
     */
    private int winner = -1;

    /**
     * Condition variable for the first pair Horse/Jockey to announce that has crossed the finish line.
     * <br>
     * This is a condition variable of {@link RacingTrack#makeAMove(int)} and it is reset on the
     * {@link RacingTrack#makeAMove(int)} method itself.
     */
    private boolean hasFirstHorseCrossedTheFinishLine = false;

    /**
     * Condition variable for the Broker to order the race to start.
     * <br>
     * This is a condition variable of {@link RacingTrack#proceedToStartLine()} and it is reset on the
     * {@link RacingTrack#hasFinishLineBeenCrossed(int)} method.
     */
    private boolean brokerDidNotOrderToStartTheRace = true;

    /**
     * Condition variable for noticing when a race is about to end.
     * <br>
     * This is a condition variable of {@link RacingTrack#makeAMove(int)}.
     */
    private boolean raceIsAboutToEnd = true;

    /**
     * The {@link GeneralInformationRepository} instance where all this region's actions will be reported.
     */
    private GeneralInformationRepository repository;
}


