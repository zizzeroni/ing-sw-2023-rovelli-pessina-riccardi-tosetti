package org.example.model.view;

import org.example.model.Game;
import org.example.model.Player;
import org.example.model.commongoal.CommonGoal;
import org.example.model.tile.Tile;
import org.example.utils.Observable;
import org.example.utils.ObservableType;
import org.example.utils.Observer;

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
        return gameModel.getNumPlayers();
    }
    public int getActivePlayerIndex() {
        return gameModel.getActivePlayerIndex();
    }
    public List<PlayerView> getPlayers() {
        List<PlayerView> temp = new ArrayList<>();
        for(Player player: this.gameModel.getPlayers()) {
            temp.add(new PlayerView(player));
        }
        return temp;
    }
    public List<TileView> getBag() {
        List<TileView> temp = new ArrayList<>();
        for(Tile tile: this.gameModel.getBag()) {
            temp.add(new TileView(tile));
        }
        return temp;
    }
    public BoardView getBoard() {
        return new BoardView(this.gameModel.getBoard());
    }
    public List<CommonGoalView> getCommonGoals() {
        List<CommonGoalView> temp = new ArrayList<>();
        for(CommonGoal commonGoal: this.gameModel.getCommonGoals()) {
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
