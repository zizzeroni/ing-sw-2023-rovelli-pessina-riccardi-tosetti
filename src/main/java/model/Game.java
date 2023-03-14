package model;

import model.commongoal.CommonGoal;
import model.tile.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {

    private boolean hasStarted; 
    private int numPlayers;
    private int activePlayerIndex;
    private ArrayList<Player> players = new ArrayList<Player>(numPlayers);
    private ArrayList<Tile> bag = new ArrayList<Tile>(131); //dubbio se è 131 o 132
    private Board board;
    private ArrayList<CommonGoal> commonGoals = new ArrayList<CommonGoal>(1); //dubbio se è 1 o 2

    public Game(boolean hasStarted, int numPlayers, int activePlayerIndex, ArrayList<Player> players, ArrayList<Tile> bag, Board board, ArrayList<CommonGoal> commonGoals) {
        this.hasStarted = hasStarted;
        this.numPlayers = numPlayers;
        this.activePlayerIndex = activePlayerIndex;
        this.players = players;
        this.bag = bag;
        this.board = board;
        this.commonGoals = commonGoals;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Tile> getBag() {
        return bag;
    }

    public void setBag(ArrayList<Tile> bag) {
        this.bag = bag;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;
    }

    public void setCommonGoals(ArrayList<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public void changeTurn(){

    }

    public void refillBoard(){

    }

    public boolean isPaused(){

        return false;
    }


}
