package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class Game represents an object {@code Game} that permits to keep
 * tracking and updating the model through a series of methods and
 * a listener linked to the class itself, {@code GameListener}.
 * <p>
 * It permits to establish the number of players, the active {@code Player}
 * and a series of interactions that may occur with the {@code Board}.
 * It is also necessary to register, access and modify the current state of the game.
 *
 * @see Game
 * @see GameListener
 * @see java.net.http.WebSocket.Listener
 * @see Player
 * @see Board
 */
public class Game {
    private transient GameListener listener;
    private GameState gameState;
    private int numberOfPlayersToStartGame;
    private int activePlayerIndex;
    private List<Player> players;
    private List<Tile> bag;
    private Board board;
    private List<CommonGoal> commonGoals;

    /**
     * Registers the {@code GameListener}.
     *
     * @param listener is the {@code GameListener} being registered.
     *
     * @see   GameListener
     */
    public void registerListener(GameListener listener) {
        this.listener = listener;
    }

    /**
     * Removes the {@code GameListener}.
     *
     * Listener is the {@code GameListener} being registered
     *
     * @see   GameListener
     */
    public void removeListener() {
        this.listener = null;
    }

    /**
     * Class Builder. Creates the object {@code Game} with the initial unset values of lists for commonGoals, gameState, class listener, ...
     */
    public Game() {
        this.gameState = GameState.IN_CREATION;
        this.listener = null;
        this.players = new ArrayList<>();
        this.activePlayerIndex = 0;
        this.board = null;
        this.numberOfPlayersToStartGame = 0;
        this.bag = new ArrayList<>(132);
        this.commonGoals = new ArrayList<>(2);
        for (int i = 0; i < 132; i++) {
            this.bag.add(new Tile(TileColor.values()[i % 6]));
        }
        this.board = new Board();

        Collections.shuffle(this.bag);

    }

    /**
     * Class Builder. Creates the object {@code Game} with the initial values of lists for commonGoals, gameState, class listener, ...
     */
    public Game(int numberOfPlayersToStartGame, List<Player> players, List<PersonalGoal> personalGoals, JsonBoardPattern boardPattern) {
        this.gameState = GameState.IN_CREATION;
        this.listener = null;
        this.players = players;
        this.activePlayerIndex = 0;
        this.board = new Board(boardPattern);
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        this.bag = new ArrayList<>(132);
        this.commonGoals = new ArrayList<>();

        //initialize bag and shuffle items
        for (int i = 0; i < 132; i++) {
            this.bag.add(new Tile(TileColor.values()[i % 6]));
        }

        Collections.shuffle(personalGoals);

        //initialize players
        for (Player player : this.players) {
            player.setBookshelf(new Bookshelf());
            player.setGoalTiles(new ArrayList<>(3));
            player.setPersonalGoal(personalGoals.remove(0));
        }

        /*
        //initialize common goals
        CommonGoal newCommonGoal;
        while (this.commonGoals.size() != 2) {
            try {
                newCommonGoal = this.getRandomCommonGoalSubclassInstance();
                if (!this.commonGoals.contains(newCommonGoal)) {
                    this.commonGoals.add(newCommonGoal);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        */
        Collections.shuffle(this.bag);

        List<Tile> drawnTiles = this.bag.subList(0, this.board.numberOfTilesToRefill());
        this.board.addTiles(drawnTiles);
    }

    /**
     * Class Builder. Creates the object {@code Game} with the initial values of lists for commonGoals, gameState, class listener, ...
     */
    public Game(int numberOfPlayersToStartGame, int activePlayerIndex, List<Player> players, List<Tile> bag, Board board, List<CommonGoal> commonGoals) {
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
        this.listener = null;
    }

    /**
     * Class Builder. Creates the object {@code Game} with the initial values of lists for commonGoals, gameState, class listener, ...
     */
    public Game(GameListener listener, int numberOfPlayersToStartGame, int activePlayerIndex, List<Player> players, List<Tile> bag, Board board, List<CommonGoal> commonGoals) {
        this.listener = listener;
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
    }

    /**
     * Gets the current state of the {@code Game}.
     *
     * @return the game's state.
     *
     * @see Game
     */
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Sets the current state of the {@code Game}.
     *
     * @param gameState the state being set.
     *
     * @see Game
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        if (this.listener != null) {
            this.listener.gameStateChanged();
        } else {
            System.err.println("Game's listener is NULL!");
        }
    }

    /** Gets the number of {@code Player}s before the {@code Game} starts,
     * respecting the limitations about maximum and minimum number of players.
     *
     * @return {@code numberOfPlayersToStartGame} the number of players participating the {@code Game}.
     *
     * @see Game
     * @see Player
     */
    public int getNumberOfPlayersToStartGame() {
        return this.numberOfPlayersToStartGame;
    }

    /**
     * Sets the number of {@code Player}s before the {@code Game} starts,
     * respecting the limitations about maximum and minimum number of players.
     *
     * @param numberOfPlayersToStartGame the number of players participating the {@code Game}.
     *
     * @see Game
     * @see Player
     */
    public void setNumberOfPlayersToStartGame(int numberOfPlayersToStartGame) {
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        if (this.listener != null) {
            this.listener.numberOfPlayersModified();
        }
    }

    /**
     * Gets the number of the active {@code Player}.
     *
     * @return {@code activePlayerIndex}, the index of the current player.
     *
     * @see Player
     */
    public int getActivePlayerIndex() {
        return this.activePlayerIndex;
    }

    /**
     * Sets the index of the {@code Player} actually active.
     *
     * @param activePlayerIndex is the index of the current {@code Player}.
     *
     * @see Player
     */
    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
        this.saveGame();
        if (this.listener != null) {
            this.listener.activePlayerIndexModified();
        } else {
            System.err.println("Game's listener is NULL!");
        }
    }

    /**
     * Gets the list of the {@code Player}s for the {@code Game}.
     *
     * @return the list of {@code Game}'s participants.
     *
     * @see Game
     * @see Player
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * Sets the active {@code Player}s.
     *
     * @param players the players participating the {@code Game}.
     *
     * @see Player
     * @see Game
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Adds a {@code Player} to the {@code Game} using the relative {@code GameListener}.
     *
     * @param player the {@code Player} added to the list of the current active players.
     *
     * @see Game
     * @see GameListener
     * @see java.net.http.WebSocket.Listener
     * @see Player
     */
    public void addPlayer(Player player) {
        this.players.add(player);
        if (this.listener != null) {
            this.listener.addedPlayer();
        } else {
            System.err.println("Game's listener is NULL!");
        }
    }

    /**
     * A getter used to return the "bag" ({@code List<Tile>} of the Tiles
     * available to the active {@code Player} at the start of the {@code Game}, before the shuffling.
     *
     * @return the "bag" of tiles to be shuffled.
     *
     * @see Tile
     * @see Game
     * @see Player
     */
    public List<Tile> getBag() {
        return this.bag;
    }

    public void setBag(List<Tile> bag) {
        this.bag = bag;
        if (this.listener != null) {
            this.listener.bagModified();
        }
    }

    /**
     * A getter used to return the {@code Board} state.
     *
     * @return the Board with the changes up to now.
     *
     * @see Board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Sets the changes to the {@code Board}.
     *
     * @param board is the modified {@code Board}.
     *
     * @see Board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets the list of the {@code CommonGoal}s in the actual {@code Game}
     *
     * @return the common goals for the players.
     *
     * @see CommonGoal
     * @see Game
     */
    public List<CommonGoal> getCommonGoals() {
        return this.commonGoals;
    }

    /**
     * Method to set the {@code CommonGoal}s for all the players in a {@code Game}.
     *
     * @param commonGoals is the list of goals achievable from all the players
     *                    in any given moment of the {@code Game}.
     *
     * @see Game
     * @see CommonGoal
     */
    public void setCommonGoals(List<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
        if (this.listener != null) {
            this.listener.commonGoalsModified();
        } else {
            System.err.println("Game's listener is NULL!");
        }
    }

    private void getPlayerScore(Player player) {
        player.score();
    }

    private boolean isPaused() {
        return this.connectedPlayers().size() == 1;
    }

    /**
     * Identify the list of the active {@code Player}s which
     * are still connected and participating the {@code Game}.
     *
     * @return the list of the active players.
     *
     * @see Player
     * @see Game
     */
    private List<Player> connectedPlayers() {
        return this.players.stream()
                .filter(Player::isConnected)
                .collect(Collectors.toList());
    }

    /**
     * Returns the name of the selected {@code Player} by his nickname.
     *
     * @param nickname of the player to return
     * @return the player selected by his nickname
     *
     * @see Player
     */
    public Player getPlayerFromNickname(String nickname) {
        return this.players.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }

    /**
     * Method used to save the current state of the {@code Game}.
     * The state is stored in a json file. BY saving
     * it, it is possible to restore the state of the game to
     * a certain moment avoiding the risk of losing information
     * about the score of a {@code Player} in case of disconnection.
     *
     * @see Game
     * @see Player
     */
    public void saveGame() {
        Gson gson = new Gson();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("src/main/resources/storage/games.json");
            gson.toJson(this, fileWriter);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Game.class, new GameSerializer());
//
//        Gson gson = gsonBuilder.setPrettyPrinting().create();
//        FileWriter fileWriter;
//        try {
//            fileWriter = new FileWriter("src/main/resources/storage/games.json");
//            gson.toJson(this, fileWriter);
//
//            fileWriter.flush();
//            fileWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

}
