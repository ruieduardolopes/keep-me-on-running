package server;

import lib.communication.ServerCom;

public class ServerLauncher {
    public static void main(String[] args) {
        if (args.length != 1) {
            // TODO: handle this case
        }
        switch (args[0]) {
            case "betting-centre" :
                server = new BettingCentreProxy();
                port = 0; // TODO : identify the port
                break;
            case "control-centre" :
                server = new ControlCentreProxy();
                port = 0; // TODO : identify the port
                break;
            case "general-repo" :
                server = new GeneralInformationRepositoryProxy();
                port = 0; // TODO : identify the port
                break;
            case "paddock" :
                server = new PaddockProxy();
                port = 0; // TODO : identify the port
                break;
            case "racing-track" :
                server = new RacingTrackProxy();
                port = 0; // TODO : identify the port
                break;
            case "stable" :
                server = new StableProxy();
                port = 0; // TODO : identify the port
                break;
            default :
                // TODO : handle this case
                break;
        }
        serverConnectionRequest = new ServerCom(port);
        serverConnectionRequest.start();
        while (true) {
            serverConnectionInstance = serverConnectionRequest.accept();
            serviceProviderAgent = new ServiceProviderAgent(serverConnectionInstance, server);
            serviceProviderAgent.start();
        }
    }

    private static Server server = null;
    private static int port;
    private static ServerCom serverConnectionRequest;
    private static ServerCom serverConnectionInstance;
    private static ServiceProviderAgent serviceProviderAgent;
}
