package hippodrome;

import exceptions.InaccessibleFileException;
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
    public GeneralInformationRepository() {
        giveFileAName();
        try {
            printHeaders();
        } catch (InaccessibleFileException ife) {
            System.err.println("The General Repository of Information could not be run...");
            System.err.println("The exception says: " + ife.getMessage());
            System.err.println("Stack Trace:\n");
            ife.printStackTrace();
        } finally {
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
    public static void giveFileAName() {
        filename = "horse-run-" + Instant.now().getEpochSecond() + ".log";
    }

    /**
     * Method to print the headers of the log files.
     *
     * @throws InaccessibleFileException
     */
    public static void printHeaders() throws InaccessibleFileException {
        boolean actionSucceeded = file.openForWriting(null, filename);
        if (!actionSucceeded) {
            throw new exceptions.InaccessibleFileException("The requested file \"" + filename + "\" is currently unaccessible or this user does not have permissions to write on this directory.");
        }
    }

    private static String filename;
    private static TextFile file = new TextFile();
}
