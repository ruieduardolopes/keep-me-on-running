package hippodrome;

import clients.GeneralInformationRepositoryStub;
import entities.HorseJockeyState;
import hippodrome.actions.Race;
import hippodrome.responses.Response;
import hippodrome.responses.ResponseType;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static configurations.SimulationConfigurations.*;

/**
 * Place where the races take place. Here each race is represented by an element
 * of the class {@link Race}, described by a given number of tracks, an identification
 * and a distance.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 2.0
 */
public class RacingTrack implements RacingTrackInterface {

    /**
     * Creates a Racing Track.
     * <br>
     * This constructor creates a Racing Track giving a race. Plus, an instance of the
     * repository is also given in order to report status changes on the course of its actions.
     *
     * @param race A race to be executed over this Racing Track.
     * @throws Exception if a illegal argument is sent.
     */
    private RacingTrack(Race race) throws Exception {
        try {
            this.race = race;
            this.currentHorsesPositions = new int[race.getNumberOfTracks()];
            this.repository.setRaceDistance(race.getDistance());
            this.repository.setRaceNumber(race.getIdentification());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * Get a singleton instance of a General Repository of Information.
     *
     * @return an instance of the General Repository of Information.
     * @throws Exception if a illegal argument is sent.
     */
    public static RacingTrack getInstance() throws Exception {
        try {
            if (instance == null) {
                instance = new RacingTrack(new Race(NUMBER_OF_TRACKS, identification, generateDistance()));
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * Changes the state of the pair Horse/Jockey to At the Start Line ({@code ATSL}) and waits till the last
     * {@link RacingTrack#startTheRace()} is performed by the Entities.
     *
     * @throws InterruptedException if the wait() is interrupted.
     */
    public synchronized void proceedToStartLine(int horseJockeyId) throws InterruptedException {
        currentHorsesPositions = new int[race.getNumberOfTracks()]; // <--
        horsesToRun.add(horseJockeyId);
        numberOfFinishedHorses = 0;
        repository.setHorseJockeyFinalStandPosition(horseJockeyId, 0);
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
    public synchronized Response makeAMove(int horseId, int ability) throws InterruptedException {
        Response response = null;
        while (horsesToRun.peek() != horseId) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                throw new InterruptedException("The makeAMove() has been interrupted on its wait().");
            }
        }
        //((ServiceProviderAgent)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNING);
        response = new Response(ResponseType.RACING_TRACK_MAKE_A_MOVE, HorseJockeyState.RUNNING, horseId);
        repository.setHorseJockeyStatus(horseId, HorseJockeyState.RUNNING);
        int thisHorse = horsesToRun.remove();
        currentHorsesPositions[thisHorse] += (int)(Math.random()*ability) + 1;
        repository.setHorseJockeyPositionOnTrack(horseId, currentHorsesPositions[thisHorse]);
        repository.setHorseJockeyNumberOfIncrementsDid(horseId, repository.getHorseJockeyNumberOfIncrementsDid(horseId)+1);
        if (currentHorsesPositions[thisHorse] >= race.getDistance()) {
            //((ServiceProviderAgent)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINNISH_LINE);
            response = new Response(ResponseType.RACING_TRACK_MAKE_A_MOVE, HorseJockeyState.AT_THE_FINNISH_LINE, horseId);
            repository.setHorseJockeyStatus(horseId, HorseJockeyState.AT_THE_FINNISH_LINE);
            hasFirstHorseCrossedTheFinishLine = true;
        } else {
            horsesToRun.add(thisHorse);
        }
        notifyAll();
        if (hasFirstHorseCrossedTheFinishLine && raceIsAboutToEnd) {
            raceIsAboutToEnd = false;
            winner = horseId;
        }
        return response;
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
    public synchronized boolean hasFinishLineBeenCrossed(int horseJockeyId) throws Exception {
        if (currentHorsesPositions[horseJockeyId] >= race.getDistance()) {
            try {
                brokerDidNotOrderToStartTheRace = true;
                markFinalPosition(horseJockeyId);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception();
            }
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
     * @throws Exception if a illegal argument is sent.
     */
    private void markFinalPosition(int horse) throws Exception {
        numberOfFinishedHorses++;
        try {
            repository.setHorseJockeyFinalStandPosition(horse, numberOfFinishedHorses);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * Generate a distance for a race.
     *
     * @return a distance of a race.
     */
    private static int generateDistance() {
        return (int)(Math.random() * (TRACK_DISTANCE_MAX_BOUND - TRACK_DISTANCE_MIN_BOUND)) + TRACK_DISTANCE_MIN_BOUND;
    }

    /**
     * Set a new Race instance.
     *
     * @param race the new Race.
     * @throws InterruptedException if the communication channel is busy and cannot be open.
     */
    public void setRace(Race race) throws InterruptedException {
        this.race = race;
        try {
            this.repository.setRaceDistance(race.getDistance());
            this.repository.setRaceNumber(race.getIdentification());
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new InterruptedException();
        }
    }

    /**
     * The identification of a race.
     */
    private static int identification = 0;

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
     * Condition variable for the Entities to order the race to start.
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
    private GeneralInformationRepositoryInterface repository;

    /**
     * The created instance of this class
     */
    private static RacingTrack instance;
}


