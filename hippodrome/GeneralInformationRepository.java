package hippodrome;

import configurations.SimulationConfigurations;
import entities.BrokerState;
import entities.HorseJockeyState;
import entities.SpectatorState;
import hippodrome.rollfilm.*;
import genclass.GenericIO;
import genclass.TextFile;
import lib.logging.Color;
import lib.logging.Logger;


import java.time.Instant;

import static configurations.SimulationConfigurations.*;

/**
 * Class which represents an archive of all hippodrome's actions
 * and memorabilia. This item allows the entities playing at an
 * hippodrome to collect information about the various statuses
 * and snapshots all the instants while something is happening.
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @since 0.1
 * @version 2.0
 */
public class GeneralInformationRepository implements GeneralInformationRepositoryInterface {
    /**
     * Creates a General Repository of Information which saves a
     * snapshot of all instants of time throughout a horse run
     * game.
     *
     * @param numberOfHorses number of pairs Horse/Jockey to run on track.
     * @param numberOfSpectators number of Spectators to attend on track.
     * @param onlyLogonFile flag for deactivate the debug on {@code stdout}.
     */
    private GeneralInformationRepository(int numberOfHorses, int numberOfSpectators, boolean onlyLogonFile) {
        this.onlyLogOnFile = onlyLogonFile;
        this.numberOfHorses = numberOfHorses;
        this.numberOfSpectators = numberOfSpectators;
        this.spectators = new Spectator[this.numberOfSpectators];
        this.temporarySpectators = new Spectator[this.numberOfSpectators];
        for (int i = 0; i != spectators.length; i++) {
            spectators[i] = new Spectator();
            temporarySpectators[i] = new Spectator();
        }
        this.horseJockeys = new HorseJockey[this.numberOfHorses];
        for (int i = 0; i != horseJockeys.length; i++) {
            horseJockeys[i] = new HorseJockey();
        }
        giveFileAName();
        try {
            printClassicHeaders();
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
     * Get a singleton instance of a General Repository of Information.
     *
     * @return an instance of the General Repository of Information.
     */
    public static GeneralInformationRepository getInstance() {
        if (instance == null) {
            instance = new GeneralInformationRepository(NUMBER_OF_PAIRS_HORSE_JOCKEY, NUMBER_OF_SPECTATORS, ONLY_LOG_ON_FILE);
        }
        return instance;
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
        String title = Color.ANSI_BLUE_BOLD + "               AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem" + Color.ANSI_RESET;
        if (!onlyLogOnFile) {
            GenericIO.writelnString(title);
        }
        file.writelnString(cleanString(title));

        /* print the column headers */
        String header1 = Color.ANSI_GREEN + " MAN/BRK" + Color.ANSI_RESET + "            " + Color.ANSI_CYAN + "SPECTATOR/BETTER" + Color.ANSI_RESET + "                                 " + Color.ANSI_YELLOW + "HORSE/JOCKEY PAIR at Race RN" + Color.ANSI_RESET + "\n";
        header1 += Color.ANSI_GREEN + "   Stat  " + Color.ANSI_RESET;
        for (int i = 0; i != numberOfSpectators; i++) {
            header1 += Color.ANSI_CYAN;
            header1 += "St" + i + "   Am" + i + " ";
            header1 += Color.ANSI_RESET;
        }
        header1 += "   RN   ";
        for (int i = 0; i != numberOfHorses; i++) {
            header1 += Color.ANSI_YELLOW;
            header1 += "St" + i + "  Len" + i + "       ";
            header1 += Color.ANSI_RESET;
        }
        String header2 = Color.ANSI_BLUE_BOLD + "                                               Race RN Status\n" + Color.ANSI_RESET;
        header2 += " RN Dist  ";
        for (int i = 0; i != numberOfSpectators; i++) {
            header2 += Color.ANSI_PURPLE;
            header2 += "BS" + i + "  BA" + i + " ";
            header2 += Color.ANSI_RESET;
        }
        header2 += "     ";
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
     * Print the headers on a file as the teacher has asked.
     *
     * @throws InaccessibleFileException if the file does not grant any permission for the current user to be written on.
     * The same behavior could occur if the directory where this file resides cannot be written by the current user.
     */
    private synchronized void printClassicHeaders() throws InaccessibleFileException {
        boolean actionSucceeded = file.openForWriting(null, filename);
        if (!actionSucceeded) {
            throw new InaccessibleFileException("The requested file \"" + filename + "\" is currently unaccessible or this user does not have permissions to write on this directory.");
        }
        String title = "         AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem";
        if (!onlyLogOnFile) {
            GenericIO.writelnString(title);
        }
        file.writelnString(cleanString(title));
        String header1 = "MAN/BRK           SPECTATOR/BETTER              HORSE/JOCKEY PAIR at Race RN\n";
        header1 += "  Stat  St0  Am0 St1  Am1 St2  Am2 St3  Am3 RN St0 Len0 St1 Len1 St2 Len2 St3 Len3\n";
        header1 += "                                        Race RN Status\n";
        header1 += " RN Dist BS0  BA0 BS1  BA1 BS2  BA2 BS3  BA3  Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 St3\n";
        if (!onlyLogOnFile) {
            GenericIO.writelnString(header1);
        }
        file.writelnString(cleanString(header1));
        file.close();
    }

    /**
     * Adds a new snapshot of the race to log it.
     *
     * @param nullable a distinguishable item of the private method {@code newSnapshot}.
     */
    public synchronized void newSnapshot(boolean nullable) {
        newSnapshot();
    }

    /**
     * Adds a new snapshot of the race to log it.
     */
    private synchronized void newSnapshot() {
        if (brokerStatus.equals("OTE")) {
            for (int i = 0; i != horseJockeys.length; i++) {
                if ((horseJockeys[i].getAbility() == 0 || horseJockeys[i].getStatus() == null) && wereWaitingTheHorses) {
                    return;
                }
            }
        }
        boolean actionSucceeded = file.openForAppending(null, filename);
        if (!actionSucceeded) {
            throw new InaccessibleFileException("The requested file \"" + filename + "\" is currently unaccessible or this user does not have permissions to write on this directory.");
        }
        String line = "";
        line += printClassicEntitiesLine();
        line += "\n";
        line += printClassicRaceLine();
        if (!(line).equals(lastSnapshotLine)) {
            if (!onlyLogOnFile) {
                GenericIO.writelnString(line);
            }
            file.writelnString(cleanString(line));
        }
        lastSnapshotLine = line;
        file.close();
    }

    /**
     * Cleans a colorful ASCII'd {@code String}, in order to print strings without color characters.
     *
     * @param colorfulString the ASCII'd {@code String}.
     * @return a cleaned {@code String}.
     */
    private String cleanString(String colorfulString) {
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
     *
     * @return an entities line of the logger.
     */
    private String printEntitiesLine() {
        String line = " ";
        line += Color.ANSI_GREEN;
        line += String.format("%6s", brokerStatus);                             /* Stat */
        line += Color.ANSI_RESET;
        line += "  ";
        line += Color.ANSI_CYAN;
        for (Spectator spectator : spectators) {
            line += String.format("%5s", spectator.getStatus());                /* St# */
            line += " ";
            if (spectator.getAmountOfMoney() == 0) {
                line += "---";
            } else {
                line += String.format("%3d", spectator.getAmountOfMoney());         /* Am# */
            }
            line += " ";
        }
        line += Color.ANSI_RESET;
        line += String.format("   %2d", raceNumber);                            /* RN */
        line += "  ";
        line += Color.ANSI_YELLOW;
        for (HorseJockey horseJockey : horseJockeys) {
            line += String.format("%5s", horseJockey.getStatus());              /* St# */
            line += " ";
            if (horseJockey.getAbility() == 0) {
                line += "----";
            } else {
                line += String.format("%4d", horseJockey.getAbility());             /* Len# */
            }
            line += "      ";
        }
        line += Color.ANSI_RESET;
        return line;
    }

    /**
     * Makes a snapshot of the entities at a given time. This method prints the messages as the teacher asked.
     *
     * @return an entities line of the logger, specified as the teacher asked for.
     */
    private String printClassicEntitiesLine() {
        String line = "  ";
        switch (brokerStatus) {
            case "OTE" :
                if (horseJockeys[0] == null || horseJockeys[1] == null || horseJockeys[2] == null || horseJockeys[3] == null) {
                    line += "OpTE";
                } else {
                    line += "ANR";
                }
                break;
            case "ANR" : line += "AnNR"; break;
            case "WFB" : line += "WaFB"; break;
            case "STR" : line += "SuTR"; break;
            case "SA"  : line += "SetA"; break;
            case "PHATB": line += "PHAB"; break;
            default : line += "----"; break;
        }
        line += "  ";
        for (Spectator spectator : spectators) {
            switch (spectator.getStatus()) {
                case "WFRTS" : line += "WRS"; break;
                case "ATH"   : line += "ATH"; break;
                case "PB"    : line += "PAB"; break;
                case "WR"    : line += "WAR"; break;
                case "CTG"   : line += "CTG"; break;
                case "C"     : line += "CEL"; break;
                default      : line += "---"; break;
            }
            line += " ";
            if (spectator.getAmountOfMoney() == 0) {
                line += "----";
            } else {
                line += String.format("%4d", spectator.getAmountOfMoney());         /* Am# */
            }
            line += " ";
        }
        line += " ";
        if (!brokerStatus.matches("OTE|PHATB")) {
            line += String.format("%1d", raceNumber);                               /* RN */
        } else {
            line += "-";
        }
        line += " ";
        for (HorseJockey horseJockey : horseJockeys) {
            switch (horseJockey.getStatus()) {
                case "ATS"  : line += "ATS"; break;
                case "ATP"  : line += "ATP"; break;
                case "ATSL" : line += "ASL"; break;
                case "R"    : line += "RUN"; break;
                case "ATFL" : line += "AFL"; break;
                default     : line += "---"; break;
            }
            line += "  ";
            if (horseJockey.getAbility() == 0) {
                if (alreadyPrinted) {
                    alreadyPrinted = false;
                    line += "--";
                } else {
                    line += SimulationConfigurations.ABILITY_MAX_BOUND;
                }
            } else {
                line += String.format("%2d", horseJockey.getAbility());             /* Len# */
            }
            line += "  ";
        }
        return line;
    }

    /**
     * Makes a snapshot of the race at a given time.
     *
     * @return a race line of the logger.
     */
    private String printRaceLine() {
        String line = " ";
        if (brokerStatus.equals("OTE")) {
            line += "--";
        } else {
            line += String.format("%2d", raceNumber);                                    /* RN */
        }
        line += " ";
        if (brokerStatus.equals("OTE")) {
            line += "----";
        } else {
            line += String.format("%4d", currentRaceDistance);                           /* Dist */
        }
        line += " ";
        line += Color.ANSI_PURPLE;
        for (int i = 0; i != spectators.length; i++) {
            if (spectators[i].getBetSelection() == -1) {
                if (spectators[i].getStatus().matches("CTG|WR")) {
                    line += String.format("%4d", temporarySpectators[i].getBetSelection() + 1);
                } else {
                    line += "----";
                }
            } else {
                line += String.format("%4d", spectators[i].getBetSelection() + 1);             /* BS# */
            }
            line += " ";
            if (spectators[i].getBetAmount() == 0) {
                if (spectators[i].getStatus().matches("CTG|WR")) {
                    line += String.format("%4d", temporarySpectators[i].getBetAmount());
                } else {
                    line += "----";
                }
            } else {
                line += String.format("%4d", spectators[i].getBetAmount());                  /* BA# */
            }
            line += " ";
        }
        line += Color.ANSI_RESET;
        line += Color.ANSI_RED;
        line += "      ";
        for (HorseJockey horseJockey : horseJockeys) {
            if (horseJockey.getProbabilityToWin() == 0) {
                line += "---";
            } else {
                line += String.format("%3d", horseJockey.getProbabilityToWin());          /* Od# */
            }
            line += " ";
            if (!(horseJockey.getStatus().matches("R|ATSL|ATFL") || horseJockey.getFinalStandPosition() != 0)) {
                line += "--";
            } else {
                line += String.format("%2d", horseJockey.getNumberOfIncrementsDone());    /* N# */
            }
            line += " ";
            if (!(horseJockey.getStatus().matches("R|ATSL|ATFL") || horseJockey.getFinalStandPosition() != 0)) {
                line += "---";
            } else {
                line += String.format("%3d", horseJockey.getPositionOnTrack());           /* Ps# */
            }
            line += " ";
            if (!horseJockey.getStatus().matches("R|ATS|ATSL") || horseJockey.getFinalStandPosition() == 0) {
                line += "----";
            } else {
                line += String.format("%4d", horseJockey.getFinalStandPosition());        /* SD# */
            }
            line += " ";
        }
        line += Color.ANSI_RESET;
        return line;
    }

    /**
     * Makes a snapshot of the race at a given time. This method prints the messages as the teacher asked.
     *
     * @return a race line of the logger, specified as the teacher asked for.
     */
    private String printClassicRaceLine() {
        String line = "  ";
        if (brokerStatus.matches("OTE|PHATB")) {
            line += "-";
        } else {
            line += String.format("%1d", raceNumber);                                    /* RN */
        }
        line += " ";
        if (brokerStatus.matches("OTE|PHATB")) {
            line += "--";
        } else {
            line += String.format("%2d", currentRaceDistance);                           /* Dist */
        }
        line += "  ";
        for (int i = 0; i != spectators.length; i++) {
            line += " ";
            if (spectators[i].getBetSelection() == -1) {
                if (spectators[i].getStatus().matches("CTG|WR")) {
                    line += String.format("%1d", temporarySpectators[i].getBetSelection() + 1);
                } else {
                    line += "-";
                }
            } else {
                line += String.format("%1d", spectators[i].getBetSelection() + 1);             /* BS# */
            }
            line += "  ";
            if (spectators[i].getBetAmount() == 0) {
                if (spectators[i].getStatus().matches("CTG|WR")) {
                    line += String.format("%4d", temporarySpectators[i].getBetAmount());
                } else {
                    line += "----";
                }
            } else {
                line += String.format("%4d", spectators[i].getBetAmount());                  /* BA# */
            }
            line += " ";
        }
        for (HorseJockey horseJockey : horseJockeys) {
            if (horseJockey.getProbabilityToWin() == 0) {
                line += "----";
            } else {
                line += String.format("%4d", horseJockey.getProbabilityToWin());          /* Od# */
            }
            line += " ";
            if (!(horseJockey.getStatus().matches("R|ATSL|ATFL") || horseJockey.getFinalStandPosition() != 0)) {
                line += "--";
            } else {
                line += String.format("%2d", horseJockey.getNumberOfIncrementsDone());    /* N# */
            }
            line += "  ";
            if (!(horseJockey.getStatus().matches("R|ATSL|ATFL") || horseJockey.getFinalStandPosition() != 0)) {
                line += "--";
            } else {
                line += String.format("%2d", horseJockey.getPositionOnTrack());           /* Ps# */
            }
            line += "  ";
            if (!horseJockey.getStatus().matches("R|ATS|ATSL") || horseJockey.getFinalStandPosition() == 0) {
                line += "-";
            } else {
                line += String.format("%1d", horseJockey.getFinalStandPosition());        /* SD# */
            }
            line += " ";
        }
        return line;
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
     * Sets a new status for the {@link entities.Broker}, given by a {@link BrokerState}. This method also engraves a new
     * snapshot.
     *
     * @param status the current state represented by a {@link BrokerState} enumeration value.
     */
    public synchronized void setBrokerStatus(BrokerState status) {
        brokerStatus = "";
        for (String word : status.name().split("_")) {
            brokerStatus += word.charAt(0);
        }
        newSnapshot();
    }

    /**
     * Sets a new status for the {@code spectatorId} {@link Spectator}, given by a {@link SpectatorState}. This method also engraves a new
     * snapshot.
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
        newSnapshot();
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
            temporarySpectators[spectatorId].setBetSelection(selection);
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
            temporarySpectators[spectatorId].setBetAmount(bet);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new UnknownSpectatorException(spectatorId);
        }
    }

    /**
     * Sets a new status for the {@code horseJockeyId} {@link HorseJockey}, given by a {@link HorseJockeyState}. This method also engraves a new
     * snapshot.
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
        newSnapshot();
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
     * Gets the number of increments (iterations) for a pair Horse/Jockey identified with {@code horseJockeyId}.
     *
     * @param horseJockeyId the identification of the pair Horse/Jockey.
     *
     * @return the number of increments (iterations) for a pair Horse/Jockey.
     *
     * @throws UnknownHorseJockeyException if a {@link HorseJockey} is non-existent and is indexed over our
     * {@code horseJockeys} array.
     */
    public synchronized int getHorseJockeyNumberOfIncrementsDid(int horseJockeyId) throws UnknownHorseJockeyException {
        try {
            return horseJockeys[horseJockeyId].getNumberOfIncrementsDone();
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
     * Sets value to internal variable to inform that all the Spectators and Entities are already created and ready on start.
     *
     * @param value {@code true} if the simulation is now waiting for the pairs Horse/Jockey to be created; otherwise {@code false}.
     */
    public synchronized void setWereWaitingTheHorses(boolean value) {
        wereWaitingTheHorses = value;
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
     * Resets all the statuses of the entities related to a race. This method should be invoked when a race is over.
     */
    public synchronized void raceIsOver() {
        for (int i = 0; i != spectators.length; i++) {
            spectators[i].setBetSelection(-1);
            spectators[i].setBetAmount(0);
        }
        for (int i = 0; i != horseJockeys.length; i++) {
            horseJockeys[i].setFinalStandPosition(0);
            horseJockeys[i].setPositionOnTrack(0);
            horseJockeys[i].setAbility(0);
            horseJockeys[i].setNumberOfIncrementsDid(0);
            horseJockeys[i].setProbabilityToWin(0);
        }
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
     * Last temporary taken snapshot of the {@link entities.Spectator} entity.
     */
    private Spectator[] temporarySpectators;

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
     * Last reported status (snapshot) of the logger.
     */
    private String lastSnapshotLine = "";

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

    /**
     * Internal variable to inform that both the Spectators and the Entities are already created.
     */
    private boolean wereWaitingTheHorses = false;

    /**
     * The created instance of this class
     */
    private static GeneralInformationRepository instance;

    private boolean alreadyPrinted = true;
}
