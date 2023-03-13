package model;

import java.util.ArrayList;

public class Player {



    //We will use 'nickname' for identify the player, in one game there can't be two players with the same nickname
    private String nickname;
    //We will use 'connected' to indicate if the player is still connected to the game or if he isn't
    private boolean connected;

    private PersonalGoal personalGoal; //The single goal of the player
    private ArrayList<Tile> goalTile = new ArrayList<Tile>(); //The array of tile...
    private Bookshelf bookshelf; //The bookshelf of the player

    //Constructor
    public Player(String nickname, boolean connected, PersonalGoal personalGoal, ArrayList<Tile> goalTile, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.connected = connected;
        this.personalGoal = personalGoal;
        this.goalTile = goalTile;
        this.bookshelf = bookshelf;
    }

    //Getter and Setter
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    public ArrayList<Tile> getGoalTile() {
        return goalTile;
    }

    public void setGoalTile(ArrayList<Tile> goalTile) {
        this.goalTile = goalTile;
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
    public int score(){
        int score = 0;

        return score ;
    }

    public ArrayList<Tile> selectTile(){

        ArrayList<Tile> selected = new ArrayList<Tile>();

        return selected;
    }
    public ArrayList<Tile> orderSelectedTile(){

        ArrayList<Tile> orderSelected = new ArrayList<Tile>();

        return orderSelected;
    }

    public int selectColumn(){
        int c = 1;

        return c;
    }

}
