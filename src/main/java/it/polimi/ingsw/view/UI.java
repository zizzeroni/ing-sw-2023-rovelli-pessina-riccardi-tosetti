package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ControllerListener;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.ModelViewListener;
import it.polimi.ingsw.model.view.GameView;

public abstract class UI implements Runnable, ModelViewListener {
    private GameView model;
    protected ControllerListener controller;

    public UI(GameView model, ControllerListener controller) {
        this.model = model;
        this.controller = controller;
    }

    public UI(GameView model) {
        this.model = model;
        this.controller = null;
    }

    public UI() {
        this.model = null;
        this.controller = null;
    }

    public GameView getModel() {
        return model;
    }

    public void setModel(GameView model) {
        this.model = model;
    }

    public ControllerListener getController() {
        return controller;
    }

    public void registerListener(GameController controller) {
        this.controller = controller;
    }

    public void removeListener() {
        this.controller = null;
    }

    public abstract Choice askPlayer();

    public abstract void showNewTurnIntro();

    public abstract void showPersonalRecap();

    @Override
    public void modelModified(GameView game) {
        this.model = game;
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
