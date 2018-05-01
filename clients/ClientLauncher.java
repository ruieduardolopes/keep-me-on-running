package clients;

import clients.configurations.ClientConfiguration;
import clients.configurations.Entities;
import entities.Broker;
import entities.HorseJockey;
import entities.Spectator;

public class ClientLauncher {
    public static void main(String[] args) {
        if (args.length != 1) {
            // TODO: handle this case
        }
        switch (args[0]) {
            case "broker" :
                port = ClientConfiguration.BROKER_PORT;
                Broker broker = new Broker(Entities.NUMBER_OF_RACES);
                broker.start();
                try {
                    broker.join();
                } catch (InterruptedException ie) {
                    // TODO : catch this exception
                }
                break;
            case "spectators" :
                // TODO : make a set of spectators (just like in the Simulator)
                port = ClientConfiguration.SPECTATOR_PORT;
                Spectator[] spectators = new Spectator[Entities.NUMBER_OF_SPECTATORS];
                for (int i = 0; i != spectators.length; i++) {
                    spectators[i] = new Spectator(i, generateMoney(), Entities.NUMBER_OF_RACES);
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
                port = ClientConfiguration.HORSE_JOCKEY_PORT;
                for (int race = 0; race != Entities.NUMBER_OF_RACES; race++) {
                    HorseJockey[] horseJockeys = new HorseJockey[Entities.NUMBER_OF_PAIRS_HORSE_JOCKEY];
                    for (int i = 0; i != horseJockeys.length; i++) {
                        horseJockeys[i] = new HorseJockey(i, generateAbility());
                        horseJockeys[i].start();
                    }
                    try {
                        for (int i = 0; i != horseJockeys.length; i++) {
                            horseJockeys[i].join();
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
    }

    /**
     * Generate money between a value of 1 and 999.
     *
     * @return a money value between 1 and 999.
     */
    private static int generateMoney() {
        return (int)(Math.random() * (Entities.MONEY_MAX_BOUND - Entities.MONEY_MIN_BOUND)) + Entities.MONEY_MIN_BOUND;
    }

    /**
     * Generate a value for representation of pair Horse/Jockey's ability.
     *
     * @return a value between 0 and 5 which must be considered as the step
     * of the horse on track, while running.
     */
    private static int generateAbility() {
        return (int)(Math.random()*(Entities.ABILITY_MAX_BOUND - Entities.ABILITY_MIN_BOUND)) + Entities.ABILITY_MIN_BOUND;
    }

    private static int port;
}
