package it.polimi.ingsw.model;

public abstract class Card {
    private int id;

    public Card() {
        this.id = 0;
    }

    public Card(int id) {
        this.id = id;
    }

    /**
     * Identify the number of repetitions of the goal pattern
     * in the chosen {@code Bookshelf}.
     *
     * @param bookshelf is the selected {@code Bookshelf}.
     * @return the total number of goal pattern repetitions.
     */
    public abstract int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf);

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
