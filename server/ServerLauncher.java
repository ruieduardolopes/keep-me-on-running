package server;

import configurations.ServerConfigurations;
import hippodrome.*;
import lib.logging.Logger;
import registry.Register;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static configurations.RMIConfigurations.*;

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

        Registry registry = null;
        String nameEntryObject = null;

        try {
            registry = LocateRegistry.getRegistry(RMI_HOSTNAME, RMI_PORT_REGISTRY);
        } catch (RemoteException re) {
            Logger.printError("Error message 1");
            System.exit(1);
        }

        switch (args[0]) {
            case "betting-centre" :
                BettingCentre bettingCentre = BettingCentre.getInstance();
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
                ControlCentre controlCentre = ControlCentre.getInstance();
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
                GeneralInformationRepository repository = GeneralInformationRepository.getInstance();
                port = ServerConfigurations.GENERAL_INFORMATION_REPOSITORY_PORT;
                nameEntryObject = GLOBAL_REPOSITORY_OF_INFORMATION_NAME;
                try {
                    repositoryInterface = (GeneralInformationRepositoryInterface) UnicastRemoteObject.exportObject(repository, port);
                } catch (RemoteException re) {
                    Logger.printError("Error message 2");
                    System.exit(2);
                }
                break;
            case "paddock" :
                Paddock paddock = Paddock.getInstance();
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
                RacingTrack racingTrack = null;
                try {
                    racingTrack = RacingTrack.getInstance();
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
                Stable stable = Stable.getInstance();
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
            System.exit(4);
        }
        try {
            if (bettingCentreInterface != null) {
                register.bind(nameEntryObject, bettingCentreInterface);
            } else if (controlCentreInterface != null) {
                register.bind(nameEntryObject, controlCentreInterface);
            } else if (repositoryInterface != null) {
                register.bind(nameEntryObject, repositoryInterface);
            } else if (paddockInterface != null) {
                register.bind(nameEntryObject, paddockInterface);
            } else if (racingTrackInterface != null) {
                register.bind(nameEntryObject, racingTrackInterface);
            } else if (stableInterface != null) {
                register.bind(nameEntryObject, stableInterface);
            }
        } catch (RemoteException | AlreadyBoundException e) {
            Logger.printError("Oh damn... again..."); // TODO : another error handling
            System.exit(5);
        }
        Logger.printInformation("Registry server already running and waiting for new messages");
        while (!terminateExecution) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                System.exit(1000); // TODO: remove this
            }
            //terminateExecution = ServiceProviderAgent.getShutdownCounter(args[0]); // TODO : remove the service provider agent
        }
        serverConnectionRequest.close();
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
