package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

import java.io.Serializable;

/**
 *
 * This class implements the {@code TileView} through the {@code Serializable} interface.
 * All the {@code Player}s always access only the implementation of the {@code Tile}s various views,
 * and are sensible to any inherent modification.
 * Also, the class contains a series of getters to access their images and colors
 * and a series of other related, relevant, informations.
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Player
 */
public class TileView implements Serializable {
    private final TileColor color;
    private final int imageID;

    /**
     * Class constructor.
     * Used to associate the representation of the {@code Tile} in the {@code TileView}
     * with the linked logic in the {@code tileModel} (passed as parameter).
     *
     * @param tileModel the model of the considered {@code Tile}.
     *
     * @see Tile
     *
     */
    public TileView(Tile tileModel) {
        this.color = tileModel.getColor();
        this.imageID = tileModel.getImageID();
    }

    /**
     * Getter used to access {@code CommonGoal}'s image identifier.
     *
     * @return the {@code CommonGoal}'s imageID.
     *
     * @see CommonGoal
     */
    public int getImageID() {
        return this.imageID;
    }

    /**
     * Getter used to access {@code TileView}'s color.
     *
     * @return the tile view's color.
     *
     * @see Tile
     */
    public TileColor getColor() {
        return this.color;
    }
}
