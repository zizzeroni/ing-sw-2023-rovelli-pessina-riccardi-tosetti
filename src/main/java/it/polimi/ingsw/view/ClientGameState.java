package it.polimi.ingsw.view;

//Enum class used to keep tracking of the UI state during the game
public enum ClientGameState {
    WAITING_IN_LOBBY, GAME_ONGOING, WAITING_FOR_OTHER_PLAYER, GAME_ENDED, WAITING_FOR_RESUME, ABORTED_BY_EXCEPTION
}
