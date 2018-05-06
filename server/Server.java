package server;

import communications.Message;

/**
 * Interface to be implemented in every hippodrome region.
 */
public interface Server {
    Message processAndAnswerRequest(Message message) throws Exception;
}
