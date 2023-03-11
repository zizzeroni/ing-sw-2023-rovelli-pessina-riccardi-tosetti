package model;

import java.util.ArrayList;

public class Player {

    //We will use 'nickname' for identify the player, in one game there can't be two players with the same nickname
    private String nickname;
    //We will use 'connected' to indicate if the player is still connected to the game or if he isn't
    private boolean connected;

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

    public Player(String nickname, boolean connected) {
        this.nickname = nickname;
        this.connected = connected;
    }

    public int score(){
        int score = 0;

        return score ;
    }

    public ArrayList<Tile> selectTile(){

        return ArrayList<Tile>;
    }
    public ArrayList<Tile> orderSelectedTile(){

        return ArrayList<Tile>;
    }

    public int selectColumn(){
        int c = 1;

        return c;
    }

}
