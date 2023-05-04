package it.polimi.ingsw.model;

import it.polimi.ingsw.model.listeners.BoardListener;
import it.polimi.ingsw.model.listeners.PlayerListener;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientImpl;


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

    private ClientImpl client;
    private GameView modelUpdated;
    private String receiver;
    private String content;

    private PlayerListener listener;
    private List<Message> chat;

    //Constructor
    public Player(String nickname, boolean connected) {
        this.nickname = nickname;
        this.connected = connected;
        this.personalGoal = null;
        this.scoreTiles = null;
        this.bookshelf = null;
    }

    public Player(String nickname, boolean connected, List<ScoreTile> scoreTiles) {
        this.nickname = nickname;
        this.connected = connected;
        this.scoreTiles = scoreTiles;
        this.personalGoal = null;
        this.bookshelf = null;
    }

    public Player(String nickname, boolean connected, List<ScoreTile> scoreTiles, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.connected = connected;
        this.scoreTiles = scoreTiles;
        this.personalGoal = null;
        this.bookshelf = bookshelf;
    }

    public Player(String nickname, boolean connected, PersonalGoal personalGoal, ArrayList<ScoreTile> scoreTiles, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.connected = connected;
        this.personalGoal = personalGoal;
        this.scoreTiles = scoreTiles;
        this.bookshelf = bookshelf;
    }

    public void registerListener(PlayerListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }
    //Getter and Setter
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    public List<ScoreTile> getGoalTiles() {
        return scoreTiles;
    }

    public void setGoalTiles(List<ScoreTile> scoreTiles) {
        this.scoreTiles = scoreTiles;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }


    public void addScoreTile(ScoreTile tile) {
        this.scoreTiles.add(tile);
    }

    /*
    Set the score of the player by the score of his bookshelf
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

        return score; //this value is based on the number of player
    }

    public void addMessage(Message message){

        if(message!=null){
            chat.add(message);
        }
        if(listener!=null){
            listener.chatUpdated();
        }
        //System.out.println("CHAT:");
        //chat.forEach((i)->System.out.println(i));

    }
}