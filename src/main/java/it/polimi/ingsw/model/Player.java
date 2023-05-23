package it.polimi.ingsw.model;

import it.polimi.ingsw.model.listeners.PlayerListener;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //We will use 'nickname' for identify the player, in one game there can't be two players with the same nickname
    private String nickname;
    //We will use 'connected' to indicate if the player is still connected to the game or if he isn't
    private boolean connected;
    private PersonalGoal personalGoal; //The single goal of the player
    private List<ScoreTile> scoreTiles; // new ArrayList<Tile>(); //The array of tile...
    private Bookshelf bookshelf; //The bookshelf of the player
    private transient PlayerListener listener;
    private final List<Message> chat;

    //Constructors
    public Player(String nickname, boolean connected) {
        this.nickname = nickname;
        this.connected = connected;
        this.personalGoal = null;
        this.scoreTiles = null;
        this.bookshelf = null;
        this.listener = null;
        this.chat = new ArrayList<>();
    }

    public Player(String nickname, boolean connected, List<ScoreTile> scoreTiles) {
        this.nickname = nickname;
        this.connected = connected;
        this.scoreTiles = scoreTiles;
        this.personalGoal = null;
        this.bookshelf = null;
        this.listener = null;
        this.chat = new ArrayList<>();
    }

    public Player(String nickname, boolean connected, List<ScoreTile> scoreTiles, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.connected = connected;
        this.scoreTiles = scoreTiles;
        this.personalGoal = null;
        this.bookshelf = bookshelf;
        this.listener = null;
        this.chat = new ArrayList<>();
    }

    public Player(String nickname, boolean connected, PersonalGoal personalGoal, ArrayList<ScoreTile> scoreTiles, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.connected = connected;
        this.personalGoal = personalGoal;
        this.scoreTiles = scoreTiles;
        this.bookshelf = bookshelf;
        this.listener = null;
        this.chat = new ArrayList<>();
    }

    public void registerListener(PlayerListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    //Getter and Setter
    public PersonalGoal getPersonalGoal() {
        return this.personalGoal;
    }

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    public List<ScoreTile> getGoalTiles() {
        return this.scoreTiles;
    }

    public void setGoalTiles(List<ScoreTile> scoreTiles) {
        this.scoreTiles = scoreTiles;
    }

    public Bookshelf getBookshelf() {
        return this.bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public String getNickname() {
        return this.nickname;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        if (listener != null) {
            listener.playerHasReconnected();
        }
    }


    public void addScoreTile(ScoreTile tile) {
        this.scoreTiles.add(tile);
    }

    public void setSingleScoreTile(ScoreTile tile, int position) {
        this.scoreTiles.set(position, tile);
    }




    /**
     * Set the score of the player considering the current state of the Bookshelf score.
     *
     * @return the score of the single player.
     */
    public int score() {
        int score = 0;
        for (ScoreTile scoreTile : this.scoreTiles) {
            score += scoreTile.getValue();
        }
        try {
            score += this.bookshelf.score();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        score += this.personalGoal.score(this.bookshelf);

        return score;
    }

    /**
     * Adds the player message to the
     *
     * @param message is the message written by the player
     */
    public void addMessage(Message message) {
        chat.add(message);

        if (listener != null) {
            listener.chatUpdated();
        }
    }


    /**
     * Returns the list of the current messages
     *
     * @return the {@Code List<Message>} of the messages sent by the active player
     */
    public List<Message> getChat(){
        return chat;
    }
}