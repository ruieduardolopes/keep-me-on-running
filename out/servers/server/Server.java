package server;

import communications.Message;

import java.rmi.Remote;

/**
 * Interface to be implemented in every hippodrome region.
 */
public interface Server extends Remote {
    Message processAndAnswerRequest(Message message) throws Exception;
}
