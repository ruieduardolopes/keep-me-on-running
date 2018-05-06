package clients;

import entities.Broker;
import entities.HorseJockey;
import entities.Spectator;
import lib.logging.Logger;

import static configurations.SimulationConfigurations.*;

public class ClientLauncher {
    public static void main(String[] args) {
        if (args.length != 1) {
            // TODO: handle this case
        }
        try {
            switch (args[0]) {
                case "broker" :
                    Broker broker = new Broker(NUMBER_OF_RACES);
                    broker.start();
                    try {
                        broker.join();
                    } catch (InterruptedException ie) {
                        // TODO : catch this exception
                    }
                    break;
                case "spectators" :
                    Spectator[] spectators = new Spectator[NUMBER_OF_SPECTATORS];
                    for (int i = 0; i != spectators.length; i++) {
                        spectators[i] = new Spectator(i, generateMoney(), NUMBER_OF_RACES);
                        spectators[i].start();
                    }
                    try {
                        for (int i = 0; i != spectators.length; i++) {
                            spectators[i].join();
                        }
                    } catch (InterruptedException ie) {
                        // TODO : catch this exception
                    }
                    break;
                case "horses" :
                    for (int race = 0; race != NUMBER_OF_RACES; race++) {
                        HorseJockey[] horseJockeys = new HorseJockey[NUMBER_OF_PAIRS_HORSE_JOCKEY];
                        for (int i = 0; i != horseJockeys.length; i++) {
                            horseJockeys[i] = new HorseJockey(i, generateAbility());
                            horseJockeys[i].start();
                        }
                        try {
                            for (int i = 0; i != horseJockeys.length; i++) {
                                horseJockeys[i].join();
                                Logger.printInformation("The pair Horse/Jockey number %d deceased.",i);
                            }
                        } catch (InterruptedException ie) {
                            // TODO : catch this exception
                        }
                    }
                    break;
                default :
                    // TODO : handle this case
                    break;
            }
        } catch (Exception e) {
            Logger.printError("An exception has been thrown... catch it man! Below there is some information about it");
            e.printStackTrace();
        }
    }

    /**
     * Generate money between a value of 1 and 999.
     *
     * @return a money value between 1 and 999.
     */
    private static int generateMoney() {
        return (int)(Math.random() * (MONEY_MAX_BOUND - MONEY_MIN_BOUND)) + MONEY_MIN_BOUND;
    }

    /**
     * Generate a value for representation of pair Horse/Jockey's ability.
     *
     * @return a value between 0 and 5 which must be considered as the step
     * of the horse on track, while running.
     */
    private static int generateAbility() {
        return (int)(Math.random()*(ABILITY_MAX_BOUND - ABILITY_MIN_BOUND)) + ABILITY_MIN_BOUND;
    }
}
