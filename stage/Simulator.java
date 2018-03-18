package stage;

import entities.*;

/**
 * Creation of a Horse Race simulation placed on a Hippodrome.
 * This class has a {@link entities.Broker}, a set of {@link entities.Spectator}s
 * and a set of pairs Horse/Jockey ({@link entities.HorseJockey}s).
 */
public class Simulator {
    /**
     * Main function which contemplates the execution of the simulation.
     * This method will then run the entire scenario of a horse race at an hippodrome,
     * this being a coordination between several threads of the game's entities and
     * shared regions.
     *
     * @param args input arguments to the simulation execution.
     */
    public static void main(String[] args) {
        /* initialize a broker and the other arrays of entities */
        broker = new Broker();
        spectators = new Spectator[numberOfSpectators];
        horseJockeys = new HorseJockey[numberOfHorses];

        /* for each race run each thread */
        new Thread(broker).start();
        for (int race = 0; race < numberOfRaces; race++) {
            for (int i = 0; i != numberOfSpectators; i++) {
                spectators[i] = new Spectator(i, generateMoney());
                new Thread(spectators[i]).start();
            }
            for (int i = 0; i != numberOfHorses; race++) {
                horseJockeys[i] = new HorseJockey(i, generateAbility());
                new Thread(horseJockeys[i]).start();
            }
            // TODO - wait till the race is over...
            try {
                for (int i = 0; i != numberOfSpectators; i++) {
                    new Thread(spectators[i]).join();
                }
                spectators = null;
                for (int i = 0; i != numberOfHorses; i++) {
                    new Thread(horseJockeys[i]).join();
                }
                horseJockeys = null;
            } catch (InterruptedException ie) {
                ie.printStackTrace(); // TODO - handle the exception...
            }
        }
    }

    /**
     * Generate money between a value of 1 and 999.
     *
     * @return a money value between 1 and 999.
     */
    private static int generateMoney() {
        return (int)(Math.random()*1000) + 1;
    }

    /**
     * Generate a value for representation of pair Horse/Jockey's ability.
     *
     * @return a value between 0 and 5 which must be considered as the step
     * of the horse on track, while running.
     */
    private static int generateAbility() {
        return (int)(Math.random()*5);
    }

    /**
     * Number of horses competing with each other.
     */
    private static int numberOfHorses = 4;

    /**
     * Number of races happening at the same event.
     */
    private static int numberOfRaces = 4;

    /**
     * Number of spectators appearing on the hippodrome.
     */
    private static int numberOfSpectators = 4;

    /**
     * The sets' Broker instance.
     */
    private static Broker broker = null;

    /**
     * The sets' array of spectators' instance.
     */
    private static Spectator[] spectators = null;

    /**
     * The sets' array of pairs Horse/Jockey instance.
     */
    private static HorseJockey[] horseJockeys = null;
}
