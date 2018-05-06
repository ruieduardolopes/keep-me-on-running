package server;

import configurations.ServerConfigurations;
import configurations.SimulationConfigurations;
import lib.communication.ServerCom;
import lib.logging.Logger;

import java.net.SocketTimeoutException;

public class ServerLauncher {
    public static void main(String[] args) {
        if (args.length != 1) {
            // TODO: handle this case
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
                    // TODO : handle this case
                    break;
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
                    // TODO : handle this exception
                }
                terminateExecution = ServiceProviderAgent.getShutdownCounter(args[0]);
            }
        } catch (InterruptedException ie) {
            Logger.printError("An exception has been thrown... catch it man! Below there is some information about it");
            ie.printStackTrace();
        }
    }

    private static boolean terminateExecution = false;
    private static Server server = null;
    private static int port;
    private static ServerCom serverConnectionRequest;
    private static ServerCom serverConnectionInstance;
    private static ServiceProviderAgent serviceProviderAgent;
}
