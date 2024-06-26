package it.polimi.ingsw.network.socketMiddleware;

/**
 * Enumeration of all the possible types of commands/exceptions.
 */
public enum CommandType {
    ADD_PLAYER,
    CHANGE_TURN,
    CHOOSE_NUMBER_OF_PLAYER,
    DISCONNECT_PLAYER,
    INSERT_USER_INPUT,
    REGISTER,
    SEND_BROADCAST_MESSAGE,
    SEND_PRIVATE_MESSAGE,
    SEND_PING_TO_SERVER,
    START_GAME,
    SEND_PING_TO_CLIENT,
    SEND_UPDATED_MODEL,
    EXCEPTION,
    TRY_TO_RESUME_GAME,
    RESTORE_STORED_GAME
}
