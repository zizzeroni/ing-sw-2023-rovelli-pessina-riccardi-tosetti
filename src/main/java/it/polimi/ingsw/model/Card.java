package it.polimi.ingsw.model;

public abstract class Card {
    private int imageID;

    public Card() {
        this.imageID = 0;
    }

    public Card(int imageID) {
        this.imageID = imageID;
    }

    /**
     * Identify the number of repetitions of the goal pattern in the chosen Bookshelf.
     *
     * @param bookshelf is the selected Bookshelf.
     * @return the number of goal pattern repetitions.
     */
    public abstract int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf);

    public int getImageID() {
        return this.imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
