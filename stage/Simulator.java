package stage;

import entities.*;
import hippodrome.*;
import hippodrome.actions.Race;

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
        /* initialize shared regions */
        GeneralInformationRepository repository = new GeneralInformationRepository(numberOfHorses, numberOfSpectators, onlyOnLogFile);
        BettingCentre bettingCentre = new BettingCentre(numberOfHorses, numberOfSpectators, repository);
        ControlCentre controlCentre = new ControlCentre(repository, numberOfHorses);
        Paddock paddock = new Paddock(numberOfSpectators, numberOfHorses, repository);
        Stable stable = new Stable();
        Race race = new Race(numberOfHorses, 0, Race.generateDistance());
        RacingTrack racingTrack = new RacingTrack(race, repository);

        repository.setRaceDistance(race.getDistance());
        repository.setRaceNumber(race.getIdentification());

        /* initialize the main entities */
        broker = new Broker(numberOfRaces, bettingCentre, controlCentre, racingTrack, stable, repository);
        spectators = new Spectator[numberOfSpectators];
        horseJockeys = new HorseJockey[numberOfHorses];

        /* initialize broker thread */
        for (int i = 0; i != numberOfSpectators; i++) {
            spectators[i] = new Spectator(i, generateMoney(), numberOfRaces, bettingCentre, controlCentre, paddock, repository);
            spectators[i].start();
            repository.newSnapshot();
        }

        /* initialize races */
        for (int i = 0; i < numberOfRaces; i++) {
            if (i >= 1) {
                race = new Race(numberOfHorses, i, Race.generateDistance());
                repository.setRaceDistance(race.getDistance());
                repository.setRaceNumber(race.getIdentification());
                racingTrack = new RacingTrack(race, repository);
                broker.setRacingTrack(racingTrack);
            }
            System.out.println("BEGIN START ----");
            for (int j = 0; j != numberOfHorses; j++) {
                horseJockeys[j] = new HorseJockey(j, generateAbility(), bettingCentre, controlCentre, paddock, racingTrack, stable, repository);
                horseJockeys[j].start();
                repository.newSnapshot();
            }
            System.out.println("END START ----");
            if (i == 0) {
                broker.start();
                repository.newSnapshot();
            }
            try {
                System.out.println("BEGIN JOIN ----");
                for (int j = 0; j != numberOfHorses; j++) {
                    horseJockeys[j].join();
                }
                System.out.println("END JOIN ----");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.err.println("An error occurred while terminating the threads.");
                System.err.println("The last program status was such as follows:");
                ie.printStackTrace();
                System.err.println("This program will now quit.");
                System.exit(2);
            }
        }
        try {
            broker.join();
            for (int i = 0; i != numberOfSpectators; i++) {
                spectators[i].join();
            }

        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.err.println("An error occurred while terminating the threads.");
            System.err.println("The last program status was such as follows:");
            ie.printStackTrace();
            System.err.println("This program will now quit.");
            System.exit(3);
        }
        repository.newSnapshot();
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
        return (int)(Math.random()*10)+1;
    }

    /**
     * Number of horses competing with each other.
     */
    private static int numberOfHorses = 4;

    /**
     * Number of races happening at the same event.
     */
    private static int numberOfRaces = 5;

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

    private static boolean onlyOnLogFile = false;
}
