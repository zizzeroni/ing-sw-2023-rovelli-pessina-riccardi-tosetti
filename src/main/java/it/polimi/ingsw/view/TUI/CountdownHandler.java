package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.ClientGameState;
import it.polimi.ingsw.view.GenericUILogic;

/**
 * This class is used to handle the countdown occurring when there is only one player remaining connected to the current's game.
 *
 * @see Thread
 * @see it.polimi.ingsw.model.Player
 * @see it.polimi.ingsw.model.Game
 */
public class CountdownHandler extends Thread implements TUIListener {
    private final GenericUILogic genericUILogic;
    private boolean canCheckGamePaused;

    /**
     * Class constructor.
     * It is used the generic UI's logic and other related values for the player's countdown.
     *
     * @param genericUILogic used to call methods that implement logic in common with every user interface.
     * @see GenericUILogic
     * @see it.polimi.ingsw.model.Player
     */
    public CountdownHandler(GenericUILogic genericUILogic) {
        super();
        this.genericUILogic = genericUILogic;
    }

    /**
     * Handle the start and interrupt of the {@code ThPrintCountdown} according to {@code Game} state
     *
     * @see it.polimi.ingsw.network.Client
     */
    @Override
    public void run() {
        GameView model;
        boolean firstTime = true;
        Thread printCountdownThread = new ThPrintCountdown(this.genericUILogic.getCountdown());
        while (!Thread.interrupted()) {
            if (this.canCheckGamePaused) {
                synchronized (this.genericUILogic.getLockState()) {
                    try {
                        this.genericUILogic.getLockState().wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    model = this.genericUILogic.getModel();
                    if (this.genericUILogic.getState() == ClientGameState.WAITING_FOR_RESUME
                            && model.getPlayerViewFromNickname(this.genericUILogic.getNickname()).isConnected()
                            && model.isPaused()
                    ) {
                        if (firstTime) {
                            firstTime = false;
                            printCountdownThread.start();
                        } else {
                            if (this.genericUILogic.getState() != ClientGameState.GAME_ENDED) {
                                if (this.genericUILogic.getState() != ClientGameState.WAITING_FOR_RESUME) {
                                    printCountdownThread.interrupt();
                                    printCountdownThread = new ThPrintCountdown(this.genericUILogic.getCountdown());
                                    firstTime = true;
                                }
                            } else {
                                this.interrupt();
                            }
                        }
                    } else if (printCountdownThread.isAlive()) {
                        printCountdownThread.interrupt();
                        printCountdownThread = new ThPrintCountdown(this.genericUILogic.getCountdown());
                        firstTime = true;
                    }
                }
            }
        }
    }


    /**
     * Used to notify the countdown handler that he can start to check if the game is paused
     */
    @Override
    public void noExceptionOccured() {
        this.canCheckGamePaused = true;
    }
}
