package it.polimi.ingsw.view;

import it.polimi.ingsw.ChatThread;
import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.exceptions.GenericException;
import javafx.application.Application;

public abstract class UI extends Application implements Runnable {
    private volatile GameView model;
    private ChatThread chat;
    protected ViewListener controller;
    private String nickname;
    //Indicate the state of the game from client perspective
    private GenericException exceptionToHandle;
    private ClientGameState clientGameState;
    //Lock associated with the "state" attribute. It's used by the UI in order to synchronize on the state value
    private final Object lockState = new Object();
    private final int countdown = 15;

    public UI(GameView model, ViewListener controller, String nickname) {
        this.model = model;
        this.controller = controller;
        this.nickname = nickname;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
        this.initializeChatThread(this.controller, this.nickname, this.getModel());
    }

    public UI(GameView model, ViewListener controller) {
        this.model = model;
        this.controller = controller;
        this.nickname = null;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
    }

    public UI(GameView model) {
        this.model = model;
        this.controller = null;
        this.nickname = null;
        this.clientGameState = ClientGameState.WAITING_IN_LOBBY;
        this.exceptionToHandle = null;
    }

    public UI() {
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

    //Method in common with all UIs that must be implemented
    public abstract Choice askPlayer();

    //Method in common with all UIs that must be implemented
    public abstract void showNewTurnIntro();

    //ONLY IN TextualUI
    //Method in common with all UIs that must be implemented
    //public abstract void showPersonalRecap();

    public void printException(GenericException clientErrorState) {
        this.exceptionToHandle = clientErrorState;
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
