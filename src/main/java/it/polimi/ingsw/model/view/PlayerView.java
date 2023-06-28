package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Message;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class represents the player's view.
 * The class contains a series of getters to access their personal goals, their goal tiles, their {@code Bookshelf}s, chats
 * and a series of other player related relevant informations.
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Player
 */
public class PlayerView implements Serializable {
    //private final Player playerModel;
    private final String nickname; //We will use 'connected' to indicate if the player is still connected to the game or if he isn't
    private final boolean connected;
    private final PersonalGoalView personalGoal; //The single goal of the player
    private final List<ScoreTileView> scoreTiles; // new ArrayList<Tile>(); //The array of tile...
    private final BookshelfView bookshelf; //The bookshelf of the player
    private final List<Message> chat; //The bookshelf of the player

    /**
     * Class constructor.
     * Used to associate the representation of the {@code Player} in the {@code PlayerView}
     * with the linked logic in the {@code playerModel} (passed as parameter).
     *
     * @param playerModel the model of the considered {@code Player}.
     *
     * @see Player
     *
     */
    public PlayerView(Player playerModel) {
        this.nickname = playerModel.getNickname();
        this.connected = playerModel.isConnected();
        this.personalGoal = new PersonalGoalView(playerModel.getPersonalGoal());
        this.scoreTiles = new ArrayList<>();
        for (ScoreTile scoreTile : playerModel.getScoreTiles()) {
            this.scoreTiles.add(new ScoreTileView(scoreTile));
        }
        this.bookshelf = new BookshelfView(playerModel.getBookshelf());
        this.chat = playerModel.getChat();

    }

    /**
     * Getter used to access the {@code Player}'s {@code PersonalGoal}.
     *
     * @return the player's personalGoal.
     *
     * @see Player
     * @see PersonalGoal
     */
    public PersonalGoalView getPersonalGoal() {
        return this.personalGoal;
    }

    /**
     * @return
     */
    public List<ScoreTileView> getScoreTiles() {
        return this.scoreTiles;
    }

    /**
     * Gets the {@code Bookshelf} associated to the current {@code Player}'s view.
     *
     * @return the Bookshelf of the given player.
     *
     * @see Player
     */
    public BookshelfView getBookshelf() {
        return this.bookshelf;
    }

    /**
     * Gets the {@code Player}'s nickname in the PlayerView context.
     *
     * @return the nickname of the selected player.
     *
     * @see Player
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Verifies the {@code Player}'s connection.
     *
     * @return {@code true} if and only if the player is still connected to
     *          the considered {@code Game}.
     */
    public boolean isConnected() {
        return this.connected;
    }

    /**
     * Used to access the {@code PlayerView}'s chat.
     *
     * @return the chat of the player.
     *
     * @see Player
     */
    public List<Message> getChat() {
        return this.chat;
    }


    /**
     * Based on the identifier number of the active {@code Player},
     * provides its score.
     *
     * @return the score of the considered player.
     *
     * @see Player
     */
    public int score() {
        int score = 0;
        for (ScoreTileView scoreTile : this.scoreTiles) {
            score += scoreTile.getValue();
        }
        try {
            score += this.bookshelf.score();
        } catch (Exception e) {
            System.err.println("Error while calculating player's score");
        }
        score += this.personalGoal.score(this.bookshelf);

        return score; //this value is based on the number of player
    }
}
