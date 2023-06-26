package it.polimi.ingsw.model.listeners;

/**
 * An interface used to represent an object of type 'listener'.
 * In this case the listener registers itself to the {@code Model}.
 * When the view is subject to changes the listener permits the model to respond to them.
 */
public interface ModelListener extends GameListener, BookshelfListener, BoardListener, PlayerListener {
    //TODO: Chiedere a rovo se implementare il listener del modello oppure usare i GameListener attraverso il metodo setGameState risettando allo stesso stato precedente il modello
    //public void gameRestored();
}
