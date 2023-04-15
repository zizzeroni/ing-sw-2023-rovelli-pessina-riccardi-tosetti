package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.listeners.BoardListener;
import it.polimi.ingsw.model.listeners.BookshelfListener;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GameView implements GameListener, BoardListener, BookshelfListener, Serializable {
    private static final long serialVersionUID = 1L;
//    private final Game gameModel;
    private ModelViewListener listener;
    private final int numberOfPlayers;
    private final int activePlayerIndex;
    private final List<Player> players;
    private final List<Tile> bag;
    private final Board board;
    private final List<CommonGoal> commonGoals;

    public void registerListener(ModelViewListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    public GameView(Game gameModel) {
        this.bag = gameModel.getBag();
        this.activePlayerIndex = gameModel.getActivePlayerIndex();
        this.board = gameModel.getBoard();
        this.commonGoals = gameModel.getCommonGoals();
        this.players = gameModel.getPlayers();
        this.numberOfPlayers = gameModel.getNumberOfPlayers();
//        if (gameModel == null) {
//            throw new IllegalArgumentException();
//        }
//        this.gameModel = gameModel;
////        gameModel.addObserver(this);
    }

    public int getNumPlayers() {
        return this.numberOfPlayers;
    }

    public int getActivePlayerIndex() {
        return this.activePlayerIndex;
    }

    public List<PlayerView> getPlayers() {
        List<PlayerView> playerViews = new ArrayList<>();
        for (Player player : this.players) {
            playerViews.add(new PlayerView(player));
        }
        return playerViews;
    }

    public List<TileView> getBag() {
        List<TileView> tileViews = new ArrayList<>();
        for (Tile tile : this.bag) {
            tileViews.add(new TileView(tile));
        }
        return tileViews;
    }

    public BoardView getBoard() {
        return new BoardView(this.board);
    }

    public List<CommonGoalView> getCommonGoals() {
        List<CommonGoalView> temp = new ArrayList<>();
        for (CommonGoal commonGoal : this.commonGoals) {
            temp.add(new CommonGoalView(commonGoal));
        }
        return temp;
    }

    @Override
    public void addedTilesToBoard(Board board) {
        this.listener.modelModified(this);
    }

    @Override
    public void removedTilesFromBoard(Board board) {
        this.listener.modelModified(this);
    }

    @Override
    public void tileAddedToBookshelf(Bookshelf bookshelf) {
        this.listener.modelModified(this);
    }

    @Override
    public void imageModified(String image) {
        this.listener.modelModified(this);
    }

    @Override
    public void numberOfPlayersModified() {
        this.listener.modelModified(this);
    }

    @Override
    public void activePlayerIndexModified() {
        this.listener.modelModified(this);
    }

    @Override
    public void bagModified() {
        this.listener.modelModified(this);
    }

    @Override
    public void commonGoalsModified() {
        this.listener.modelModified(this);
    }
}
