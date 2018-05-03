package server;

import communications.Message;
import communications.MessageType;
import hippodrome.GeneralInformationRepository;

public class GeneralInformationRepositoryProxy implements Server {
    public GeneralInformationRepositoryProxy() {
        repository = GeneralInformationRepository.getInstance();
    }

    @Override
    public Message processAndAnswerRequest(Message message) throws Exception {
        Message reply = null;
        switch (message.getType()) {
            case GENERAL_INFORMATION_REPOSITORY_GET_CURRENT_RACE_DISTANCE:
                int distance = repository.getCurrentRaceDistance();
                reply = new Message(MessageType.REPLY_GENERAL_INFORMATION_REPOSITORY_GET_CURRENT_RACE_DISTANCE, distance);
                break;
            case GENERAL_INFORMATION_REPOSITORY_GET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID:
                int numberOfIncrements = repository.getHorseJockeyNumberOfIncrementsDid(message.getHorseID());
                reply = new Message(MessageType.REPLY_GENERAL_INFORMATION_REPOSITORY_GET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID, numberOfIncrements);
                break;
            case GENERAL_INFORMATION_REPOSITORY_GET_RACE_NUMBER:
                int raceNumber = repository.getRaceNumber();
                reply = new Message(MessageType.REPLY_GENERAL_INFORMATION_REPOSITORY_GET_RACE_NUMBER, raceNumber);
                break;
            case GENERAL_INFORMATION_REPOSITORY_NEW_SNAPSHOT:
                repository.newSnapshot(false);
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_RACE_IS_OVER:
                repository.raceIsOver();
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_BROKER_STATUS:
                repository.setBrokerStatus(message.getBrokerState());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_ABILITY:
                repository.setHorseJockeyAbility(message.getHorseID(), message.getAbility());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_FINAL_STAND_POSITION:
                repository.setHorseJockeyFinalStandPosition(message.getHorseID(), message.getHorsePosition());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_NUMBER_OF_INCREMENTS_DID:
                repository.setHorseJockeyNumberOfIncrementsDid(message.getHorseID(), message.getHorseIteration());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_POSITION_ON_TRACK:
                repository.setHorseJockeyPositionOnTrack(message.getHorseID(), message.getHorsePosition());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_PROBABILITY_TO_WIN:
                repository.setHorseJockeyProbabilityToWin(message.getHorseID(), message.getHorseProbability());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_HORSE_JOCKEY_STATUS:
                repository.setHorseJockeyStatus(message.getHorseID(), message.getHorseJockeyState());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_RACE_DISTANCE:
                repository.setRaceDistance(message.getRaceDistance());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_RACE_NUMBER:
                repository.setRaceNumber(message.getRaceNumber());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_AMOUNT_OF_MONEY:
                repository.setSpectatorAmountOfMoney(message.getSpectatorID(), message.getSpectatorAmountOfMoney());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_AMOUNT:
                repository.setSpectatorBetAmount(message.getSpectatorID(), message.getBet());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_BET_SELECTION:
                repository.setSpectatorBetSelection(message.getSpectatorID(), message.getSpectatorBetSelection());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_SPECTATOR_STATUS:
                repository.setSpectatorStatus(message.getSpectatorID(), message.getSpectatorState());
                reply = new Message(MessageType.OK);
                break;
            case GENERAL_INFORMATION_REPOSITORY_SET_WERE_WAITING_THE_HORSES:
                repository.setWereWaitingTheHorses(message.getValue());
                reply = new Message(MessageType.OK);
                break;
            default:
                // TODO : handle this case
                System.out.print("AHEHF"+message.getType());
                break;
        }
        return reply;
    }

    private final GeneralInformationRepository repository;
}
