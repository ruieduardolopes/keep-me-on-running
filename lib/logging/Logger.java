package lib.logging;

/**
 * This class serves as a colorful Printing Logger of Strings.
 *
 * @author Rui Lopes
 * @version 2.0
 * @since 2.0
 */
public class Logger {

    /** Prints a Green Information String */
    public static void printInformation(String message, Object ... args) {
        System.out.printf(Color.ANSI_GREEN + "[INFOR] " + Color.ANSI_RESET + message + "." + "\n", args);
    }

    /** Prints a Cyan Notification String */
    public static void printNotification(String message, Object ... args) {
        System.out.printf(Color.ANSI_CYAN + "[NOTIF] " + Color.ANSI_RESET + message + "..." + "\n", args);
    }

    /** Prints a Red Error String */
    public static void printError(String message, Object ... args) {
        System.out.printf(Color.ANSI_RED + "[ERROR] " + Color.ANSI_RESET + message + "." + "\n", args);
    }

    /** Prints a Purple Debug String */
    public static void printDebug(String message, Object ... args) {
        System.out.printf(Color.ANSI_PURPLE + "[DEBUG] " + Color.ANSI_RESET + message + "\n", args);
    }

    /** Prints a Yellow Warning String */
    public static void printWarning(String message, Object ... args) {
        System.out.printf(Color.ANSI_YELLOW + "[WARNG] " + Color.ANSI_RESET + message + "\n", args);
    }
}
