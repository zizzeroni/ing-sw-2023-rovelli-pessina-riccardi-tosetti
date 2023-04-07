package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ObservableType;

public enum Event implements ObservableType {
    USER_INPUT, REMOVE_TILES_BOARD, ADD_TILES_BOOKSHELF, RECAP_REQUEST, RECAP_SEND, SEND_MESSAGE, COMPUTE_SCORE;

    @Override
    public Event getEvent() {
        return this;
    }
}
