package hippodrome;

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
        while (brokerDidNotOrderToStartTheRace) {
            try {
                System.out.println("Hello!");
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(9);
            }
        }
        System.out.println("khgfdsfghjkljhgfdsghjk");
        // wait for Broker to start the race with STR()
        // change the HJ's state to ATSL
        // done
    }

    /**
     * Let a pair Horse/Jockey {@code horse} make a move on the track, accordingly to its abilities to move.
     *
     * @param horseId the identification of the pair Horse/Jockey which wants to make a move.
     */
    public synchronized void makeAMove(int horseId) {
        ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.RUNNING);
        while (currentIndex != horseId) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(16);
            }
        }
        currentHorsesPositions[currentIndex]++;
        currentIndex += currentIndex + 1 % currentHorsesPositions.length;
        notifyAll();
        if (hasFirstHorseCrossedTheFinishLine) {
            winner = ((HorseJockey)Thread.currentThread()).getIdentification();
        }
        // wait while this thread ID is different than the currentIndex;
        // if this horse crossed the line, change the state to ATFL
        // increment our position on array currentPosition and increment the index on modulo.
        // i.e. currentIndex += (currentIndex + 1) % currentHorsesPosition.length;
        // notify the next horse
        // on the last make a move do this:
        // done
    }

    /**
     * Verification if the pair Horse/Jockey {@code horse} has crossed the finish line.
     *
     * @param horseJockeyId the pair Horse/Jockey which we want to verify if had crossed the finish line.
     *
     * @return {@code true} if the pair Horse/Jockey had crossed the finish line; otherwise it will return {@code false}.
     */
    public synchronized boolean hasFinishLineBeenCrossed(int horseJockeyId) {
        if (currentHorsesPositions[horseJockeyId] >= race.getDistance()) {
            ((HorseJockey)Thread.currentThread()).setHorseJockeyState(HorseJockeyState.AT_THE_FINNISH_LINE);
            return true;
        }
        return false;
        // if displacement of this horse is equal to race distance return true; otherwise false. check array
        // done
    }

    public synchronized void startTheRace() {
        brokerDidNotOrderToStartTheRace = false;
        notifyAll();
        // notify first horse to unlock the starting line (ATSL state)
        // done
    }

    public synchronized Race getRace() {
        return race;
    }

    public synchronized int getWinner() {
        return winner;
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

    private boolean hasFirstHorseCrossedTheFinishLine = false;

    private int winner = -1;

    private boolean brokerDidNotOrderToStartTheRace = true;

    private boolean someCondition = true;
}
