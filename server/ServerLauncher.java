package server;

import configurations.ServerConfigurations;
import hippodrome.*;
import hippodrome.actions.Race;
import lib.logging.Logger;
import registry.Register;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static configurations.RMIConfigurations.*;
import static configurations.SimulationConfigurations.*;

/**
 * Main function which must be used to run the various entities classified on {@link entities}.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 2.0
 * @version 2.0
 */
public class ServerLauncher {
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

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = null;
        String nameEntryObject = null;

        try {
            registry = LocateRegistry.getRegistry(RMI_HOSTNAME, RMI_PORT_REGISTRY);
        } catch (RemoteException re) {
            Logger.printError("Error message 1");
            System.exit(1);
        }
        if (!args[0].equals("general-repo")) {
            try {
                repositoryInterface = (GeneralInformationRepositoryInterface) registry.lookup(GLOBAL_REPOSITORY_OF_INFORMATION_NAME);
            } catch (RemoteException | NotBoundException e) {
                Logger.printError("Error message 0");
                System.exit(11);
            }
        }

        switch (args[0]) {
            case "betting-centre" :
                bettingCentre = new BettingCentre(repositoryInterface, NUMBER_OF_PAIRS_HORSE_JOCKEY, NUMBER_OF_SPECTATORS);
                port = ServerConfigurations.BETTING_CENTRE_PORT;
                nameEntryObject = BETTING_CENTRE_NAME;
                try {
                    bettingCentreInterface = (BettingCentreInterface) UnicastRemoteObject.exportObject(bettingCentre, port);
                } catch (RemoteException re) {
                    Logger.printError("Error message 2");
                    System.exit(2);
                }
                break;
            case "control-centre" :
                controlCentre = new ControlCentre(repositoryInterface, NUMBER_OF_PAIRS_HORSE_JOCKEY);
                port = ServerConfigurations.CONTROL_CENTRE_PORT;
                nameEntryObject = CONTROL_CENTRE_NAME;
                try {
                    controlCentreInterface = (ControlCentreInterface) UnicastRemoteObject.exportObject(controlCentre, port);
                } catch (RemoteException re) {
                    Logger.printError("Error message 2");
                    System.exit(2);
                }
                break;
            case "general-repo" :
                repository = new GeneralInformationRepository(NUMBER_OF_PAIRS_HORSE_JOCKEY, NUMBER_OF_SPECTATORS, true);
                port = ServerConfigurations.GENERAL_INFORMATION_REPOSITORY_PORT;
                nameEntryObject = GLOBAL_REPOSITORY_OF_INFORMATION_NAME;
                try {
                    repositoryInterface = (GeneralInformationRepositoryInterface) UnicastRemoteObject.exportObject(repository, port);
                } catch (RemoteException re) {
                    Logger.printError("Error message 2");
                    re.printStackTrace();
                    System.exit(2);
                }
                break;
            case "paddock" :
                paddock = new Paddock(repositoryInterface, NUMBER_OF_SPECTATORS, NUMBER_OF_PAIRS_HORSE_JOCKEY);
                port = ServerConfigurations.PADDOCK_PORT;
                nameEntryObject = PADDOCK_NAME;
                try {
                    paddockInterface = (PaddockInterface) UnicastRemoteObject.exportObject(paddock, port);
                } catch (RemoteException re) {
                    Logger.printError("Error message 2");
                    System.exit(2);
                }
                break;
            case "racing-track" :
                racingTrack = null;
                try {
                    racingTrack = new RacingTrack(repositoryInterface, new Race(NUMBER_OF_TRACKS, 0, Race.generateDistance()));
                } catch (Exception e) {
                    Logger.printError("Something went wrong with the creation of the Racing Track");
                    System.exit(3);
                }
                port = ServerConfigurations.RACING_TRACK_PORT;
                nameEntryObject = RACING_TRACK_NAME;
                try {
                    racingTrackInterface = (RacingTrackInterface) UnicastRemoteObject.exportObject(racingTrack, port);
                } catch (RemoteException re) {
                    Logger.printError("Error Message 2");
                    System.exit(2);
                }
                break;
            case "stable" :
                stable = new Stable(repositoryInterface);
                port = ServerConfigurations.STABLE_PORT;
                nameEntryObject = STABLE_NAME;
                try {
                    stableInterface = (StableInterface) UnicastRemoteObject.exportObject(stable, port);
                } catch (RemoteException re) {
                    Logger.printError("Error message 2");
                    System.exit(2);
                }
                break;
            default :
                printUsage();
                System.exit(3);
        }

        Logger.printNotification("Preparing to run %s server on port %d", args[0], port);
        String nameEntryBase = RMI_REGISTER_NAME;
        Register register = null;
        try {
            register = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException | NotBoundException e) {
            Logger.printError("Oh damn... again..."); // TODO : another error handling
            e.printStackTrace();
            System.exit(4);
        }
        try {
            if (bettingCentreInterface != null) {
                register.bind(nameEntryObject, bettingCentreInterface);
            } else if (controlCentreInterface != null) {
                register.bind(nameEntryObject, controlCentreInterface);
            } else if (paddockInterface != null) {
                register.bind(nameEntryObject, paddockInterface);
            } else if (racingTrackInterface != null) {
                register.bind(nameEntryObject, racingTrackInterface);
            } else if (stableInterface != null) {
                register.bind(nameEntryObject, stableInterface);
            } else if (repositoryInterface != null) {
                register.bind(nameEntryObject, repositoryInterface);
            }
        } catch (RemoteException | AlreadyBoundException e) {
            Logger.printError("Oh damn... again..."); // TODO : another error handling
            e.printStackTrace();
            System.exit(5);
        }
        Logger.printInformation("Registry server already running and waiting for new messages");

        try {
            switch (nameEntryObject) {
                case BETTING_CENTRE_NAME:
                    while (bettingCentreInterface.getNumberOfEntitiesDeclaringExit() < NUMBER_OF_SPECTATORS*NUMBER_OF_RACES + NUMBER_OF_RACES) {
                        Thread.sleep(1000);
                    }
                    break;
                case CONTROL_CENTRE_NAME:
                    while (controlCentreInterface.getNumberOfEntitiesDeclaringExit() < NUMBER_OF_SPECTATORS + 1) {
                        Thread.sleep(1000);
                    }
                    break;
                case GLOBAL_REPOSITORY_OF_INFORMATION_NAME:
                    while (!repositoryInterface.isBrokerReadyToGiveTheMasterFart()) {
                        Thread.sleep(1000);
                    }
                    break;
                case PADDOCK_NAME:
                    while (paddockInterface.getNumberOfEntitiesDeclaringExit() < NUMBER_OF_SPECTATORS*NUMBER_OF_RACES) {
                        Thread.sleep(1000);
                    }
                    break;
                case RACING_TRACK_NAME:
                    while (racingTrackInterface.getNumberOfEntitiesDeclaringExit() < 1) {
                        Thread.sleep(1000);
                    }
                    break;
                case STABLE_NAME:
                    while (stableInterface.getNumberOfEntitiesDeclaringExit() < 1) {
                        Thread.sleep(1000);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.printError("What an error!");
            System.exit(17);
        }
        resetRegistry(register, nameEntryObject);
        Logger.printInformation("This machine is going to blow up!");
        System.exit(0);
    }

    private static void resetRegistry(Register register, String name) {
        try {
            register.unbind(name);
            switch (name) {
                case BETTING_CENTRE_NAME:
                    UnicastRemoteObject.unexportObject(bettingCentre, true);
                    break;
                case CONTROL_CENTRE_NAME:
                    UnicastRemoteObject.unexportObject(controlCentre, true);
                    break;
                case GLOBAL_REPOSITORY_OF_INFORMATION_NAME:
                    UnicastRemoteObject.unexportObject(repository, true);
                    break;
                case PADDOCK_NAME:
                    UnicastRemoteObject.unexportObject(paddock, true);
                    break;
                case RACING_TRACK_NAME:
                    UnicastRemoteObject.unexportObject(racingTrack, true);
                    break;
                case STABLE_NAME:
                    UnicastRemoteObject.unexportObject(stable, true);
                    break;
                default:
                    break;
            }
            Logger.printInformation("Everything's clear.");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.printError("Something's missing");
        }
    }

    /**
     * Prints the help message for this method.
     */
    private static void printUsage() {
        System.err.println("Invalid arguments.");
        System.err.println("Usage: java -classpath \"project-folder:genclass-jar-folder\" ServerLauncher <hippodrome-region>");
        System.err.println("Hippodrome Regions:");
        System.err.println("  - betting-centre      (Betting Centre)\n" +
                "  - control-centre      (Control Centre)\n" +
                "  - general-repo        (General Repository of Information)\n" +
                "  - paddock             (Paddock)\n" +
                "  - racing-track        (Racing Track)\n" +
                "  - stable              (Stable)\n");
    }

    private static BettingCentre bettingCentre;

    private static ControlCentre controlCentre;

    private static GeneralInformationRepository repository;

    private static Paddock paddock;

    private static RacingTrack racingTrack;

    private static Stable stable;

    private static BettingCentreInterface bettingCentreInterface = null;

    private static ControlCentreInterface controlCentreInterface = null;

    private static GeneralInformationRepositoryInterface repositoryInterface = null;

    private static PaddockInterface paddockInterface = null;

    private static RacingTrackInterface racingTrackInterface = null;

    private static StableInterface stableInterface = null;

    private static int port = 0;

    private static final String RMI_HOSTNAME = "localhost"; // TODO: switch this for a proper configuration

    /**
     * Boolean variable to signal when the execution must be terminated
     */
    private static boolean terminateExecution = false;
}
