package server;

import configurations.ServerConfigurations;
import hippodrome.*;
import interfaces.BettingCentreInterface;
import interfaces.ControlCentreInterface;
import interfaces.GeneralInformationRepositoryInterface;
import interfaces.PaddockInterface;
import interfaces.RacingTrackInterface;
import interfaces.StableInterface;
import lib.communication.ServerCom;
import lib.logging.Logger;
import registry.Register;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static configurations.RMIConfigurations.*;
import static configurations.ServerConfigurations.*;

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
        Logger.printInformation("A Security Manager has been installed");
        Registry registry = null;
        String nameEntryObject = null;
        try {
            registry = LocateRegistry.getRegistry(rmiHostname, rmiPort);
        } catch (RemoteException re) {
            Logger.printError("North Korea killed this thing..."); // TODO : redo this error handling...
            System.exit(1);
        }
        switch (args[0]) {
            case "betting-centre" :
                BettingCentre bettingCentre = BettingCentre.getInstance();
                port = ServerConfigurations.BETTING_CENTRE_PORT;
                nameEntryObject = BETTING_CENTRE_NAME;
                try {
                    server = (BettingCentreInterface) UnicastRemoteObject.exportObject(bettingCentre, port);
                } catch (RemoteException re) {
                    Logger.printError("Again... damn Kim Jong Un!"); // TODO : redo this error handling
                    System.exit(2);
                }
                break;
            case "control-centre" :
                ControlCentre controlCentre = ControlCentre.getInstance();
                port = ServerConfigurations.CONTROL_CENTRE_PORT;
                nameEntryObject = CONTROL_CENTRE_NAME;
                try {
                    server = (ControlCentreInterface) UnicastRemoteObject.exportObject(controlCentre, port);
                } catch (RemoteException re) {
                    Logger.printError("Again... damn Kim Jong Un!"); // TODO : redo this error handling
                    System.exit(2);
                }
                break;
            case "general-repo" :
                GeneralInformationRepository repository = GeneralInformationRepository.getInstance();
                port = ServerConfigurations.GENERAL_INFORMATION_REPOSITORY_PORT;
                nameEntryObject = GLOBAL_REPOSITORY_OF_INFORMATION_NAME;
                try {
                    server = (GeneralInformationRepositoryInterface) UnicastRemoteObject.exportObject(repository, port);
                } catch (RemoteException re) {
                    Logger.printError("Again... damn Kim Jong Un!"); // TODO : redo this error handling
                    System.exit(2);
                }
                break;
            case "paddock" :
                Paddock paddock = Paddock.getInstance();
                port = ServerConfigurations.PADDOCK_PORT;
                nameEntryObject = PADDOCK_NAME;
                try {
                    server = (PaddockInterface) UnicastRemoteObject.exportObject(paddock, port);
                } catch (RemoteException re) {
                    Logger.printError("Again... damn Kim Jong Un!"); // TODO : redo this error handling
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
                    server = (RacingTrackInterface) UnicastRemoteObject.exportObject(racingTrack, port);
                } catch (RemoteException re) {
                    Logger.printError("Again... damn Kim Jong Un!"); // TODO : redo this error handling
                    System.exit(2);
                }
                break;
            case "stable" :
                Stable stable = Stable.getInstance();
                port = ServerConfigurations.STABLE_PORT;
                nameEntryObject = STABLE_NAME;
                try {
                    server = (StableInterface) UnicastRemoteObject.exportObject(stable, port);
                } catch (RemoteException re) {
                    Logger.printError("Again... damn Kim Jong Un!"); // TODO : redo this error handling
                    System.exit(2);
                }
                break;
            default :
                printUsage();
                System.exit(2);
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
            register.bind(nameEntryObject, server);
        } catch (RemoteException | AlreadyBoundException e) {
            Logger.printError("Oh damn... again..."); // TODO : another error handling
            System.exit(5);
        }
        Logger.printInformation("Registry server already running and waiting for new messages");
        while (!terminateExecution) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {

            }
            terminateExecution = ServiceProviderAgent.getShutdownCounter(args[0]);
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


    /**
     * Boolean variable to signal when the execution must be terminated
     */
    private static boolean terminateExecution = false;

    /**
     * The instance of a general server.
     */
    private static Server server = null;

    /**
     * The identification of the service port.
     */
    private static int port;

    /**
     * The port of the RMI Registry Server
     */
    private static int rmiPort; // TODO : RMI port on server

    /**
     * The hostname of the RMI Registry Server
     */
    private static String rmiHostname; // TODO : RMI hostname on server

    /**
     * The request channel for communications with the clients.
     */
    private static ServerCom serverConnectionRequest;

    /**
     * The instance of the communication channel.
     */
    private static ServerCom serverConnectionInstance;

    /**
     * The instance of the service provider agent.
     */
    private static ServiceProviderAgent serviceProviderAgent;
}
