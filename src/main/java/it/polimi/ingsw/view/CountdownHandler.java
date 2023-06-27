package it.polimi.ingsw.view;

import it.polimi.ingsw.view.GUI.ThPrintCountdown;

/**
 * This class is used to represent the handler of the Player's countdown
 * when there is only a remaining connected client to the current game.
 *
 * @see it.polimi.ingsw.model.Player
 * @see it.polimi.ingsw.model.Game
 * @see it.polimi.ingsw.network.Client
 */
public class CountdownHandler extends Thread {
    private final GenericUILogic genericUILogic;

    /**
     * Class constructor.
     * It is used the generic ui's logic and other related values for the player's countdown.
     *
     * @param genericUILogic the logic associated to the client's countdown.
     *
     * @see GenericUILogic
     * @see it.polimi.ingsw.model.Player
     */
    public CountdownHandler(GenericUILogic genericUILogic) {
        super();
        this.genericUILogic = genericUILogic;
    }

    /**
     * Runs the game's countdown providing a way to handle the case of only a single client remaining connected to the current game.
     *
     * @see it.polimi.ingsw.network.Client
     */
    @Override
    public void run() {
        boolean firstTime = true;
        Thread printCountdownThread = new ThPrintCountdown(this.genericUILogic.getCountdown());
        while (!Thread.interrupted()) {
            synchronized (this.genericUILogic.getLockState()) {
                try {
                    genericUILogic.getLockState().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (this.genericUILogic.getState() == ClientGameState.WAITING_FOR_RESUME && this.genericUILogic.getModel().getPlayerViewFromNickname(this.genericUILogic.getNickname()).isConnected() && this.genericUILogic.getModel().isPaused()) {
                    if (firstTime) {
                        firstTime = false;
                        printCountdownThread.start();
                    } else {
                        if (this.genericUILogic.getState() != ClientGameState.GAME_ENDED) {
                            printCountdownThread.interrupt();
                            printCountdownThread = new ThPrintCountdown(this.genericUILogic.getCountdown());
                            firstTime = true;
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
