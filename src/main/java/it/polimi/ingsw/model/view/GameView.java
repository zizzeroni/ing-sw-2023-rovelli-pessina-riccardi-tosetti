package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.ObservableType;
import it.polimi.ingsw.utils.Observer;

import java.util.ArrayList;
import java.util.List;


public class GameView extends Observable<ObservableType> implements Observer<Game, ObservableType> {
    private final Game gameModel;

    public GameView(Game gameModel) {
        if (gameModel == null) {
            throw new IllegalArgumentException();
        }
        this.gameModel = gameModel;
        gameModel.addObserver(this);
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
    public void update(Game o, ObservableType arg) {
        setChanged();
        notifyObservers(arg);
    }
}
