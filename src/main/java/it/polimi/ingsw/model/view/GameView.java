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


public class GameView implements Serializable {
    private static final long serialVersionUID = 1L;
//    private final Game gameModel;
    private ModelViewListener listener;
    private final int numberOfPlayers;
    private final boolean started;
    private final int activePlayerIndex;
    private final List<PlayerView> players;
    private final List<TileView> bag;
    private final BoardView board;
    private final List<CommonGoalView> commonGoals;

    public void registerListener(ModelViewListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    public GameView(Game gameModel) {
        if (gameModel == null) {
            throw new IllegalArgumentException();
        }
        this.started = gameModel.isStarted();
        this.players = new ArrayList<>();
        this.bag = new ArrayList<>();
        this.commonGoals = new ArrayList<>();

        this.numberOfPlayers = gameModel.getNumberOfPlayers();
        this.activePlayerIndex = gameModel.getActivePlayerIndex();
        for (Player player : gameModel.getPlayers()) {
            this.players.add(new PlayerView(player));
        }
        for (Tile tile : gameModel.getBag()) {
            this.bag.add(new TileView(tile));
        }
        this.board = new BoardView(gameModel.getBoard());
        for (CommonGoal commonGoal : gameModel.getCommonGoals()) {
            this.commonGoals.add(new CommonGoalView(commonGoal));
        }
    }

    public boolean isStarted() {
        return this.started;
    }

    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public int getActivePlayerIndex() {
        return this.activePlayerIndex;
    }

    public List<PlayerView> getPlayers() {
        return this.players;
    }

    public List<TileView> getBag() {
        return this.bag;
    }

    public BoardView getBoard() {
        return this.board;
    }

    public List<CommonGoalView> getCommonGoals() {
        return this.commonGoals;
    }
    /*
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
    }*/
}
