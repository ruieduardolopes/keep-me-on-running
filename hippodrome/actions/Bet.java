package hippodrome.actions;

public class Bet {
    public Bet(int horseJockeyId, int amount) {
        this.horseJockeyId = horseJockeyId;
        this.amount = amount;
    }

    public int getHorseJockeyId() {
        return horseJockeyId;
    }

    public int getAmount() {
        return amount;
    }

    private int horseJockeyId;

    private int amount;
}
