package model;

import utils.ObservableType;

public enum Event implements ObservableType {
    USER_INPUT, NEW_TURN, RECAP_REQUEST, RECAP_SEND, SEND_MESSAGE, COMPUTE_SCORE;

    @Override
    public Event getEvent() {
        return this;
    }
}
