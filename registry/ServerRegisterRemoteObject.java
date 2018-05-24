package registry;

import configurations.RMIConfigurations;
import lib.logging.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRegisterRemoteObject {
    public static void main(String[] args) {
        // TODO : create validation of program input
        String hostname = "rmi-registry";
        int port = RMIConfigurations.RMI_PORT;
        Logger.printNotification("Trying to run RMI Registry Server on %s with port %s", hostname, port);

        // first phase : create and install the Java's security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Logger.printInformation("A Security Manager has been installed");

        // second phase : instantiation of a remote registration object and generation of a stub for it
        RegisterRemoteObject registryEngine = new RegisterRemoteObject(hostname, port);
        Register registryEngineStub = null;
        int listeningPort = RMIConfigurations.RMI_PORT;
        try {
            registryEngineStub = (Register) UnicastRemoteObject.exportObject(registryEngine, listeningPort);
        } catch (RemoteException re) {
            Logger.printError("An exception has been thrown on Stub creation: %s", re.getMessage());
            System.exit(1);
        }
        Logger.printInformation("A Stub has been successfully created");

        // third phase : register stub with the local registry service
        String nameEntry = "RegisterHandler";
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(hostname, port);
        } catch (RemoteException re) {
            Logger.printError("An exception has been thrown on getting registry location: %s", re.getMessage());
            System.exit(2);
        }
        Logger.printInformation("The registry has been successfully located");
        try {
            registry.rebind(nameEntry, registryEngineStub);
        } catch (RemoteException re) {
            Logger.printError("An exception has been thrown on registry registration: %s", re.getMessage());
            re.printStackTrace();
            System.exit(3);
        }
        Logger.printInformation("This registry remote object has been successfully registered");
    }
}
