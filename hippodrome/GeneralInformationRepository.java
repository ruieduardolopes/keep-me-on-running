package hippodrome;

import exceptions.InaccessibleFileException;
import genclass.GenericIO;
import genclass.TextFile;
import hippodrome.registry.HorseJockey;
import hippodrome.registry.Spectator;

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
    public GeneralInformationRepository() {
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
    private static void giveFileAName() {
        filename = "horse-run-" + Instant.now().getEpochSecond() + ".log";
    }

    /**
     * Print the headers of the log files.
     *
     * @throws InaccessibleFileException if the file does not grant any permission for the current user to be written on.
     * The same behavior could occur if the directory where this file resides cannot be written by the current user.
     */
    private static void printHeaders() throws InaccessibleFileException {
        boolean actionSucceeded = file.openForWriting(null, filename);
        if (!actionSucceeded) {
            throw new exceptions.InaccessibleFileException("The requested file \"" + filename + "\" is currently unaccessible or this user does not have permissions to write on this directory.");
        }

        /* print the title */
        String title = "AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem";
        GenericIO.writelnString(title);
        file.writelnString(title);

        /* print the column headers */ // TODO - make this generic
        String header1 = "MAN/BRK           SPECTATOR/BETTER              HORSE/JOCKEY PAIR at Race RN\n" +
                         "  Stat  St0  Am0 St1  Am1 St2  Am2 St3  Am3 RN St0 Len0 St1 Len1 St2 Len2 St3 Len3";
        String header2 = "                                        Race RN Status\n" +
                         " RN Dist BS0  BA0 BS1  BA1 BS2  BA2 BS3  BA3  Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 St3";
        GenericIO.writelnString(header1);
        file.writelnString(header1);
        GenericIO.writelnString(header2);
        file.writelnString(header2);
    }

    /**
     * Makes a snapshot of the entities at a given time.
     */
    public static void printEntitiesLine() {

    }

    /**
     * Makes a snapshot of the race at a given time.
     */
    public static void printRaceLine() {

    }

    private static String brokerStatus;
    private static Spectator[] spectators;
    private static HorseJockey[] horseJockeys;
    private static int raceNumber;
    private static int currentRaceDistance;

    /**
     * Name of the file where the log is to be saved.
     */
    private static String filename;

    /**
     * File object {@code file} from the external class {@link TextFile} which
     * allows us to control a file as IO for the log.
     */
    private static TextFile file = new TextFile();
}
