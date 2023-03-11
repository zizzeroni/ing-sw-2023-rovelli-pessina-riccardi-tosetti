package model;

public class Game {



    private boolean hasStarted;
    private int numPlayers;
    private int activePlayerIndex;

    public Game(boolean hasStarted, int numPlayers, int activePlayerIndex) {
        this.hasStarted = hasStarted;
        this.numPlayers = numPlayers;
        this.activePlayerIndex = activePlayerIndex;
    }

    public static void game(String[] args) {

    }

    public boolean isHasStarted() {
        return hasStarted;
    }
    public void setHasStarted(boolean start){
        hasStarted = start;
    }
    public void setNumPlayers(int n){
        numPlayers=n;
    }
    public void setActivePlayerIndex(int activePlayer){
        activePlayerIndex=activePlayer;
    }

    public boolean getHasStarted(){
        return hasStarted;
    }
    public int getNumPlayers(){
        return numPlayers;
    }
    public int getActivePlayerIndex(){
        return activePlayerIndex;
    }


    public void changeTurn(){

    }

    public void refillBoard(){

    }

    public boolean isPaused(){

        return false;
    }


}
