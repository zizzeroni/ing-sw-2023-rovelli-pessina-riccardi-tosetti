package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.GameView;

public abstract class UI implements Runnable {
    public String getNicknameID() {
        return nicknameID;
    }

    public void setNicknameID(String nicknameID) {
        this.nicknameID = nicknameID;
    }

    public enum State {
        WAITING_IN_LOBBY, GAME_ONGOING, WAITING_FOR_OTHER_PLAYER
    }

    private State state;

    public Object getLock() {
        return lock;
    }

    private final Object lock = new Object();

    public State getState() {
        synchronized (lock) {
            return state;
        }

    }

    public void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }

    }

    private GameView model;
    protected ViewListener controller;
    private String nicknameID;

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

    public GameView getModel() {
        return model;
    }

    public void setModel(GameView model) {
        this.model = model;
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

    public abstract Choice askPlayer();

    public abstract void showNewTurnIntro();

    public abstract void showPersonalRecap();

    public void modelModified(GameView game) {
        this.model = game;
        /*if (this.model.getPlayers().size() >= this.model.getNumberOfPlayers()) {
            this.setState(State.WAITING_FOR_OTHER_PLAYER);
        }*/
        switch (state) {
            case WAITING_IN_LOBBY -> {
                if (this.model.isStarted()) {
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
        }

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
