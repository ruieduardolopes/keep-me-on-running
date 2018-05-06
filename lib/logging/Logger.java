package lib.logging;

/**
 * This class serves as a colorful Printing Logger of Strings.
 *
 * @author Rui Lopes
 * @version 2.0
 * @since 2.0
 */
public class Logger {

    /**
     * Prints an Information String
     * @param message the message to print.
     * @param args the formatter.
     */
    public static void printInformation(String message, Object ... args) {
        System.out.printf(Color.ANSI_GREEN + "[INFOR] " + Color.ANSI_RESET + message + "." + "\n", args);
    }

    /**
     * Prints a Notification String
     * @param message the message to print.
     * @param args the formatter.
     */
    public static void printNotification(String message, Object ... args) {
        System.out.printf(Color.ANSI_CYAN + "[NOTIF] " + Color.ANSI_RESET + message + "..." + "\n", args);
    }

    /**
     * Prints a Error String
     * @param message the message to print.
     * @param args the formatter.
     */
    public static void printError(String message, Object ... args) {
        System.out.printf(Color.ANSI_RED + "[ERROR] " + Color.ANSI_RESET + message + "." + "\n", args);
    }

    /**
     * Prints a Debug String
     * @param message the message to print.
     * @param args the formatter.
     */
    public static void printDebug(String message, Object ... args) {
        System.out.printf(Color.ANSI_PURPLE + "[DEBUG] " + Color.ANSI_RESET + message + "\n", args);
    }

    /**
     * Prints a Warning String
     * @param message the message to print.
     * @param args the formatter.
     */
    public static void printWarning(String message, Object ... args) {
        System.out.printf(Color.ANSI_YELLOW + "[WARNG] " + Color.ANSI_RESET + message + "\n", args);
    }
}
