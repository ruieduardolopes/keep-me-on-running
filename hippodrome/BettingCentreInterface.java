package hippodrome;

import hippodrome.rollfilm.UnknownHorseJockeyException;

public interface BettingCentreInterface {

    void acceptTheBets() throws InterruptedException;

    void honourTheBets() throws InterruptedException;

    int placeABet(int spectator, int bet, int horse) throws InterruptedException;

    int goCollectTheGains() throws InterruptedException;

    boolean haveIWon(int spectatorId) throws InterruptedException;

    boolean areThereAnyWinners(int winner) throws InterruptedException;

    int getNumberOfHorses() throws InterruptedException;

    void setAbility(int horse, int ability) throws InterruptedException, RuntimeException;
}
