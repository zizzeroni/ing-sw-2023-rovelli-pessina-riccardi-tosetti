package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class is used to represents the coordinates of different elements which are used in the model, such as {@code Tile}s, ...
 * It is provided as in implementation of the {@code Serializable} interface.
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see Serializable
 */
public class Coordinates implements Serializable {
    private int x;
    private int y;

    /**
     *
     * Class Builder. Set the attributes' coordinates.
     *
     * @param x is the first coordinate.
     * @param y is the second coordinate.
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Class Builder. Set the attributes' coordinates at the default values of '0'.
     */
    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Gets the x coordinate
     *
     * @return the x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Sets the x coordinate.
     *
     * @param x the x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y coordinate
     *
     * @return the y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y the x coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * Evaluates if the coordinates of the parameter are equivalent to the
     * coordinates of the object calling the method.
     *
     * @param obj is the object which coordinates will be compared.
     * @return {@code true} if and only if the coordinates of the parameter and
     *             those of the object are equals, false otherwise.
     */
    public boolean equals(Coordinates obj) {
        return obj.getX() == this.getX() && obj.getY() == this.getY();
    }
}
