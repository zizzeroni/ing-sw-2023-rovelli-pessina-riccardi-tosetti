package model.view;

import model.Bookshelf;
import model.PersonalGoal;
import model.Player;
import model.tile.GoalTile;

import java.util.ArrayList;
import java.util.List;

public class PlayerView {
    private final Player playerModel;

    public PlayerView(Player playerModel) {
        this.playerModel = playerModel;
    }

    public PersonalGoalView getPersonalGoal() {
        return new PersonalGoalView(this.playerModel.getPersonalGoal());
    }
    public List<GoalTileView> getGoalTiles() {
        List<GoalTileView> temp = new ArrayList<>();
        for(GoalTile goalTile: this.playerModel.getGoalTiles()) {
            temp.add(new GoalTileView(goalTile));
        }
        return temp;
    }
    public BookshelfView getBookshelf() {
        return new BookshelfView(this.playerModel.getBookshelf());
    }
    public String getNickname() {
        return this.playerModel.getNickname();
    }
    public boolean isConnected() {
        return this.playerModel.isConnected();
    }
}
