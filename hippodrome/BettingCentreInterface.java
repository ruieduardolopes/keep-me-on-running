package hippodrome;

import hippodrome.rollfilm.UnknownHorseJockeyException;

public interface BettingCentreInterface {

    void acceptTheBets() throws InterruptedException;

    void honourTheBets() throws InterruptedException;

    int placeABet(int spectator, int bet, int horse) throws InterruptedException;

    int goCollectTheGains() throws InterruptedException;

    boolean haveIWon(int spectatorId) throws InterruptedException;

    boolean areThereAnyWinners(int winner);

    int getNumberOfHorses();

    void setAbility(int horse, int ability) throws UnknownHorseJockeyException;
}
