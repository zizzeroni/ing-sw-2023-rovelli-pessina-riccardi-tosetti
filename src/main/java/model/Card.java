package model;

public abstract class Card {
    private String image;

    private int score;

    public Card() {
    }

    public Card(String image) {
        this.image = image;
    }

    public abstract boolean goalPattern();

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getScore() {
        return score;
    }

    public void setScore(String image) {
        this.score = score;
    }
}
