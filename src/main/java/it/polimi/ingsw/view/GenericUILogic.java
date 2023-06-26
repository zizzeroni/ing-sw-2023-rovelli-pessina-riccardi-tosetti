package it.polimi.ingsw.view;

import it.polimi.ingsw.ChatThread;
import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.exceptions.ExceptionType;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.OptionsValues;

public class GenericUILogic {
    private volatile GameView model;
    private ChatThread chat;
    protected ViewListener controller;
    private String nickname;
    //Indicate the state of the game from client perspective
    private GenericException exceptionToHandle;
    private ClientGameState clientGameState;
    //Lock associated with the "state" attribute. It's used by the UI in order to synchronize on the state value
    private final Object lockState = new Object();
    private final int countdown = OptionsValues.MILLISECOND_COUNTDOWN_VALUE / 1000;
    private boolean areThereStoredGamesForPlayer = false;


    public GenericUILogic(GameView model, ViewListener controller, String nickname) {
        this.model = model;
        this.controller = controller;
        this.nickname = nickname;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
        this.initializeChatThread(this.controller, this.nickname, this.getModel());
    }

    public GenericUILogic(GameView model, ViewListener controller) {
        this.model = model;
        this.controller = controller;
        this.nickname = null;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
    }

    public GenericUILogic(GameView model) {
        this.model = model;
        this.controller = null;
        this.nickname = null;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
    }

    public GenericUILogic() {
        this.model = null;
        this.controller = null;
        this.nickname = null;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
    }

    public GenericException getExceptionToHandle() {
        return this.exceptionToHandle;
    }

    public void setExceptionToHandle(GenericException exceptionToHandle) {
        this.exceptionToHandle = exceptionToHandle;
    }

    public Object getLockState() {
        return this.lockState;
    }

    public ClientGameState getState() {
        synchronized (this.lockState) {
            return this.clientGameState;
        }
    }

    public void setState(ClientGameState clientGameState) {
        synchronized (this.lockState) {
            this.clientGameState = clientGameState;
            this.lockState.notifyAll();
        }
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        this.chat.setNickname(nickname);
    }

    public GameView getModel() {
        return this.model;
    }

    public ViewListener getController() {
        return this.controller;
    }

    public void registerListener(ViewListener controller) {
        this.controller = controller;
    }

    public void removeListener() {
        this.controller = null;
    }

    public int getCountdown() {
        return countdown;
    }

    public void printException(GenericException clientErrorState) {
        this.exceptionToHandle = clientErrorState;
        if (this.exceptionToHandle.toEnum() == ExceptionType.EXCESS_OF_PLAYER_EXCEPTION) {
            this.setState(ClientGameState.GAME_ENDED);
        }
    }

    //Method used to update the model by receiving a GameView object from the Server. Depending on the UI state and different model attributes
    //this method change the State of the game from the UI perspective
    public void modelModified(GameView game) {
        this.model = game;
        this.chat.setGameView(game);

        switch (this.model.getGameState()) {
            case IN_CREATION -> { /*Already in WAITING_IN_LOBBY*/}
            case ON_GOING, FINISHING -> {
                if (this.model.getPlayers().get(this.getModel().getActivePlayerIndex()).getNickname().equals(this.nickname)) {
                    this.setState(ClientGameState.GAME_ONGOING);
                } else {
                    this.setState(ClientGameState.WAITING_FOR_OTHER_PLAYER);
                }
            }
            case PAUSED -> {
                this.setState(ClientGameState.WAITING_FOR_RESUME);
            }
            case RESET_NEEDED -> {
                this.setState(ClientGameState.GAME_ENDED);
            }
        }
    }

    /**
     * Ini
     *
     * @param controller
     * @param nickname
     * @param model
     */
    public void initializeChatThread(ViewListener controller, String nickname, GameView model) {
        chat = new ChatThread(controller, nickname);
        //we do not set the game view in the constructor because we need the value passed as reference instead of value
        chat.setGameView(model);
        chat.start();
    }

    public void setAreThereStoredGamesForPlayer(boolean result) {
        this.areThereStoredGamesForPlayer = result;
    }

    public boolean areThereStoredGamesForPlayer() {
        return this.areThereStoredGamesForPlayer;
    }
}
