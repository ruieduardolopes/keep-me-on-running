package server;

import configurations.ServerConfiguration;
import lib.communication.ServerCom;
import lib.logging.Logger;

public class ServerLauncher {
    public static void main(String[] args) {
        if (args.length != 1) {
            // TODO: handle this case
        }
        switch (args[0]) {
            case "betting-centre" :
                server = new BettingCentreProxy();
                port = ServerConfiguration.BETTING_CENTRE_PORT;
                break;
            case "control-centre" :
                server = new ControlCentreProxy();
                port = ServerConfiguration.CONTROL_CENTRE_PORT;
                break;
            case "general-repo" :
                server = new GeneralInformationRepositoryProxy();
                port = ServerConfiguration.GENERAL_INFORMATION_REPOSITORY_PORT;
                break;
            case "paddock" :
                server = new PaddockProxy();
                port = ServerConfiguration.PADDOCK_PORT;
                break;
            case "racing-track" :
                server = new RacingTrackProxy();
                port = ServerConfiguration.RACING_TRACK_PORT;
                break;
            case "stable" :
                server = new StableProxy();
                port = ServerConfiguration.STABLE_PORT;
                break;
            default :
                // TODO : handle this case
                break;
        }
        Logger.printNotification("Preparing to run %s server on port %d", args[0], port);
        serverConnectionRequest = new ServerCom(port);
        serverConnectionRequest.start();
        Logger.printInformation("Server already running and waiting for new messages");
        while (true) {
            serverConnectionInstance = serverConnectionRequest.accept();
            Logger.printNotification("Preparing to attend to request");
            serviceProviderAgent = new ServiceProviderAgent(serverConnectionInstance, server);
            serviceProviderAgent.start();
            Logger.printInformation("An agent was already made available to attend the situation");
        }
    }

    private static Server server = null;
    private static int port;
    private static ServerCom serverConnectionRequest;
    private static ServerCom serverConnectionInstance;
    private static ServiceProviderAgent serviceProviderAgent;
}
