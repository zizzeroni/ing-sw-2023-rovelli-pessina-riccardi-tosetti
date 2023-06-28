package it.polimi.ingsw.utils;

/**
 * Enumeration used to identify all a series of personal goals , tiles, common goals and players related values.
 */
public class OptionsValues {
    public static final int RMI_PORT = 1099;
    public static final int MIN_NUMBER_OF_PLAYERS_TO_START_GAME = 0;
    public static final int MAX_NUMBER_OF_PLAYERS_TO_START_GAME = 4;
    public static final int NUMBER_OF_COMMON_GOAL = 2;
    public static final int NUMBER_OF_COMMON_GOAL_CARDS = 12;
    public static final int MIN_SELECTABLE_NUMBER_OF_PLAYERS = 2;
    public static final int MAX_SELECTABLE_NUMBER_OF_PLAYERS = 4;
    public static final int NUMBER_OF_PERSONAL_GOALS = 12;
    public static final int MILLISECOND_COUNTDOWN_VALUE = 60000;
    public static final int MIN_PLAYERS_TO_GO_ON_PAUSE = 1;
    public static final int WINNING_TILE_VALUE = 1;
    public static final int PERSONAL_GOAL_ZERO_TILE_SCORE = 0;
    public static final int PERSONAL_GOAL_ONE_TILE_SCORE = 1;
    public static final int PERSONAL_GOAL_TWO_TILE_SCORE = 2;
    public static final int PERSONAL_GOAL_THREE_TILE_SCORE = 4;
    public static final int PERSONAL_GOAL_FOUR_TILE_SCORE = 6;
    public static final int PERSONAL_GOAL_FIVE_TILE_SCORE = 9;
    public static final int PERSONAL_GOAL_SIX_TILE_SCORE = 12;
    public static final int MAX_NUMBER_PICKABLE_TILES = 3;
    public static final int BAG_SIZE = 132;
    public static final int NUMBER_OF_SCORE_TILE = 3;
    public static final int INITIAL_MISSED_PINGS = 0;
    public static final int MILLISECOND_TIMEOUT_PING = 6000;
    public static final int MILLISECOND_PING_TO_CLIENT_PERIOD = 2000;
    public static final int MILLISECOND_PING_TO_SERVER_PERIOD = 3000;
    public static final String SERVER_RMI_NAME = "server";
    public static final int SOCKET_PORT = 1234;
    public static final String GAMES_STORAGE_DEFAULT_PATH = "src/main/resources/storage/games.json";
    public static final String GAMES_STORAGE_BACKUP_DEFAULT_PATH = "src/main/resources/storage/games-bkp.json";
}
