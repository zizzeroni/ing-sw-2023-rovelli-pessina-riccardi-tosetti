package it.polimi.ingsw.view;

import it.polimi.ingsw.ChatThread;
import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.exceptions.GenericException;
import javafx.application.Application;

/**
 * The abstract class used to represent the User Interface (UI).
 * It has two different implementations in the TextualUI and the GUI classes.
 *
 * @see it.polimi.ingsw.GUI.User
 * @see TextualUI
 * @see GUI
 */
public abstract class UI extends Application implements Runnable {
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
    public UI(GameView model, ViewListener controller, String nickname) {
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
    public UI(GameView model, ViewListener controller) {
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
    public UI(GameView model) {
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
    public UI() {
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
     * @see UI#lockState
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
     * @see UI
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
     * Displays the part of the CLI interacting with the user to ask the type of action
     * the player will enact after the method's call.
     * In order to do this it interacts with the commands queue.
     * The possible choices are: 'display the personal recap', tile's selection, 'send chat message', call disconnection.
     *
     * @return the player's choice.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.network.Client
     * @see it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer.DisconnectPlayerCommand
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.utils.CommandQueue
     * @see it.polimi.ingsw.utils.CommandReader
     */
    //Method in common with all UIs that must be implemented
    public abstract Choice askPlayer();

    /**
     * Displays a standard message to identify the starting of the next turn.
     * Calls the nickname of the active player and the shows the board's state.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     */
    //Method in common with all UIs that must be implemented
    public abstract void showNewTurnIntro();

    //ONLY IN TextualUI
    //Method in common with all UIs that must be implemented
    //public abstract void showPersonalRecap();

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
            case RESET_NEEDED -> this.setState(ClientGameState.GAME_ENDED);
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
    //ESEMPIO INTERAZIONE TESTUALE
    /*
        >>  ---NEW TURN---
        >>  Tocca a te player "nickname".
        >>  Stato della board attuale:
        >>  [ 0 0 0 0 0 0 0 0 0 ]
        >>  [ 0 0 0 B G 0 0 0 0 ]
        >>  [ 0 0 0 B W P 0 0 0 ]
        >>  [ 0 0 Y W G B G W 0 ]
        >>  [ 0 C Y Y C W P G 0 ]
        >>  [ 0 P C C B P Y 0 0 ]
        >>  [ 0 0 0 C W Y 0 0 0 ]
        >>  [ 0 0 0 0 B G 0 0 0 ]
        >>  [ 0 0 0 0 0 0 0 0 0 ]
        >>  Seleziona l'azione(Digita il numero associato all'azione):
        >>  1)Recap situazione personale
        >>  2)Scegli tessere
        >>  3)Invia messaggio tramite chat
        <<  1
        >>  Ecco il tuo recap:
        >>  Stato della tua bookshelf:
        >>  [ P P P 0 0 ]
        >>  [ W P P G 0 ]
        >>  [ B W B W 0 ]
        >>  [ C Y C Y 0 ]
        >>  [ C C G G G ]
        >>  [ C C C G G ]
        >>  Il tuo obiettivo personale:
        >>  [ P 0 B 0 0 ]
        >>  [ 0 0 0 0 G ]
        >>  [ 0 0 0 W 0 ]
        >>  [ 0 Y 0 0 0 ]
        >>  [ 0 0 0 0 0 ]
        >>  [ 0 0 C 0 0 ]
        >>  Obiettivi comuni completati: Obiettivo1:4, Obiettivo2: (Valore delle goalTile)
        >>  //Visualizzazione dei pattern degli obiettivi
        >>  Il tuo punteggio attuale: 28
        >>  Seleziona l'azione(Digita il numero associato all'azione):
        >>  1)Recap situazione partita
        >>  2)Scegli tessere
        <<  2
        >>  La situazione della board attuale:
        >>  [ 0 0 0 0 0 0 0 0 0 ]
        >>  [ 0 0 0 B G 0 0 0 0 ]
        >>  [ 0 0 0 B W P 0 0 0 ]
        >>  [ 0 0 Y W G B G W 0 ]
        >>  [ 0 C Y Y C W P G 0 ]
        >>  [ 0 P C C B P Y 0 0 ]
        >>  [ 0 0 0 C W Y 0 0 0 ]
        >>  [ 0 0 0 0 B G 0 0 0 ]
        >>  [ 0 0 0 0 0 0 0 0 0 ]
        >>  Inserisci le coordinate delle tessere che vuoi prendere (Digita STOP per fermarti)
        <<  4,8
        >>  OK!
        <<  4,7
        >>  OK!
        <<  4,6
        >>  Impossibile prendere la tessera (Ha tutti i lati occupati), riprovare
        <<  STOP
        >>  Lo stato della tua bookshelf:
        >>  [ P P P 0 0 ]
        >>  [ W P P G 0 ]
        >>  [ B W B W 0 ]
        >>  [ C Y C Y 0 ]
        >>  [ C C G G G ]
        >>  [ C C C G G ]
        >>  Scegliere la colonna in cui le si vuole inserire
        <<  8
        >>  Questa colonna non esiste, scegliene un'altra:
        <<  5
        >>  Digita l'ordine con cui vuoi inserire le tessere (1 indica la prima tessera scelta, 2 la seconda e 3 la terza)
        <<  2,1,3
        >>  Hai scelto solo 2 tessere! Reinserisci l'ordine
        <<  2,1
        ------------------OPPURE----------------------------
        <<  2,1,3
        >>  Hai scelto solo 2 tessere! Verrà mantenuto l'ordine della tessera 1 e 2
        ----------------------------------------------------
        >>  ---NEW TURN---
        >>  Tocca a te player "nickname"
        >>  ...

     */
}
