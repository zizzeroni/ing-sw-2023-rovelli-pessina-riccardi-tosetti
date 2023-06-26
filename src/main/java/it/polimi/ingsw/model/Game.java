package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.utils.GameModelDeserializer;
import it.polimi.ingsw.utils.OptionsValues;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private transient GameListener listener;
    private GameState gameState;
    private int numberOfPlayersToStartGame;
    private int activePlayerIndex;
    private List<Player> players;
    private List<Tile> bag;
    private Board board;
    private List<CommonGoal> commonGoals;

    public void registerListener(GameListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    public Game() {
        this.gameState = GameState.IN_CREATION;
        this.listener = null;
        this.players = new ArrayList<>();
        this.activePlayerIndex = 0;
        this.board = null;
        this.numberOfPlayersToStartGame = 0;
        int numberOfTilesInBag = OptionsValues.BAG_SIZE;
        this.bag = new ArrayList<>(numberOfTilesInBag);
        this.commonGoals = new ArrayList<>(OptionsValues.NUMBER_OF_COMMON_GOAL);
        this.initializeBag(numberOfTilesInBag);
        this.board = new Board();

    }

    public Game(int numberOfPlayersToStartGame, List<Player> players, List<PersonalGoal> personalGoals, JsonBoardPattern boardPattern) {
        this.gameState = GameState.IN_CREATION;
        this.listener = null;
        this.players = players;
        this.activePlayerIndex = 0;
        this.board = new Board(boardPattern);
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        int numberOfTilesInBag = OptionsValues.BAG_SIZE;
        this.bag = new ArrayList<>(numberOfTilesInBag);
        this.commonGoals = new ArrayList<>();
        this.initializeBag(numberOfTilesInBag);

        Collections.shuffle(personalGoals);

        //initialize players
        for (Player player : this.players) {
            player.setBookshelf(new Bookshelf());

            player.setScoreTiles(new ArrayList<>(OptionsValues.NUMBER_OF_SCORE_TILE));

            player.setPersonalGoal(personalGoals.remove(0));
        }

        Collections.shuffle(this.bag);

        List<Tile> drawnTiles = this.bag.subList(0, this.board.numberOfTilesToRefill());
        this.board.addTiles(drawnTiles);
    }

    public Game(int numberOfPlayersToStartGame, int activePlayerIndex, List<Player> players, List<Tile> bag, Board board, List<CommonGoal> commonGoals) {
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
        this.listener = null;
    }

    public Game(GameListener listener, int numberOfPlayersToStartGame, int activePlayerIndex, List<Player> players, List<Tile> bag, Board board, List<CommonGoal> commonGoals) {
        this.listener = listener;
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public synchronized void setGameState(GameState gameState) {
        this.gameState = gameState;
        if (this.listener != null) {
            this.listener.gameStateChanged();
        } else {
            System.err.println("Game's listener is NULL!");
        }
    }

    public int getNumberOfPlayersToStartGame() {
        return this.numberOfPlayersToStartGame;
    }

    public void setNumberOfPlayersToStartGame(int numberOfPlayersToStartGame) {
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        if (this.listener != null) {
            this.listener.numberOfPlayersModified();
        }
    }

    public int getActivePlayerIndex() {
        return this.activePlayerIndex;
    }

    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;

        if (this.listener != null) {
            this.listener.activePlayerIndexModified();
        } else {
            System.err.println("Game's listener is NULL!");
        }
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        if (this.listener != null) {
            this.listener.addedPlayer();
        } else {
            System.err.println("Game's listener is NULL!");
        }
    }

    public List<Tile> getBag() {
        return this.bag;
    }

    public void setBag(List<Tile> bag) {
        this.bag = bag;
        if (this.listener != null) {
            this.listener.bagModified();
        }
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<CommonGoal> getCommonGoals() {
        return this.commonGoals;
    }

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
        return this.connectedPlayers().size() == OptionsValues.MIN_PLAYERS_TO_GO_ON_PAUSE;
    }

    private List<Player> connectedPlayers() {
        return this.players.stream()
                .filter(Player::isConnected)
                .collect(Collectors.toList());
    }

    public Player getPlayerFromNickname(String nickname) {
        return this.players.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }

    public boolean isPlayerInGame(String nickname) {
        return this.players.stream().anyMatch(player -> player.getNickname().equals(nickname));
    }
    public void createGameFileIfNotExist(String gamesPath){
        //create a new empty games file if it does not exist
        File gamesFile = new File(gamesPath);

        try {
            if (gamesFile.createNewFile()) {
                System.out.println("Games' storage file created correctly");
            } else {
                System.out.println("Games' storage file already exists, skipping creation");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveGame() {
        //there is no need to store games in different files or handle simultaneous access to the file because there
        //are no cases in which this method is called from different games (multi game is not available) neither the
        //file is accessed while saving and vice-versa

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Game.class, new GameModelDeserializer());
        Gson gson = gsonBuilder.create();
        Reader fileReader;
        FileWriter fileWriter;
        String gamesPath = "src/main/resources/storage/games.json";
        String gamesBkpPath = "src/main/resources/storage/games-bkp.json";
        Path source = Paths.get(gamesPath);
        List<Game> games;

        try {
            this.createGameFileIfNotExist(gamesPath);
            //make a backup of the stored games in case something goes wrong during the saving
            Files.copy(source, Paths.get(gamesBkpPath), StandardCopyOption.REPLACE_EXISTING);

            fileReader = Files.newBufferedReader(source);

            Game[] gamesAsArray = gson.fromJson(fileReader, Game[].class);
            fileReader.close();

            fileWriter = new FileWriter(gamesPath);
            if (gamesAsArray == null) gamesAsArray = new Game[0];
            games = new ArrayList<>(Arrays.asList(gamesAsArray));

            if (!games.isEmpty()) {
                //use hash set in filter to increase performance
                Game storedCurrentGame = games.stream()
                        .filter(game -> new HashSet<>(
                                game.players.stream()
                                        .map(Player::getNickname).toList())
                                .containsAll(this.players.stream().map(Player::getNickname).toList())
                                && game.getGameState() != GameState.RESET_NEEDED
                        )
                        .findFirst()
                        .orElse(null);
                System.out.println(storedCurrentGame);
                if (storedCurrentGame != null) {
                    games.set(games.indexOf(storedCurrentGame), this);
                } else {
                    games.add(this);
                }
            } else {
                games = new ArrayList<>();
                games.add(this);
            }

            gson.toJson(games, fileWriter);

            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeBag(int numberOfTilesInBag) {
        int id = 0;
        //initialize bag and shuffle items
        for (int i = 0; i < numberOfTilesInBag; i++) {
            if (i == numberOfTilesInBag / 3) {
                id = 1;
            }
            if (i == numberOfTilesInBag * 2 / 3) {
                id = 2;
            }
            this.bag.add(new Tile(TileColor.values()[i % 6], id));
        }
    }
}
