package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.utils.OptionsValues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements the {@code GameView} through the {@code Serializable} interface.
 * All the {@code Player}s always access only the implementation of the {@code Game}s various views,
 * and are sensible to any inherent modification.
 * Also, the class contains a series of getters to access their images and colors
 * and a series of other related, relevant, informations.
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Player
 */
public class GameView implements Serializable {
    private final int numberOfPlayers;
    private final GameState gameState;
    private final int activePlayerIndex;
    private final List<PlayerView> players;
    private final List<TileView> bag;
    private final BoardView board;
    private final List<CommonGoalView> commonGoals;

    /**
     * Class constructor.
     * Used to associate the representation of the {@code Game} in the {@code GameView}
     * with the linked logic in the {@code gameModel} (passed as parameter).
     * Also, sets the bag and the {@code Board} for the game.
     *
     * @param gameModel the model of the {@code Game}.
     * @see Game
     * @see it.polimi.ingsw.model.Board
     */
    public GameView(Game gameModel) {
        if (gameModel == null) {
            throw new IllegalArgumentException();
        }
        this.gameState = gameModel.getGameState();
        this.players = new ArrayList<>();
        this.bag = new ArrayList<>();
        this.commonGoals = new ArrayList<>();

        this.numberOfPlayers = gameModel.getNumberOfPlayersToStartGame();
        this.activePlayerIndex = gameModel.getActivePlayerIndex();
        for (Player player : gameModel.getPlayers()) {
            this.players.add(new PlayerView(player));
        }
        for (Tile tile : gameModel.getBag()) {
            this.bag.add(new TileView(tile));
        }
        this.board = new BoardView(gameModel.getBoard());

        for (CommonGoal commonGoal : gameModel.getCommonGoals()) {
            this.commonGoals.add(commonGoal.copyImmutable());
        }
    }

    /**
     * Gets a {@code Player} using his nickname as reference.
     *
     * @param nickname the player's nickname.
     * @return the View for the player, based on the nickname received as parameter.
     * @see Player
     */
    public PlayerView getPlayerViewFromNickname(String nickname) {
        return this.players.stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }

    /**
     * Used to retrieve the present state of the {@code Game}.
     *
     * @return the game's state.
     * @see Game
     */
    public GameState getGameState() {
        return this.gameState;
    }

    /*
     * TODO
     */
    public boolean isPlayerInGame(String nickname) {
        return this.players.stream().anyMatch(player -> player.getNickname().equals(nickname));
    }

    /**
     * Gets the number of active {@code Player}s in the current {@code Game},
     *
     * @return the number of players participating the {@code Game}.
     * @see Game
     * @see Player
     */
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    /**
     * Gets the number of the active {@code Player}.
     *
     * @return {@code activePlayerIndex}, the index of the current player.
     * @see Player
     */
    public int getActivePlayerIndex() {
        return this.activePlayerIndex;
    }

    /**
     * Gets the game's {@code Player}s.
     *
     * @return the list of players in the {@code Game} linked to the current GameView.
     * @see Game
     * @see Player
     */
    public List<PlayerView> getPlayers() {
        return this.players;
    }

    /**
     * A getter used to return the "bag" ({@code List<Tile>} of the Tiles
     * available to the active {@code Player}s at the start of the {@code Game}, before the shuffle.
     *
     * @return the "bag" of tiles to be shuffled.
     * @see Player
     * @see Tile
     * @see Game
     */
    public List<TileView> getBag() {
        return this.bag;
    }

    /**
     * Gets the {@code Board}'s view.
     *
     * @return the view of the Board.
     * @see it.polimi.ingsw.model.Board
     */
    public BoardView getBoard() {
        return this.board;
    }

    /**
     * Gets the views of all the common goals in the {@code Game}.
     *
     * @return the list of the common goals views in the current GameView.
     * @see CommonGoal
     * @see Game
     */
    public List<CommonGoalView> getCommonGoals() {
        return this.commonGoals;
    }

    public boolean isPaused() {
        return this.connectedPlayers().size() == OptionsValues.MIN_PLAYERS_TO_GO_ON_PAUSE;
    }

    public List<PlayerView> connectedPlayers() {
        return this.players.stream()
                .filter(PlayerView::isConnected)
                .collect(Collectors.toList());
    }
}
