package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.view.GameView;

public abstract class UI implements Runnable {
    private GameView model;

    protected ViewListener controller;
    private String nicknameID;
    //Indicate the state of the game from client perspective
    private State state;
    //Lock associated with the "state" attribute. It's used by the UI in order to synchronize on the state value
    private final Object lockState = new Object();

    public UI(GameView model, ViewListener controller, String nicknameID) {
        this.model = model;
        this.controller = controller;
        this.nicknameID = nicknameID;
        this.state = State.WAITING_IN_LOBBY;
    }

    public UI(GameView model, ViewListener controller) {
        this.model = model;
        this.controller = controller;
        this.nicknameID = null;
        this.state = State.WAITING_IN_LOBBY;
    }

    public UI(GameView model) {
        this.model = model;
        this.controller = null;
        this.nicknameID = null;
        this.state = State.WAITING_IN_LOBBY;
    }

    public UI() {
        this.model = null;
        this.controller = null;
        this.state = State.WAITING_IN_LOBBY;
    }

    public Object getLockState() {
        return lockState;
    }

    public State getState() {
        synchronized (lockState) {
            return state;
        }
    }

    public void setState(State state) {
        synchronized (lockState) {
            this.state = state;
            lockState.notifyAll();
        }
    }

    public String getNicknameID() {
        return nicknameID;
    }

    public void setNicknameID(String nicknameID) {
        this.nicknameID = nicknameID;
    }

    public GameView getModel() {
        return model;
    }

    public ViewListener getController() {
        return controller;
    }

    public void registerListener(ViewListener controller) {
        this.controller = controller;
    }

    public void removeListener() {
        this.controller = null;
    }

    //Method in common with all UIs that must be implemented
    public abstract Choice askPlayer();

    //Method in common with all UIs that must be implemented
    public abstract void showNewTurnIntro();

    //Method in common with all UIs that must be implemented
    public abstract void showPersonalRecap();

    //Method used to update the model by receiving a GameView object from the Server. Depending on the UI state and different model attributes
    //this method change the State of the game from the UI perspective
    public void modelModified(GameView game) {
        this.model = game;

        switch (this.model.getGameState()) {
            case IN_CREATION -> { /*Already in WAITING_IN_LOBBY*/}
            case ON_GOING, FINISHING -> {
                if (this.model.getPlayers().get(this.getModel().getActivePlayerIndex()).getNickname().equals(nicknameID)) {
                    this.setState(State.GAME_ONGOING);
                } else {
                    this.setState(State.WAITING_FOR_OTHER_PLAYER);
                }
            }
            case RESET_NEEDED -> this.setState(State.GAME_ENDED);
        }
        /*
        switch (state) {
            case WAITING_IN_LOBBY -> {
                if (this.model.getGameState()== GameState.ON_GOING) {
                    if (this.model.getPlayers().get(this.getModel().getActivePlayerIndex()).getNickname().equals(nicknameID)) {
                        this.setState(State.GAME_ONGOING);
                    } else {
                        this.setState(State.WAITING_FOR_OTHER_PLAYER);
                    }
                }
            }
            case WAITING_FOR_OTHER_PLAYER -> {
                if (this.model.getPlayers().get(this.getModel().getActivePlayerIndex()).getNickname().equals(nicknameID)) {
                    this.setState(State.GAME_ONGOING);
                }
            }
            case GAME_ONGOING -> {
                if (!this.model.getPlayers().get(this.getModel().getActivePlayerIndex()).getNickname().equals(nicknameID)) {
                    this.setState(State.WAITING_FOR_OTHER_PLAYER);
                }
            }


        }*/
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
