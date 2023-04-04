package model;

public abstract class Card {
    private int imageID;

    public Card() {
        this.imageID = 0;
    }

    public Card(int imageID) {
        this.imageID = imageID;
    }

    public abstract int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf);

    public int getImageID() {
        return this.imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
