package model;

abstract class Card {
    private String image;

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
}
