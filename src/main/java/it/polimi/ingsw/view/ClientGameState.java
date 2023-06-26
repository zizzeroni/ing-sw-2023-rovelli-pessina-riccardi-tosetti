package it.polimi.ingsw.view;

/**
 * Enums the class used to keep track of the UI state during the game.
 *
 * @see it.polimi.ingsw.model.Game
 */

public enum ClientGameState {
    WAITING_IN_LOBBY, GAME_ONGOING, WAITING_FOR_OTHER_PLAYER, GAME_ENDED, WAITING_FOR_RESUME, ABORTED_BY_EXCEPTION
}
