package model;

import model.tile.GoalTile;
import model.tile.Tile;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
    public int score() {
        int score=0;/*
        int i = 0;
        Player player = new Player(nickname, connected, personalGoal, goalTile, bookshelf);
        Tile tile = new Tile();
        if (player.isConnected() == true) {
            while (i <= Game.getNumPlayers()) {
                score += player.getGoalTile().get(i).getScore(); //accesso a goal tile ?
            }
            if(personalGoalAchieved == true){ //serve ?
                score += player.goalTile.getScore(); //accesso a personal Goal ?
            }

        }*/
        return score;// viene calcolato in base al numero di giocatori
    }

    // ma è goal o tile ? Dove viene indicato il punteggio obbiettivo

    public Tile selectTile(){
         // array ?
        Tile selected = new Tile();
        Bookshelf bookshelf1 = new Bookshelf();
        Scanner scan = new Scanner(System.in);
        System.out.println("inserire valori per x");
        int x = scan.nextInt();
        System.out.println("inserire valori per y");
        int y = scan.nextInt();

        // ? ArrayList<Tile> selected = new ArrayList<Tile>();

        return selected;
    }

   /* public ArrayList<Tile> orderSelectedTile() { // no perchè sono tessere non tessere punteggio -> accesso da bookshelf
        int i = 0;
        ArrayList<Tile> orderSelected = new ArrayList<Tile>();

        }
        return orderSelected;
    }*/
    public int selectColumn(int c) {
        int i = 0;/*
        ArrayList<Tile> selection = new ArrayList<Tile>();
        playerBookshelf.getTiles();
        while (i < 6) {
            selection.add(playerBookshelf[i][c],i);
            i++;
        }*/
        return i;
    }

    public ArrayList<Tile> SelectTiles(Board board){
        ArrayList<Tile> selected = new ArrayList<Tile>();
        Tile[][] tiles;
        tiles = board.getTiles();
        for(int i = 0; i< tiles.length; i++){
            for(int j = 0; j< tiles.length; j++){
                selected.add(tiles[i][j]);
            }
        }
        return selected;
    }


}