package it.polimi.ingsw.view;

import it.polimi.ingsw.ChatThread;
import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.exceptions.ExceptionType;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.OptionsValues;

/**
 * The class used to represent a generic User Interface (UI).
 * It contains common methods used by both GUI and TextualUI.
 *
 * @see TextualUI
 * @see it.polimi.ingsw.view.GUI.GraphicalUI
 */
public class GenericUILogic {
    private volatile GameView model;
    private ChatThread chat;
    protected ViewListener controller;
    private String nickname;
    //Indicate the state of the game from client perspective
    private GenericException exceptionToHandle;
    private ClientGameState clientGameState;

    /**
     * Lock associated with the "state" attribute. It's used by the UI in order to synchronize on the state value.
     */
    private final Object lockState = new Object();
    private final int countdown = OptionsValues.MILLISECOND_COUNTDOWN_VALUE / 1000;
    private boolean areThereStoredGamesForPlayer = false;

    /**
     * Class constructor.
     * Initialize the model and the controller to the associated values.
     *
     * @param model the given model.
     * @param controller the assigned controller.
     * @param nickname the UI's nickname.
     *
     * @see GameView
     * @see ViewListener
     */
    public GenericUILogic(GameView model, ViewListener controller, String nickname) {
        this.model = model;
        this.controller = controller;
        this.nickname = nickname;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
        this.initializeChatThread(this.controller, this.nickname, this.getModel());
    }

    /**
     * Class constructor.
     * Initialize the model and the controller to the associated values.
     *
     * @param model the given model.
     * @param controller the assigned controller.
     *
     * @see GameView
     * @see ViewListener
     */
    public GenericUILogic(GameView model, ViewListener controller) {
        this.model = model;
        this.controller = controller;
        this.nickname = null;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
    }

    /**
     * Class constructor.
     * Initialize the model and the controller to the associated values.
     *
     * @param model the given model.
     *
     * @see GameView
     */
    public GenericUILogic(GameView model) {
        this.model = model;
        this.controller = null;
        this.nickname = null;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
    }

    /**
     * Class constructor.
     * Initialize the model and the controller to the default values (null).
     */
    public GenericUILogic() {
        this.model = null;
        this.controller = null;
        this.nickname = null;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
    }

    /**
     * Gets the GenericException exception to be handled.
     *
     * @return the exception that has occurred.
     *
     * @see GenericException
     */
    public GenericException getExceptionToHandle() {
        return this.exceptionToHandle;
    }

    /**
     * Sets the exception to be handled using a GenericException.
     *
     * @param exceptionToHandle the occurred exception.
     *
     * @see GenericException
     */
    public void setExceptionToHandle(GenericException exceptionToHandle) {
        this.exceptionToHandle = exceptionToHandle;
    }

    /**
     * Gets a lock on the current client's game state.
     *
     * @return lockState
     *
     * @see GenericUILogic#lockState
     * @see ClientGameState
     */
    public Object getLockState() {
        return this.lockState;
    }


    /**
     * Gets the current Client's GameState
     *
     * @return the current game's state associated to the client.
     *
     * @see ClientGameState
     */
    public ClientGameState getState() {
        synchronized (this.lockState) {
            return this.clientGameState;
        }
    }

    /**
     * Sets the current Client's GameState.
     *
     * @param clientGameState the actual state of the game's client.
     *
     * @see ClientGameState
     */
    public void setState(ClientGameState clientGameState) {
        synchronized (this.lockState) {
            this.clientGameState = clientGameState;
            this.lockState.notifyAll();
        }
    }

    /**
     * Gets the UI's nickname.
     *
     * @return the UI's associated nickname.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Sets the UI's nickname.
     *
     * @param nickname the UI associated nickname.
     *
     * @see GenericUILogic
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
        this.chat.setNickname(nickname);
    }

    /**
     * Getter used to retrieve the game's model.
     * Its value is used for enabling different settings in other class's methods.
     *
     * @return the game's model.
     */
    public GameView getModel() {
        return this.model;
    }

    /**
     * Getter used the current controller.
     *
     * @return game's controller.
     *
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.controller.GameController
     */
    public ViewListener getController() {
        return this.controller;
    }

    /**
     * Registers the controller listener.
     *
     * @param controller the controller to be registered to.
     *
     * @see ViewListener
     * @see it.polimi.ingsw.controller.GameController
     */
    public void registerListener(ViewListener controller) {
        this.controller = controller;
    }

    public void removeListener() {
        this.controller = null;
    }

    /**
     * Getter used to get the countdown time after which the games end
     * if it remains only one player in the lobby
     *
     * @see CountdownHandler
     * @see it.polimi.ingsw.view.GUI.ThPrintCountdown
     */
    public int getCountdown() {
        return countdown;
    }

    /**
     * Used to print an exception when it is identified.
     *
     * @param clientErrorState the state associated to a client's error.
     *
     * @see it.polimi.ingsw.network.Client
     * @see GenericException
     */
    public void printException(GenericException clientErrorState) {
        this.exceptionToHandle = clientErrorState;
        if (this.exceptionToHandle.toEnum() == ExceptionType.EXCESS_OF_PLAYER_EXCEPTION) {
            this.setState(ClientGameState.GAME_ENDED);
        }
    }

    /**
     * Method used to update the model by receiving a GameView object from the Server. Depending on the UI state and different model attributes
     * this method changes the State of the game from the UI perspective.
     *
     * @param game the current game.
     *
     * @see it.polimi.ingsw.model.Game
     * @see GameView
     * @see it.polimi.ingsw.network.Server
     */
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
     * Initialize the chat's thread.
     * Moreover, this method sets the GameView.
     * The game view is not set in the constructor because we need the value passed as reference instead of the real value.
     *
     * @param controller is the GameController.
     * @param nickname the player's (client's) nickname.
     * @param model the model used to set the GameView.
     *
     * @see it.polimi.ingsw.controller.GameController
     * @see it.polimi.ingsw.model.Game
     * @see GameView
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
