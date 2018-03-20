package hippodrome;

import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.registry.*;
import genclass.GenericIO;
import genclass.TextFile;

import java.time.Instant;

/**
 * Class which represents an archive of all hippodrome's actions
 * and memorabilia. This item allows the entities playing at an
 * hippodrome to collect information about the various statuses
 * and snapshots all the instants while something is happening.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 0.1
 */
public class GeneralInformationRepository {
    /**
     * Creates a General Repository of Information which saves a
     * snapshot of all instants of time throughout a horse run
     * game.
     */
    public GeneralInformationRepository(int numberOfHorses, int numberOfSpectators) {
        this.numberOfHorses = numberOfHorses;
        this.numberOfSpectators = numberOfSpectators;
        this.spectators = new Spectator[this.numberOfSpectators];
        this.horseJockeys = new HorseJockey[this.numberOfHorses];
        giveFileAName();
        try {
            printHeaders();
        } catch (InaccessibleFileException ife) {
            System.err.println("The General Repository of Information could not be run...");
            System.err.println("The exception says: " + ife.getMessage());
            System.err.println("Stack Trace:\n");
            ife.printStackTrace();
            System.err.println("Quitting...");
            System.exit(1);
        }
    }

    /**
     * Gives a name to the logging file. This name is
     * constructed with the application name ({@code horse-run})
     * followed by the UNIX timestamp of when this method is executed
     * and the extension {@code .log}.
     */
    private void giveFileAName() {
        filename = "horse-run-" + Instant.now().getEpochSecond() + ".log";
    }

    /**
     * Print the headers of the log files.
     *
     * @throws InaccessibleFileException if the file does not grant any permission for the current user to be written on.
     * The same behavior could occur if the directory where this file resides cannot be written by the current user.
     */
    private synchronized void printHeaders() throws InaccessibleFileException {
        boolean actionSucceeded = file.openForWriting(null, filename);
        if (!actionSucceeded) {
            throw new InaccessibleFileException("The requested file \"" + filename + "\" is currently unaccessible or this user does not have permissions to write on this directory.");
        }

        /* print the title */
        String title = "AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem";
        GenericIO.writelnString(title);
        file.writelnString(title);

        /* print the column headers */
        String header1 = "MAN/BRK           SPECTATOR/BETTER              HORSE/JOCKEY PAIR at Race RN\n";
        header1 += "  Stat  ";
        for (int i = 0; i != numberOfSpectators; i++) {
            header1 += "St" + i + "  Am" + i + " ";
        }
        header1 += "RN ";
        for (int i = 0; i != numberOfHorses; i++) {
            header1 += "St" + i + "  Len" + i + " ";
        }
        String header2 = "                                        Race RN Status\n" +
                         " RN Dist BS0  BA0 BS1  BA1 BS2  BA2 BS3  BA3  Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 St3";
        header2 += " RN Dist ";
        for (int i = 0; i != numberOfSpectators; i++) {
            header2 += "BS" + i + "  BA" + i + " ";
        }
        header2 += " ";
        for (int i = 0; i != numberOfHorses; i++) {
            header2 += "Od" + i + " N" + i + " Ps" + i + " SD" + i + " ";
        }
        GenericIO.writelnString(header1);
        file.writelnString(header1);
        GenericIO.writelnString(header2);
        file.writelnString(header2);
        file.close();
    }

    /**
     * Adds a new snapshot of the race to log it.
     */
    public void newSnapshot() {
        boolean actionSucceeded = file.openForAppending(null, filename);
        if (!actionSucceeded) {
            throw new InaccessibleFileException("The requested file \"" + filename + "\" is currently unaccessible or this user does not have permissions to write on this directory.");
        }
        printEntitiesLine();
        printRaceLine();
        file.close();
    }

    /**
     * Makes a snapshot of the entities at a given time.
     */
    private void printEntitiesLine() {
        String line = " ";
        line += String.format("%6s", brokerStatus);                             /* Stat */
        line += " ";
        for (Spectator spectator : spectators) {
            line += String.format("%4s", spectator.getStatus());                /* St# */
            line += "-";
            line += String.format("%3d", spectator.getAmountOfMoney());         /* Am# */
            line += " ";
        }
        line += String.format("%2d", raceNumber);                               /* RN */
        line += " ";
        for (HorseJockey horseJockey : horseJockeys) {
            line += String.format("%4s", horseJockey.getStatus());              /* St# */
            line += "-";
            line += String.format("%4d", horseJockey.getAbility());             /* Len# */
            line += " ";
        }
        GenericIO.writelnString(line);
        file.writelnString(line);
    }

    /**
     * Makes a snapshot of the race at a given time.
     */
    private void printRaceLine() {
        String line = " ";
        line += String.format("%2d", raceNumber);                                   /* RN */
        line += " ";
        line += String.format("%4d", currentRaceDistance);                          /* Dist */
        line += " ";
        for (Spectator spectator : spectators) {
            line += String.format("%4d", spectator.getBetSelection());              /* BS# */
            line += "-";
            line += String.format("%3d", spectator.getBetAmount());                 /* BA# */
            line += " ";
        }
        line += " ";
        for (HorseJockey horseJockey : horseJockeys) {
            line += String.format("%4f", horseJockey.getProbabilityToWin());        /* Od# */
            line += "-";
            line += String.format("%2d", horseJockey.getNumberOfIncrementsDid());   /* N# */
            line += "-";
            line += String.format("%3d", horseJockey.getPositionOnTrack());         /* Ps# */
            line += "-";
            line += String.format("%3d", horseJockey.getFinalStandPosition());      /* SD# */
            line += " ";
        }
        GenericIO.writelnString(line);
        file.writelnString(line);
    }

    /**
     * Sets a new race number as the last race number.
     *
     * @param number the new race number identification.
     */
    public void setRaceNumber(int number) {
        raceNumber = number;
    }

    /**
     * Sets a new race distance as the last race distance.
     *
     * @param distance the new race distance.
     */
    public void setRaceDistance(int distance) {
        currentRaceDistance = distance;
    }

    /**
     * Sets a new status for the {@link entities.Broker}, given by a {@link BrokerState}.
     *
     * @param status the current state represented by a {@link BrokerState} enumeration value.
     */
    public void setBrokerStatus(BrokerState status) {
        brokerStatus = "";
        for (String word : status.name().split("_")) {
            brokerStatus += word.charAt(0);
        }
    }

    /**
     * Sets a new status for the {@code spectatorId} {@link Spectator}, given by a {@link SpectatorState}.
     *
     * @param spectatorId the identification of the {@link Spectator}.
     * @param status the current state represented by a {@link SpectatorState} enumeration value.
     *
     * @throws UnknownSpectatorException if a {@link Spectator} is non-existent and is indexed over our
     * {@code spectators} array.
     */
    public void setSpectatorStatus(int spectatorId, SpectatorState status) throws UnknownSpectatorException {
        try {
            spectators[spectatorId].setStatus(status);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownSpectatorException(spectatorId);
        }
    }

    /**
     * Sets a new amount of money for the {@code spectatorId} {@link Spectator}.
     *
     * @param spectatorId the identification of the {@link Spectator}.
     * @param amount the current amount of money represented by a {@code Integer}.
     *
     * @throws UnknownSpectatorException if a {@link Spectator} is non-existent and is indexed over our
     * {@code spectators} array.
     */
    public void setSpectatorAmountOfMoney(int spectatorId, int amount) throws UnknownSpectatorException {
        try {
            spectators[spectatorId].setAmountOfMoney(amount);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownSpectatorException(spectatorId);
        }
    }

    /**
     * Sets a new betting selection for the {@code spectatorId} {@link Spectator}.
     *
     * @param spectatorId the identification of the {@link Spectator}.
     * @param selection the current selection of a pair Horse/Jockey represented its identification number.
     *
     * @throws UnknownSpectatorException if a {@link Spectator} is non-existent and is indexed over our
     * {@code spectators} array.
     */
    public void setSpectatorBetSelection(int spectatorId, int selection) throws UnknownSpectatorException {
        try {
            spectators[spectatorId].setBetSelection(selection);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownSpectatorException(spectatorId);
        }
    }

    /**
     * Sets a new betting amount of the {@code spectatorId} {@link Spectator}.
     *
     * @param spectatorId the identification of the {@link Spectator}.
     * @param bet the current betting amount on a pair Horse/Jockey.
     *
     * @throws UnknownSpectatorException if a {@link Spectator} is non-existent and is indexed over our
     * {@code spectators} array.
     */
    public void setSpectatorBetAmount(int spectatorId, int bet) throws UnknownSpectatorException {
        try {
            spectators[spectatorId].setBetAmount(bet);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownSpectatorException(spectatorId);
        }
    }

    /**
     * Sets a new status for the {@code horseJockeyId} {@link HorseJockey}, given by a {@link HorseJockeyState}.
     *
     * @param horseJockeyId the identification of the pair {@link HorseJockey}.
     * @param status the current state represented by a {@link HorseJockeyState} enumeration value.
     *
     * @throws UnknownHorseJockeyException if a {@link HorseJockey} is non-existent and is indexed over our
     * {@code horseJockeys} array.
     */
    public void setHorseJockeyStatus(int horseJockeyId, HorseJockeyState status) throws UnknownHorseJockeyException {
        try {
            horseJockeys[horseJockeyId].setStatus(status);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownHorseJockeyException(horseJockeyId);
        }
    }

    /**
     * Sets a new ability for the {@code horseJockeyId} {@link HorseJockey}.
     *
     * @param horseJockeyId the identification of the pair {@link HorseJockey}.
     * @param ability the current ability.
     *
     * @throws UnknownHorseJockeyException if a {@link HorseJockey} is non-existent and is indexed over our
     * {@code horseJockeys} array.
     */
    public void setHorseJockeyAbility(int horseJockeyId, int ability) throws UnknownHorseJockeyException {
        try {
            horseJockeys[horseJockeyId].setAbility(ability);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownHorseJockeyException(horseJockeyId);
        }
    }

    /**
     * Sets a new probability to win for the {@code horseJockeyId} {@link HorseJockey}.
     *
     * @param horseJockeyId the identification of the pair {@link HorseJockey}.
     * @param probability the current probability to win.
     *
     * @throws UnknownHorseJockeyException if a {@link HorseJockey} is non-existent and is indexed over our
     * {@code horseJockeys} array.
     */
    public void setHorseJockeyProbabilityToWin(int horseJockeyId, double probability) throws UnknownHorseJockeyException {
        try {
            horseJockeys[horseJockeyId].setProbabilityToWin(probability);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownHorseJockeyException(horseJockeyId);
        }
    }

    /**
     * Sets a new number of increments (iterations) for the {@code horseJockeyId} {@link HorseJockey}.
     *
     * @param horseJockeyId the identification of the pair {@link HorseJockey}.
     * @param iterations the current number of increments.
     *
     * @throws UnknownHorseJockeyException if a {@link HorseJockey} is non-existent and is indexed over our
     * {@code horseJockeys} array.
     */
    public void setHorseJockeyNumberOfIncrementsDid(int horseJockeyId, int iterations) throws UnknownHorseJockeyException {
        try {
            horseJockeys[horseJockeyId].setNumberOfIncrementsDid(iterations);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownHorseJockeyException(horseJockeyId);
        }
    }

    /**
     * Sets a new position on track for the {@code horseJockeyId} {@link HorseJockey}.
     *
     * @param horseJockeyId the identification of the pair {@link HorseJockey}.
     * @param position the current pair Horse/Jockey position on track.
     *
     * @throws UnknownHorseJockeyException if a {@link HorseJockey} is non-existent and is indexed over our
     * {@code horseJockeys} array.
     */
    public void setHorseJockeyPositionOnTrack(int horseJockeyId, int position) throws UnknownHorseJockeyException {
        try {
            horseJockeys[horseJockeyId].setPositionOnTrack(position);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownHorseJockeyException(horseJockeyId);
        }
    }

    /**
     * Sets a new final stand position for the {@code horseJockeyId} {@link HorseJockey}, given by a {@link HorseJockeyState}.
     *
     * @param horseJockeyId the identification of the pair {@link HorseJockey}.
     * @param position the current final stand position.
     *
     * @throws UnknownHorseJockeyException if a {@link HorseJockey} is non-existent and is indexed over our
     * {@code horseJockeys} array.
     */
    public void setHorseJockeyFinalStandPosition(int horseJockeyId, int position) throws UnknownHorseJockeyException {
        try {
            horseJockeys[horseJockeyId].setFinalStandPosition(position);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownHorseJockeyException(horseJockeyId);
        }
    }

    /**
     * Gets the value of the race number.
     *
     * @return the identification of the last snapshot's race number.
     */
    public int getRaceNumber() {
        return raceNumber;
    }

    /**
     * Gets the value of the race distance.
     *
     * @return the distance of the last snapshot's race.
     */
    public int getCurrentRaceDistance() {
        return currentRaceDistance;
    }

    /**
     * Last taken snapshot of the {@link entities.Broker} entity.
     */
    private String brokerStatus;

    /**
     * Last taken snapshot of the {@link entities.Spectator} entity.
     */
    private Spectator[] spectators;

    /**
     * Last taken snapshot of the pairs Horse/Jockey ({@link entities.HorseJockey}) entities.
     */
    private HorseJockey[] horseJockeys;

    /**
     * Last taken snapshot of the race number.
     */
    private int raceNumber;

    /**
     * Last taken snapshot of the current race distance of track.
     */
    private int currentRaceDistance;

    /**
     * Name of the file where the log is to be saved.
     */
    private String filename;

    /**
     * File object {@code file} from the external class {@link TextFile} which
     * allows us to control a file as IO for the log.
     */
    private TextFile file = new TextFile();

    private int numberOfHorses;

    private int numberOfSpectators;
}
