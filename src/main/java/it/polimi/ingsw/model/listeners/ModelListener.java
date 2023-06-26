package it.polimi.ingsw.model.listeners;

public interface ModelListener extends GameListener, BookshelfListener, BoardListener, PlayerListener {
    //TODO: Chiedere a rovo se implementare il listener del modello oppure usare i GameListener attraverso il metodo setGameState risettando allo stesso stato precedente il modello
    //public void gameRestored();
}
