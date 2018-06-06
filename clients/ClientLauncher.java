package clients;

import configurations.ClientConfigurations;
import configurations.RMIConfigurations;
import entities.Broker;
import entities.HorseJockey;
import entities.Spectator;
import hippodrome.*;
import lib.logging.Logger;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static configurations.SimulationConfigurations.*;

/**
 * Main function which must be used to run the various entities classified on {@link entities}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class ClientLauncher {
    /**
     * The main function for this execution.
     *
     * @param args the entity to run.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            System.exit(1);
        }
        try {
            registry = LocateRegistry.getRegistry(RMIConfigurations.RMI_HOSTNAME, RMIConfigurations.RMI_PORT_REGISTRY);
        } catch (RemoteException re) {
            Logger.printError("OMG, this happened!"); // TODO : refactor this error handling
        }
        try {
            repositoryInterface = (GeneralInformationRepositoryInterface) registry.lookup(RMIConfigurations.GLOBAL_REPOSITORY_OF_INFORMATION_NAME);
            bettingCentreInterface = (BettingCentreInterface) registry.lookup(RMIConfigurations.BETTING_CENTRE_NAME);
            controlCentreInterface = (ControlCentreInterface) registry.lookup(RMIConfigurations.CONTROL_CENTRE_NAME);
            stableInterface = (StableInterface) registry.lookup(RMIConfigurations.STABLE_NAME);
            racingTrackInterface = (RacingTrackInterface) registry.lookup(RMIConfigurations.RACING_TRACK_NAME);
            paddockInterface = (PaddockInterface) registry.lookup(RMIConfigurations.PADDOCK_NAME);
        } catch (RemoteException | NotBoundException e) {
            Logger.printError("Something went terribly wrong!"); // TODO : handle this error
        }
        try {
            switch (args[0]) {
                case "broker" :
                    Broker broker = new Broker(NUMBER_OF_RACES, bettingCentreInterface, controlCentreInterface, racingTrackInterface, stableInterface, repositoryInterface);
                    broker.start();
                    try {
                        broker.join();
                    } catch (InterruptedException ie) {
                        Logger.printError("An exception has been thrown... catch it man! Below there is some information about it");
                        ie.printStackTrace();
                        System.exit(2);
                    }
                    break;
                case "spectators" :
                    Spectator[] spectators = new Spectator[NUMBER_OF_SPECTATORS];
                    for (int i = 0; i != spectators.length; i++) {
                        spectators[i] = new Spectator(i, generateMoney(), NUMBER_OF_RACES, bettingCentreInterface, controlCentreInterface, paddockInterface, repositoryInterface);
                        spectators[i].start();
                    }
                    try {
                        for (int i = 0; i != spectators.length; i++) {
                            spectators[i].join();
                        }
                    } catch (InterruptedException ie) {
                        Logger.printError("An exception has been thrown... catch it man! Below there is some information about it");
                        ie.printStackTrace();
                        System.exit(3);
                    }
                    break;
                case "horses" :
                    for (int race = 0; race != NUMBER_OF_RACES; race++) {
                        HorseJockey[] horseJockeys = new HorseJockey[NUMBER_OF_PAIRS_HORSE_JOCKEY];
                        for (int i = 0; i != horseJockeys.length; i++) {
                            horseJockeys[i] = new HorseJockey(i, generateAbility(), bettingCentreInterface, controlCentreInterface, paddockInterface, racingTrackInterface, stableInterface, repositoryInterface);
                            horseJockeys[i].start();
                        }
                        try {
                            for (int i = 0; i != horseJockeys.length; i++) {
                                horseJockeys[i].join();
                                Logger.printInformation("The pair Horse/Jockey number %d deceased.",i);
                            }
                        } catch (InterruptedException ie) {
                            Logger.printError("An exception has been thrown... catch it man! Below there is some information about it");
                            ie.printStackTrace();
                            System.exit(4);
                        }
                    }
                    break;
                default :
                    printUsage();
                    System.exit(5);
            }
        } catch (Exception e) {
            Logger.printError("An exception has been thrown... catch it man! Below there is some information about it");
            e.printStackTrace();
        }
    }

    /**
     * Prints the help message for this method.
     */
    private static void printUsage() {
        System.err.println("Invalid arguments.");
        System.err.println("Usage: java -classpath \"project-folder:genclass-jar-folder\" ClientLauncher <entity>");
        System.err.println("Entities:");
        System.err.println("  - broker          (Broker)\n" +
                "  - spectator       (Spectators)\n" +
                "  - horses          (Pairs Horse/Jockey)");
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
     * @return a value between 2 and 5 which must be considered as the step
     * of the horse on track, while running.
     */
    private static int generateAbility() {
        return (int)(Math.random()*(ABILITY_MAX_BOUND - ABILITY_MIN_BOUND)) + ABILITY_MIN_BOUND;
    }

    private static Registry registry = null;

    /**
     * An interface for the remote method invocations over the Betting Centre hippodrome region.
     */
    private static BettingCentreInterface bettingCentreInterface = null;

    /**
     * An interface for the remote method invocations over the Control Centre hippodrome region.
     */
    private static ControlCentreInterface controlCentreInterface = null;

    /**
     * An interface for the remote method invocations over the General Repository of Information hippodrome region.
     */
    private static GeneralInformationRepositoryInterface repositoryInterface = null;

    /**
     * An interface for the remote method invocations over the Paddock hippodrome region.
     */
    private static PaddockInterface paddockInterface = null;

    /**
     * An interface for the remote method invocations over the Racing Track hippodrome region.
     */
    private static RacingTrackInterface racingTrackInterface = null;

    /**
     * An interface for the remote method invocations over the Stable hippodrome region.
     */
    private static StableInterface stableInterface = null;

}
