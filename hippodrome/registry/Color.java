package hippodrome.registry;

/**
 * This class serves as basis to concatenate colors on java Strings.
 *
 * @author Rui Lopes
 */
public class Color {
    /** Color resetter, to put after a text in order to cancel the color application. */
    public static final String ANSI_RESET = "\u001B[0m";
    /** Color black */
    public static final String ANSI_BLACK = "\u001B[30m";
    /** Color red */
    public static final String ANSI_RED = "\u001B[31m";
    /** Color green */
    public static final String ANSI_GREEN = "\u001B[32m";
    /** Color yellow */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /** Color blue */
    public static final String ANSI_BLUE = "\u001B[34m";
    /** Color purple */
    public static final String ANSI_PURPLE = "\u001B[35m";
    /** Color cyan */
    public static final String ANSI_CYAN = "\u001B[36m";
    /** Color white */
    public static final String ANSI_WHITE = "\u001B[37m";
    /** Color black, applying bold formatting */
    public static final String ANSI_BLACK_BOLD = "\033[1;30m";
    /** Color red, applying bold formatting */
    public static final String ANSI_RED_BOLD = "\033[1;31m";
    /** Color green, applying bold formatting */
    public static final String ANSI_GREEN_BOLD = "\033[1;32m";
    /** Color yellow, applying bold formatting */
    public static final String ANSI_YELLOW_BOLD = "\033[1;33m";
    /** Color blue, applying bold formatting */
    public static final String ANSI_BLUE_BOLD = "\033[1;34m";
    /** Color purple, applying bold formatting */
    public static final String ANSI_PURPLE_BOLD = "\033[1;35m";
    /** Color cyan, applying bold formatting */
    public static final String ANSI_CYAN_BOLD = "\033[1;36m";
    /** Color white, applying bold formatting */
    public static final String ANSI_WHITE_BOLD = "\033[1;37m";
}
