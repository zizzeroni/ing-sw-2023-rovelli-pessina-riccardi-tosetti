package model;

import model.commongoal.CommonGoal;
import model.tile.Tile;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameView extends Observable implements Observer {
    private final Game model;

    public GameView(Game model) {
        this.model = model;
        model.addObserver(this);
    }

    public int getNumPlayers() {
        return model.getNumPlayers();
    }
    public int getActivePlayerIndex() {
        return model.getActivePlayerIndex();
    }
    public List<Player> getPlayers() {
        return model.getPlayers();
    }
    public List<Tile> getBag() {
        return model.getBag();
    }
    public Board getBoard() {
        return model.getBoard();
    }
    public List<CommonGoal> getCommonGoals() {
        return model.getCommonGoals();
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
