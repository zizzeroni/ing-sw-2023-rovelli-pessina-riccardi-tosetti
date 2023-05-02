package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.*;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.stream.Collectors;

public class Game {
    private GameListener listener;
    //private boolean started;
    private GameState gameState;
    private int numberOfPlayersToStartGame;
    private int activePlayerIndex;
    private List<Player> players;
    private List<Tile> bag;
    private Board board;
    private List<CommonGoal> commonGoals;
    private final Random randomizer = new Random();

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
        this.bag = new ArrayList<>(132);
        this.commonGoals = new ArrayList<>(2);
        for (int i = 0; i < 132; i++) {
            this.bag.add(new Tile(TileColor.values()[i % 6]));
        }
        this.board = new Board();
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
        Collections.shuffle(this.bag);

    }

    public Game(int numberOfPlayersToStartGame, List<Player> players, List<PersonalGoal> personalGoals, JsonBoardPattern boardPattern) {
        this.gameState = GameState.IN_CREATION;
        this.listener = null;
        this.players = players;
        this.activePlayerIndex = 0;
        this.board = new Board(boardPattern);
        this.numberOfPlayersToStartGame = numberOfPlayersToStartGame;
        this.bag = new ArrayList<>(132);
        this.commonGoals = new ArrayList<>(2);

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
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        if (this.listener != null) {
            this.listener.startOfTheGame();


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
        }
    }

    private void getPlayerScore(Player player) {
        player.score();
    }

    private boolean isPaused() {
        return this.connectedPlayers().size() == 1;
    }

    private List<Player> connectedPlayers() {
        return this.players.stream()
                .filter(Player::isConnected)
                .collect(Collectors.toList());
    }

    private CommonGoal getRandomCommonGoalSubclassInstance() throws Exception {
        switch (this.randomizer.nextInt(12)) {
            case 0 -> {
                return new EightShapelessPatternGoal();
            }
            case 1 -> {
                return new MinEqualsTilesPattern(0, 2, CheckType.DIFFERENT, Direction.HORIZONTAL, 0);
            }
            case 2 -> {
                return new MinEqualsTilesPattern(0, 3, CheckType.INDIFFERENT, Direction.VERTICAL, 3);
            }
            case 3 -> {
                return new DiagonalEqualPattern(1, 1, CheckType.EQUALS, new int[][]{
                        {1, 0, 1},
                        {0, 1, 0},
                        {1, 0, 1},
                });
            }
            case 4 -> {
                return new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
            }
            case 5 -> {
                return new StairPatternGoal(1, 1, CheckType.INDIFFERENT);
            }
            case 6 -> {
                return new MinEqualsTilesPattern(0, 2, CheckType.DIFFERENT, Direction.VERTICAL, 0);
            }
            case 7 -> {
                return new DiagonalEqualPattern(1, 1, CheckType.EQUALS, new int[][]{
                        {1, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1},
                });
            }
            case 8 -> {
                return new ConsecutiveTilesPatternGoal(1, 6, CheckType.EQUALS, 2);
            }
            case 9 -> {
                return new TilesInPositionsPatternGoal(1, 1, CheckType.EQUALS, new int[][]{
                        {1, 1},
                        {1, 1},
                });
            }
            case 10 -> {
                return new ConsecutiveTilesPatternGoal(1, 4, CheckType.EQUALS, 4);
            }
            case 11 -> {
                return new FourCornersPatternGoal();
            }
            default -> {
                throw new Exception("This class does not exists");
            }
        }
    }

    private Player getPlayerFromNickname(String nickname) {
        return this.players.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }
}
