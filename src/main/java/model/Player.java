package model;

import model.tile.GoalTile;
import model.tile.Tile;


import java.util.ArrayList;
import java.util.List;

public class Player {


    //We will use 'nickname' for identify the player, in one game there can't be two players with the same nickname
    private String nickname;
    //We will use 'connected' to indicate if the player is still connected to the game or if he isn't
    private boolean connected;
    private PersonalGoal personalGoal; //The single goal of the player
    private List<GoalTile> goalTiles; // new ArrayList<Tile>(); //The array of tile...
    private Bookshelf bookshelf; //The bookshelf of the player

    //Constructor
    public Player(String nickname, boolean connected, PersonalGoal personalGoal, List<GoalTile> goalTiles, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.connected = connected;
        this.personalGoal = personalGoal;
        this.goalTiles = goalTiles;
        this.bookshelf = bookshelf;
    }

    //Getter and Setter
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    public List<GoalTile> getGoalTiles() {
        return goalTiles;
    }

    public void setGoalTiles(List<GoalTile> goalTiles) {
        this.goalTiles = goalTiles;
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


    //class methods
    public int score() {
        int score = 0;
        for (GoalTile goalTile : this.goalTiles) {
            score += goalTile.getValue();
        }
        score += this.bookshelf.score();
        score += this.personalGoal.score(this.bookshelf);

        return score;// viene calcolato in base al numero di giocatori
    }


   /* public ArrayList<Tile> orderSelectedTile() { // no ?
        int i = 0;
        ArrayList<Tile> orderSelected = new ArrayList<Tile>();

        }
        return orderSelected;
    }*/

    public int selectColumn(int c) {
        return c;
    }

    public ArrayList<Tile> selectTiles(Board board) {
        ArrayList<Tile> selected = new ArrayList<Tile>();
        Tile[][] tiles;
        tiles = board.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                selected.add(tiles[i][j]);
            }
        }
        return selected;
    }
}