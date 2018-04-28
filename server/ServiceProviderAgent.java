package server;

import communications.Message;
import lib.communication.ServerCom;

public class ServiceProviderAgent extends Thread {
    public ServiceProviderAgent(ServerCom connection, Server server) {
        this.connection = connection;
        this.server = server;
    }

    @Override
    public void run() {
        Message reply = null;
        Message message = (Message) connection.readObject();
        try {
            reply = server.processAndAnswerRequest(message);
        } catch (Exception e) {
            // TODO : Handle this exception
        }
        connection.writeObject(reply);
        connection.close();
    }

    private ServerCom connection;
    private Server server;
}
