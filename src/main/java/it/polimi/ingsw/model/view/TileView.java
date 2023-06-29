package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

import java.io.Serializable;

/**
 * This class represents the Tile's view.
 * The class contains a series of getters to access their personal goals, their goal tiles, their {@code Bookshelf}s, chats
 * and a series of other class related relevant information.
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Player
 */
public class TileView implements Serializable {
    private final TileColor color;
    private final int id;

    /**
     * Class constructor.
     * Used to associate the representation of the {@code Tile} in the {@code TileView}
     * with the linked logic in the {@code tileModel} (passed as parameter).
     *
     * @param tileModel the model of the considered {@code Tile}.
     * @see Tile
     */
    public TileView(Tile tileModel) {
        this.color = tileModel.getColor();
        this.id = tileModel.getId();
    }

    /**
     * Getter used to access {@code TileView}'s identifier.
     *
     * @return the {@code Tile}'s id.
     * @see Tile
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter used to access {@code TileView}'s color.
     *
     * @return the tile view's color.
     * @see Tile
     */
    public TileColor getColor() {
        return this.color;
    }
}
