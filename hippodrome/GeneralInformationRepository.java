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
 * @version 1.0
 */
public class GeneralInformationRepository {
    /**
     * Creates a General Repository of Information which saves a
     * snapshot of all instants of time throughout a horse run
     * game.
     *
     * @param numberOfHorses number of pairs Horse/Jockey to run on track.
     * @param numberOfSpectators number of Spectators to attend on track.
     */
    public GeneralInformationRepository(int numberOfHorses, int numberOfSpectators, boolean onlyLogonFile) {
        this.onlyLogOnFile = onlyLogonFile;
        this.numberOfHorses = numberOfHorses;
        this.numberOfSpectators = numberOfSpectators;
        this.spectators = new Spectator[this.numberOfSpectators];
        for (int i = 0; i != spectators.length; i++) {
            spectators[i] = new Spectator();
        }
        this.horseJockeys = new HorseJockey[this.numberOfHorses];
        for (int i = 0; i != horseJockeys.length; i++) {
            horseJockeys[i] = new HorseJockey();
        }
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
        String title = Color.ANSI_BLUE_BOLD + "AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem" + Color.ANSI_RESET;
        if (!onlyLogOnFile) {
            GenericIO.writelnString(title);
        }
        file.writelnString(cleanString(title));

        /* print the column headers */
        String header1 = Color.ANSI_GREEN + "MAN/BRK" + Color.ANSI_RESET + "            " + Color.ANSI_CYAN + "SPECTATOR/BETTER" + Color.ANSI_RESET + "               " + Color.ANSI_YELLOW + "HORSE/JOCKEY PAIR at Race RN" + Color.ANSI_RESET + "\n";
        header1 += Color.ANSI_GREEN + "  Stat  " + Color.ANSI_RESET;
        for (int i = 0; i != numberOfSpectators; i++) {
            header1 += Color.ANSI_CYAN;
            header1 += "St" + i + "  Am" + i + " ";
            header1 += Color.ANSI_RESET;
        }
        header1 += "RN ";
        for (int i = 0; i != numberOfHorses; i++) {
            header1 += Color.ANSI_YELLOW;
            header1 += "St" + i + "  Len" + i + " ";
            header1 += Color.ANSI_RESET;
        }
        String header2 = Color.ANSI_BLUE_BOLD + "                                        Race RN Status\n" + Color.ANSI_RESET;
        header2 += " RN Dist  ";
        for (int i = 0; i != numberOfSpectators; i++) {
            header2 += Color.ANSI_PURPLE;
            header2 += "BS" + i + "  BA" + i + " ";
            header2 += Color.ANSI_RESET;
        }
        header2 += "  ";
        for (int i = 0; i != numberOfHorses; i++) {
            header2 += Color.ANSI_RED;
            header2 += "Od" + i + " N" + i + " Ps" + i + "  SD" + i + " ";
            header2 += Color.ANSI_RESET;
        }
        if (!onlyLogOnFile) {
            GenericIO.writelnString(header1);
        }
        file.writelnString(cleanString(header1));
        if (!onlyLogOnFile) {
            GenericIO.writelnString(header2);
        }
        file.writelnString(cleanString(header2));
        file.close();
    }

    /**
     * Adds a new snapshot of the race to log it.
     */
    public synchronized void newSnapshot() {
        boolean actionSucceeded = file.openForAppending(null, filename);
        if (!actionSucceeded) {
            throw new InaccessibleFileException("The requested file \"" + filename + "\" is currently unaccessible or this user does not have permissions to write on this directory.");
        }
        printEntitiesLine();
        printRaceLine();
        file.close();
    }

    /**
     * Cleans a colorful ASCII'd {@code String}, in order to print strings without color characters.
     *
     * @param colorfulString the ASCII'd {@code String}.
     * @return a cleaned {@code String}.
     */
    private synchronized String cleanString(String colorfulString) {
        return colorfulString.replace(Color.ANSI_RESET, "").replace(Color.ANSI_BLACK, "").
                              replace(Color.ANSI_RED, "").replace(Color.ANSI_GREEN, "").
                              replace(Color.ANSI_YELLOW, "").replace(Color.ANSI_BLUE, "").
                              replace(Color.ANSI_PURPLE, "").replace(Color.ANSI_CYAN, "").
                              replace(Color.ANSI_WHITE, "").replace(Color.ANSI_BLACK_BOLD, "").
                              replace(Color.ANSI_RED_BOLD, "").replace(Color.ANSI_GREEN_BOLD , "").
                              replace(Color.ANSI_YELLOW_BOLD, "").replace(Color.ANSI_BLUE_BOLD, "").
                              replace(Color.ANSI_PURPLE_BOLD, "").replace(Color.ANSI_CYAN_BOLD, "");
    }

    /**
     * Makes a snapshot of the entities at a given time.
     */
    private synchronized void printEntitiesLine() {
        String line = " ";
        line += Color.ANSI_GREEN;
        line += String.format("%6s", brokerStatus);                             /* Stat */
        line += Color.ANSI_RESET;
        line += "  ";
        line += Color.ANSI_CYAN;
        for (Spectator spectator : spectators) {
            line += String.format("%5s", spectator.getStatus());                /* St# */
            line += " ";
            line += String.format("%3d", spectator.getAmountOfMoney());         /* Am# */
            line += " ";
        }
        line += Color.ANSI_RESET;
        line += String.format("%2d", raceNumber);                               /* RN */
        line += "  ";
        line += Color.ANSI_YELLOW;
        for (HorseJockey horseJockey : horseJockeys) {
            line += String.format("%5s", horseJockey.getStatus());              /* St# */
            line += " ";
            line += String.format("%4d", horseJockey.getAbility());             /* Len# */
            line += " ";
        }
        line += Color.ANSI_RESET;
        if (!onlyLogOnFile) {
            GenericIO.writelnString(line);
        }
        file.writelnString(cleanString(line));
    }

    /**
     * Makes a snapshot of the race at a given time.
     */
    private synchronized void printRaceLine() {
        String line = " ";
        line += String.format("%2d", raceNumber);                                    /* RN */
        line += " ";
        line += String.format("%4d", currentRaceDistance);                           /* Dist */
        line += " ";
        line += Color.ANSI_PURPLE;
        for (Spectator spectator : spectators) {
            line += String.format("%4d", spectator.getBetSelection()+1);             /* BS# */
            line += " ";
            line += String.format("%3d", spectator.getBetAmount());                  /* BA# */
            line += " ";
        }
        line += Color.ANSI_RESET;
        line += Color.ANSI_RED;
        line += "   ";
        for (HorseJockey horseJockey : horseJockeys) {
            line += String.format("%3d", horseJockey.getProbabilityToWin());/* Od# */
            line += " ";
            line += String.format("%2d", horseJockey.getNumberOfIncrementsDone());    /* N# */
            line += " ";
            line += String.format("%3d", horseJockey.getPositionOnTrack());          /* Ps# */
            line += " ";
            line += String.format("%4d", horseJockey.getFinalStandPosition());       /* SD# */
            line += " ";
        }
        line += Color.ANSI_RESET;
        if (!onlyLogOnFile) {
            GenericIO.writelnString(line);
        }
        file.writelnString(cleanString(line));
    }

    /**
     * Sets a new race number as the last race number.
     *
     * @param number the new race number identification.
     */
    public synchronized void setRaceNumber(int number) {
        raceNumber = number;
    }

    /**
     * Sets a new race distance as the last race distance.
     *
     * @param distance the new race distance.
     */
    public synchronized void setRaceDistance(int distance) {
        currentRaceDistance = distance;
    }

    /**
     * Sets a new status for the {@link entities.Broker}, given by a {@link BrokerState}.
     *
     * @param status the current state represented by a {@link BrokerState} enumeration value.
     */
    public synchronized void setBrokerStatus(BrokerState status) {
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
    public synchronized void setSpectatorStatus(int spectatorId, SpectatorState status) throws UnknownSpectatorException {
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
    public synchronized void setSpectatorAmountOfMoney(int spectatorId, int amount) throws UnknownSpectatorException {
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
    public synchronized void setSpectatorBetSelection(int spectatorId, int selection) throws UnknownSpectatorException {
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
    public synchronized void setSpectatorBetAmount(int spectatorId, int bet) throws UnknownSpectatorException {
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
    public synchronized void setHorseJockeyStatus(int horseJockeyId, HorseJockeyState status) throws UnknownHorseJockeyException {
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
    public synchronized void setHorseJockeyAbility(int horseJockeyId, int ability) throws UnknownHorseJockeyException {
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
    public synchronized void setHorseJockeyProbabilityToWin(int horseJockeyId, int probability) throws UnknownHorseJockeyException {
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
    public synchronized void setHorseJockeyNumberOfIncrementsDid(int horseJockeyId, int iterations) throws UnknownHorseJockeyException {
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
    public synchronized void setHorseJockeyPositionOnTrack(int horseJockeyId, int position) throws UnknownHorseJockeyException {
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
    public synchronized void setHorseJockeyFinalStandPosition(int horseJockeyId, int position) throws UnknownHorseJockeyException {
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
    public synchronized int getRaceNumber() {
        return raceNumber;
    }

    /**
     * Gets the value of the race distance.
     *
     * @return the distance of the last snapshot's race.
     */
    public synchronized int getCurrentRaceDistance() {
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

    /**
     * Number of pairs Horse/Jockey to attend the events.
     */
    private int numberOfHorses;

    /**
     * Number of Spectators to attend the events.
     */
    private int numberOfSpectators;

    /**
     * Flag to control if user wants to have colored information about the system's status on the {@code stdout} and
     * simple one on file (behavior activated if {@code false}); otherwise, with {@code true}, only on file, without
     * colors.
     */
    private boolean onlyLogOnFile;
}
