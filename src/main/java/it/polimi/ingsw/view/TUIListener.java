package it.polimi.ingsw.view;

public interface TUIListener {
    /**
     * Notify that no exception occurred during the nickname input
     */
    public void noExceptionOccured();

    /**
     * Used to start the thread that implements this interface
     */
    public void start();
}
