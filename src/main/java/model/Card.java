package model;

public abstract class Card {
    private String image;

    private int score;

    public Card() {
    }

    public Card(String image) {
        this.image = image;
    }

    public abstract int goalPattern(Bookshelf b);

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
