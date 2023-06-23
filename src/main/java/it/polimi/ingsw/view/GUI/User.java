package it.polimi.ingsw.view.GUI;

public class User {
    private String numberOfPlayer;
    private String firstPlayerNickname;
    public User() {
        this.numberOfPlayer = "";
        this.firstPlayerNickname = "";
    }
    public User(String numberOfPlayer, String firstPlayerNickname) {
        this.numberOfPlayer = numberOfPlayer;
        this.firstPlayerNickname = firstPlayerNickname;
    }

    public String getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(String numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public String getFirstPlayerNickname() {
        return firstPlayerNickname;
    }

    public void setFirstPlayerNickname(String firstPlayerNickname) {
        this.firstPlayerNickname = firstPlayerNickname;
    }
}
