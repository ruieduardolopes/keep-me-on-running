package lib.logging;

public class Logger {
    public static void printInformation(String message, Object ... args) {
        System.out.printf(Color.ANSI_GREEN + "[INFOR] " + Color.ANSI_RESET + message + "..." + "\n", args);
    }

    public static void printNotification(String message, Object ... args) {
        System.out.printf(Color.ANSI_CYAN + "[NOTIF] " + Color.ANSI_RESET + message + "." + "\n", args);
    }

    public static void printError(String message, Object ... args) {
        System.out.printf(Color.ANSI_RED + "[ERROR] " + Color.ANSI_RESET + message + "." + "\n", args);
    }

    public static void printDebug(String message, Object ... args) {
        System.out.printf(Color.ANSI_PURPLE + "[DEBUG] " + Color.ANSI_RESET + message + "\n", args);
    }
}
