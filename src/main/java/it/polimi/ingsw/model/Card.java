package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.Tile;

/**
 * This class is used to represent the Card objects.
 * It provides methods to access and modify the image of the card, plus a method
 * to associate the card to the pattern it represents and counting the number of its
 * repetitions inside the {@code Bookshelf}.
 *
 * @see Bookshelf
 */
public abstract class Card {
    private int imageID;

    /**
     * Class constructor.
     * Initialize the single card.
     */
    public Card() {
        this.imageID = 0;
    }

    /**
     * Class constructor.
     * Initialize the card's image.
     *
     * @param imageID the card's image.
     */
    public Card(int imageID) {
        this.imageID = imageID;
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
    public int getImageID() {
        return this.imageID;
    }

    /**
     * Setter used to modify {@code Card}'s imageID.
     *
     * @param imageID is the image identifier of the considered card.
     */
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
