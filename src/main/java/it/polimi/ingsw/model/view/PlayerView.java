package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Message;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerView implements Serializable {
    //private final Player playerModel;
    private final String nickname; //We will use 'connected' to indicate if the player is still connected to the game or if he isn't
    private final boolean connected;
    private final PersonalGoalView personalGoal; //The single goal of the player
    private final List<ScoreTileView> scoreTiles; // new ArrayList<Tile>(); //The array of tile...
    private final BookshelfView bookshelf; //The bookshelf of the player
    private final List<Message> chat; //The bookshelf of the player

    public PlayerView(Player playerModel) {
        this.nickname = playerModel.getNickname();
        this.connected = playerModel.isConnected();
        this.personalGoal = new PersonalGoalView(playerModel.getPersonalGoal());
        this.scoreTiles = new ArrayList<>();
        for (ScoreTile scoreTile : playerModel.getGoalTiles()) {
            this.scoreTiles.add(new ScoreTileView(scoreTile));
        }
        this.bookshelf = new BookshelfView(playerModel.getBookshelf());
        this.chat = playerModel.getChat();

    }

    public PersonalGoalView getPersonalGoal() {
        return this.personalGoal;
    }

    public List<ScoreTileView> getGoalTiles() {
        return this.scoreTiles;
    }

    public BookshelfView getBookshelf() {
        return this.bookshelf;
    }

    public String getNickname() {
        return this.nickname;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public List<Message> getChat() {
        return this.chat;
    }


    public int score() {
        int score = 0;
        for (ScoreTileView scoreTile : this.scoreTiles) {
            score += scoreTile.getValue();
        }
        try {
            score += this.bookshelf.score();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        score += this.personalGoal.score(this.bookshelf);

        return score; //this value is based on the number of player
    }
}
