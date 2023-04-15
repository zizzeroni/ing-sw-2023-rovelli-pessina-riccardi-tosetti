package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.tile.ScoreTile;

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

    public List<ScoreTileView> getGoalTiles() {
        List<ScoreTileView> scoreTileViews = new ArrayList<>();
        for (ScoreTile scoreTile : this.playerModel.getGoalTiles()) {
            scoreTileViews.add(new ScoreTileView(scoreTile));
        }
        return scoreTileViews;
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
        try {
            score += this.playerModel.getBookshelf().score();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        score += this.playerModel.getPersonalGoal().score(this.playerModel.getBookshelf());

        return score; //this value is based on the number of player
    }
}
