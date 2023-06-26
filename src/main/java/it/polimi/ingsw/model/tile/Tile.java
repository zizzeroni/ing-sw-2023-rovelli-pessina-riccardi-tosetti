package it.polimi.ingsw.model.tile;

/**
 * This class represents the {@code Tile} and all the necessary
 * methods (getters and setters) used to access and modify its fields (color, imageID).
 */
public class Tile {
    private TileColor color;
    private int id;

    /**
     * Constructor of the class, sets tile color and id to default values.
     */
    public Tile() {
        this.color = null;
        this.id = 0;
    }

<<<<<<< HEAD
    /**
     * Constructor of the class, assigns {@code Tile}'s
     * color and id (passed as parameters).
     *
     * @param color the tile's color.
     * @param imageID the identifier associated to the tile.
     *
     * @see Tile
     */
    public Tile(TileColor color, int imageID) {
=======
    public Tile(TileColor color, int id) {
>>>>>>> 859bad82d69f5d3a13cbdcd56fcc32f950648cfd
        this.color = color;
        this.id = id;
    }

<<<<<<< HEAD
    /**
     * Getter used to access {@code Tile}'s image identifier.
     *
     * @return the tile's imageID.
     *
     * @see Tile
     */
    public int getImageID() {
        return this.imageID;
    }

    /**
     * Setter used to modify {@code Tile}'s imageID.
     *
     * @param imageID is the image identifier of the considered tile.
     *
     * @see Tile
     */
    public void setImageID(int imageID) {
        this.imageID = imageID;
=======
    public int getId() {
        return this.id;
    }

    public void setid(int id) {
        this.id = id;
>>>>>>> 859bad82d69f5d3a13cbdcd56fcc32f950648cfd
    }

    /**
     * Class constructor.
     * Sets the {@code Tile}'s color to an initial value.
     *
     * @param color is the color given to the current tile.
     *
     * @see Tile
     */
    public Tile(TileColor color) {
        this.color = color;
    }

    /**
     * Getter used to access {@code Tile}'s color.
     *
     * @return the tile's color.
     *
     * @see Tile
     */
    public TileColor getColor() {
        return this.color;
    }

    /**
     * Setter used to modify {@code Tile}'s color.
     *
     * @param color is the color of the considered tile.
     *
     * @see Tile
     */
    public void setColor(TileColor color) {
        this.color = color;
    }
}
