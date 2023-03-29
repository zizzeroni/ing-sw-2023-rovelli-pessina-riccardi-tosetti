package model;

import controller.GameController;
import model.commongoal.CommonGoal;
import model.tile.Tile;
import utils.Observable;
import utils.Observer;

import java.util.List;


public class GameView extends Observable<Event> implements Observer<Game, Event> {
    private final Game model;

    public GameView(Game model) {
        this.model = model;
        //model.addObserver(this);
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
    public void update(Game o, Event arg) {

    }
}
