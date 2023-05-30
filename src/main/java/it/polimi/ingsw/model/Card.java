package it.polimi.ingsw.model;

public abstract class Card {
    private int id;

    public Card() {
        this.id = 0;
    }

    public Card(int id) {
        this.id = id;
    }

    public abstract int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf);

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
