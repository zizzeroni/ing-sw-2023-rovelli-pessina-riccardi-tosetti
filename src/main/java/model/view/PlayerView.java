package model.view;

import model.Player;
import model.tile.ScoreTile;

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
        for(ScoreTile scoreTile : this.playerModel.getGoalTiles()) {
            temp.add(new GoalTileView(scoreTile));
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

    //TODO: Chiedere se è una soluzione corretta
    public int score() {
        int score = 0;
        for (ScoreTile scoreTile : this.playerModel.getGoalTiles()) {
            score += scoreTile.getValue();
        }
        score += this.playerModel.getBookshelf().score();
        score += this.playerModel.getPersonalGoal().score(this.playerModel.getBookshelf());

        return score;// viene calcolato in base al numero di giocatori
    }
}
