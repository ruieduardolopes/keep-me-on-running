package server;

import configurations.ServerConfigurations;
import configurations.SimulationConfigurations;
import lib.communication.ServerCom;
import lib.logging.Logger;

import java.net.SocketTimeoutException;

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
        try {
            switch (args[0]) {
                case "betting-centre" :
                    server = new BettingCentreProxy();
                    port = ServerConfigurations.BETTING_CENTRE_PORT;
                    break;
                case "control-centre" :
                    server = new ControlCentreProxy();
                    port = ServerConfigurations.CONTROL_CENTRE_PORT;
                    break;
                case "general-repo" :
                    server = new GeneralInformationRepositoryProxy();
                    port = ServerConfigurations.GENERAL_INFORMATION_REPOSITORY_PORT;
                    break;
                case "paddock" :
                    server = new PaddockProxy();
                    port = ServerConfigurations.PADDOCK_PORT;
                    break;
                case "racing-track" :
                    server = new RacingTrackProxy();
                    port = ServerConfigurations.RACING_TRACK_PORT;
                    break;
                case "stable" :
                    server = new StableProxy();
                    port = ServerConfigurations.STABLE_PORT;
                    break;
                default :
                    printUsage();
                    System.exit(2);
            }
            Logger.printNotification("Preparing to run %s server on port %d", args[0], port);
            serverConnectionRequest = new ServerCom(port);
            serverConnectionRequest.start();
            Logger.printInformation("Server already running and waiting for new messages");
            while (!terminateExecution) {
                try {
                    serverConnectionInstance = serverConnectionRequest.accept();
                    Logger.printNotification("Preparing to attend to request");
                    serviceProviderAgent = new ServiceProviderAgent(serverConnectionInstance, server);
                    Logger.printInformation("An agent was already made available to attend the situation");
                    serviceProviderAgent.start();
                } catch (SocketTimeoutException ste) {
                    System.exit(0);
                }
                terminateExecution = ServiceProviderAgent.getShutdownCounter(args[0]);
            }
        } catch (InterruptedException ie) {
            Logger.printError("An exception has been thrown... catch it man! Below there is some information about it");
            ie.printStackTrace();
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
