package clients;

import hippodrome.BettingCentreInterface;
import hippodrome.rollfilm.UnknownHorseJockeyException;

public class BettingCentreStub implements BettingCentreInterface {
    @Override
    public void acceptTheBets() throws InterruptedException {

    }

    @Override
    public void honourTheBets() throws InterruptedException {

    }

    @Override
    public int placeABet(int spectator, int bet, int horse) throws InterruptedException {
        return 0;
    }

    @Override
    public int goCollectTheGains() throws InterruptedException {
        return 0;
    }

    @Override
    public boolean haveIWon(int spectatorId) {
        return false;
    }

    @Override
    public boolean areThereAnyWinners(int winner) {
        return false;
    }

    @Override
    public int getNumberOfHorses() {
        return 0;
    }

    @Override
    public void setAbility(int horse, int ability) throws UnknownHorseJockeyException {

    }
}
