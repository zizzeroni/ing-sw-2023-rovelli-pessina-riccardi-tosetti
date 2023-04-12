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

import java.util.ArrayList;
import java.util.List;


public class GameView implements GameListener, BoardListener, BookshelfListener {
    private final Game gameModel;
    private ModelViewListener listener;

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
        this.gameModel = gameModel;
        //gameModel.addObserver(this);
    }

    public int getNumPlayers() {
        return gameModel.getNumberOfPlayers();
    }

    public int getActivePlayerIndex() {
        return gameModel.getActivePlayerIndex();
    }

    public List<PlayerView> getPlayers() {
        List<PlayerView> playerViews = new ArrayList<>();
        for (Player player : this.gameModel.getPlayers()) {
            playerViews.add(new PlayerView(player));
        }
        return playerViews;
    }

    public List<TileView> getBag() {
        List<TileView> tileViews = new ArrayList<>();
        for (Tile tile : this.gameModel.getBag()) {
            tileViews.add(new TileView(tile));
        }
        return tileViews;
    }

    public BoardView getBoard() {
        return new BoardView(this.gameModel.getBoard());
    }

    public List<CommonGoalView> getCommonGoals() {
        List<CommonGoalView> temp = new ArrayList<>();
        for (CommonGoal commonGoal : this.gameModel.getCommonGoals()) {
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
