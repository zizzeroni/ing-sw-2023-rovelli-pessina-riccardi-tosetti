package it.polimi.ingsw.model;

import it.polimi.ingsw.model.listeners.PlayerListener;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the {@code Player} registered in the lobby when at
 * the start of the {code Game}.
 * It provides methods to be used by the players to interact with the board and their bookshelves.
 * It also links the player's with its objectives using a list of {@code ScoreTile}s and
 * one providing access to the player's personal and common goals.
 *
 * @see Game
 * @see Tile
 * @see ScoreTile
 * @see PersonalGoal
 * @see it.polimi.ingsw.model.commongoal.CommonGoal
 */
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

    /**
     * Class constructor.
     *
     * @param nickname  player's nickname.
     * @param connected indicates the status of player's connection.
     */
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

    /**
     * Class constructor.
     *
     * @param nickname   player's nickname.
     * @param connected  indicates the status of player's connection.
     * @param scoreTiles the player's list of scoreTiles
     */
    public Player(String nickname, boolean connected, List<ScoreTile> scoreTiles) {
        this.nickname = nickname;
        this.connected = connected;
        this.scoreTiles = scoreTiles;
        this.personalGoal = null;
        this.bookshelf = null;
        this.listener = null;
        this.chat = new ArrayList<>();
    }

    /**
     * Class constructor.
     *
     * @param nickname   player's nickname.
     * @param connected  indicates the status of player's connection.
     * @param scoreTiles the player's list of {@code ScoreTile}s.
     * @param bookshelf  the player's {@code Bookshelf}.
     * @see Bookshelf
     * @see ScoreTile
     */
    public Player(String nickname, boolean connected, List<ScoreTile> scoreTiles, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.connected = connected;
        this.scoreTiles = scoreTiles;
        this.personalGoal = null;
        this.bookshelf = bookshelf;
        this.listener = null;
        this.chat = new ArrayList<>();
    }

    /**
     * Class constructor.
     * In this implementation all the possible player's parameter are used.
     *
     * @param nickname     player's nickname.
     * @param connected    indicates the status of player's connection.
     * @param scoreTiles   the player's list of {@code ScoreTile}s.
     * @param bookshelf    the player's {@code Bookshelf}.
     * @param personalGoal the player's {@code PersonalGoal}.
     * @see Bookshelf
     * @see ScoreTile
     * @see PersonalGoal
     */
    public Player(String nickname, boolean connected, PersonalGoal personalGoal, ArrayList<ScoreTile> scoreTiles, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.connected = connected;
        this.personalGoal = personalGoal;
        this.scoreTiles = scoreTiles;
        this.bookshelf = bookshelf;
        this.listener = null;
        this.chat = new ArrayList<>();
    }

    /**
     * Registers the {@code PlayerListener} on the PLayer.
     *
     * @param listener the listener that will register on the {@code Player}.
     * @see PlayerListener
     * @see java.net.http.WebSocket.Listener
     * @see Player
     * @see java.net.http.WebSocket.Listener
     */
    public void registerListener(PlayerListener listener) {
        this.listener = listener;
    }

    /**
     * Removes the player's listener {@code PlayerListener}.
     *
     * @see PlayerListener
     */
    public void removeListener() {
        this.listener = null;
    }

    /**
     * Getter to retrieve the player's personal goal.
     *
     * @return the player's {@code PersonalGoal}.
     * @see PersonalGoal
     */
    //Getter and Setter
    public PersonalGoal getPersonalGoal() {
        return this.personalGoal;
    }

    /**
     * Sets the player's personal goal.
     *
     * @param personalGoal the player's {@code PersonalGoal}.
     * @see PersonalGoal
     */
    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    /**
     * Gets the goal {@code Tile}s list.
     *
     * @return the player's goal tiles list.
     * @see Tile
     */
    public List<ScoreTile> getScoreTiles() {
        return this.scoreTiles;
    }

    /**
     * Sets the goal {@code Tile}s list.
     *
     * @param scoreTiles is the player list of goal tiles.
     * @return the player's goal tiles list.
     * @see Tile
     */
    public void setScoreTiles(List<ScoreTile> scoreTiles) {
        this.scoreTiles = scoreTiles;
    }

    /**
     * Gets the {@code Bookshelf} associated to the current {@code Player}.
     *
     * @return the Bookshelf of the given player.
     * @see Bookshelf
     */
    public Bookshelf getBookshelf() {
        return this.bookshelf;
    }

    /**
     * Gets the values of the {@code Bookshelf}'s {@code Tile}s associated to the current {@code Player}.
     *
     * @see Bookshelf
     * @see Tile
     */
    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    /**
     * Method used to get the player's nickname.
     *
     * @return the nickname chosen by the player.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Gets the player's connection state.
     *
     * @return {@code true} if and only if the connection is established,
     * false otherwise.
     */
    public boolean isConnected() {
        return this.connected;
    }

    /**
     * Method used to set the player's nickname.
     *
     * @param nickname the nickname chosen by the player.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Sets the player's connection state to 'connected'.
     *
     * @param connected is the boolean to be set true or false depending on connection conditions.
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
        if (listener != null && this.connected) {
            listener.playerHasReconnected();
        }
    }


    /**
     * Adds the given {@code ScoreTile} to the player's list of score {@code Tile}s.
     *
     * @param tile the tile to be added.
     * @see Tile
     * @see ScoreTile
     */
    public void addScoreTile(ScoreTile tile) {
        this.scoreTiles.add(tile);
    }

    /**
     * Sets the values of the specified {@code ScoreTile}.
     *
     * @param tile     the tile to be set.
     * @param position the tile's position.
     * @see ScoreTile
     * @see Tile
     */
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
     * Adds the player message to the list of his messages that will be displayed on the chat
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
    public List<Message> getChat() {
        return chat;
    }
}