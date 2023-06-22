package it.polimi.ingsw.model;

/**
 *
 */
public abstract class Card {
    private int imageID;

    /**
     *
     */
    public Card() {
        this.imageID = 0;
    }

    public Card(int imageID) {
        this.imageID = imageID;
    }

    /**
     * Identify the number of repetitions of the goal pattern
     * in the chosen {@code Bookshelf}.
     *
     * @param bookshelf is the selected {@code Bookshelf}.
     * @return the total number of goal pattern repetitions.
     */
    public abstract int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf);

    /**
     * @return
     */
    public int getImageID() {
        return this.imageID;
    }

    /**
     * @param imageID
     */
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
