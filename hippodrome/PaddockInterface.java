package hippodrome;

public interface PaddockInterface {
    //TODO: replace Exception
    void proceedToPaddock(int raceNumber) throws InterruptedException;

    void goCheckHorses(boolean isTheLastSpectator) throws InterruptedException;

    void proceedToStartLine();

    boolean goCheckHorses();
}
