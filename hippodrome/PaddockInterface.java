package hippodrome;

public interface PaddockInterface {
    void proceedToPaddock(int raceNumber) throws InterruptedException;

    void goCheckHorses(boolean isTheLastSpectator) throws InterruptedException;

    void proceedToStartLine();

    boolean goCheckHorses();
}
