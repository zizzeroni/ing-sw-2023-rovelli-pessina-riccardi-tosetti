package it.polimi.ingsw.model;

/**
 * This class is used to represent the Card objects.
 * It provides methods to access and modify the image of the card, plus a method
 * to associate the card to the pattern it represents and counting the number of its
 * repetitions inside the {@code Bookshelf}.
 *
 * @see Bookshelf
 */
public abstract class Card {
    private int id;

    /**
     * Class constructor.
     * Initialize the single card.
     */
    public Card() {
        this.id = 0;
    }

    /**
     * Class constructor.
     * Initialize the card's identifier.
     *
     * @param id the card's identifier.
     */
    public Card(int id) {
        this.id = id;
    }

    /**
     * Identify the number of repetitions of the goal pattern
     * in the chosen {@code Bookshelf}.
     *
     * @param bookshelf is the selected {@code Bookshelf}.
     * @return the total number of goal pattern repetitions.
     *
     * @see Bookshelf
     */
    public abstract int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf);

    /**
     * Getter used to access {@code Card}'s image identifier.
     *
     * @return the {@code Card}'s imageID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter used to modify {@code Card}'s identifier.
     *
     * @param id is the identifier of the considered card.
     */
    public void setId(int id) {
        this.id = id;
    }
}
