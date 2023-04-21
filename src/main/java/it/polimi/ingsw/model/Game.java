package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.commongoal.*;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import java.util.stream.Collectors;

public class Game {
    private GameListener listener;

    private boolean started;
    private int numberOfPlayers;
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
        this.started = false;
        this.listener = null;
        this.players = new ArrayList<>();
        this.activePlayerIndex = 0;
        this.board = null;
        this.numberOfPlayers = 0;
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
    public Game(int numberOfPlayers, List<Player> players, List<PersonalGoal> personalGoals, JsonBoardPattern boardPattern) {
        this.started = false;
        this.listener = null;
        this.players = players;
        this.activePlayerIndex = 0;
        this.board = new Board(boardPattern);
        this.numberOfPlayers = numberOfPlayers;
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

    public Game(int numberOfPlayers, int activePlayerIndex, List<Player> players, List<Tile> bag, Board board, List<CommonGoal> commonGoals) {
        this.numberOfPlayers = numberOfPlayers;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
        this.listener = null;
    }

    public Game(GameListener listener, int numberOfPlayers, int activePlayerIndex, List<Player> players, List<Tile> bag, Board board, List<CommonGoal> commonGoals) {
        this.listener = listener;
        this.numberOfPlayers = numberOfPlayers;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
        if(this.listener!=null) {
            this.listener.startOfTheGame();
        }
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        if (this.listener != null) {
            this.listener.numberOfPlayersModified();
        }
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
        if (this.listener != null) {
            this.listener.activePlayerIndexModified();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        if(this.listener!=null) {
            this.listener.addedPlayer();
        }
    }

    public List<Tile> getBag() {
        return bag;
    }

    public void setBag(List<Tile> bag) {
        this.bag = bag;
        if (this.listener != null) {
            this.listener.bagModified();
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        /*Tile[][] boardCopy=new Tile[this.board.getNumberOfRows()][this.board.getNumberOfColumns()];
        for(int row = 0;row<this.board.getNumberOfRows();row++) {
            for(int column = 0;column<this.getBoard().getNumberOfColumns();column++) {
                this.board.setSingleTile(row,column,new Tile(board.getSingleTile(row,column).getColor()!=null ? board.getSingleTile(row,column).getColor() : null));
            }
        }*/
        this.board=board;
    }

    public List<CommonGoal> getCommonGoals() {
        return commonGoals;
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
        return players.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }
}
