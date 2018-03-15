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

    private static int generateMoney() {
        return (int)(Math.random()*1000);
    }

    private static int generateAbility() {
        return (int)(Math.random()*5);
    }

    private static int numberOfHorses = 4;
    private static int numberOfRaces = 4;
    private static int numberOfRacingTracks = 4;
    private static int numberOfSpectators = 4;
    private static Broker broker = null;
    private static Spectator[] spectators = null;
    private static HorseJockey[] horseJockeys = null;
}
