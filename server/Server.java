package server;

import communications.Message;

public interface Server {
    Message processAndAnswerRequest(Message message) throws Exception ;
}
