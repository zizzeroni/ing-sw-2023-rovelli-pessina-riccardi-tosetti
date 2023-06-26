package it.polimi.ingsw.view;

import it.polimi.ingsw.view.GUI.ThPrintCountdown;

public class CountdownHandler extends Thread {
    private final GenericUILogic genericUILogic;

    public CountdownHandler(GenericUILogic genericUILogic) {
        super();
        this.genericUILogic = genericUILogic;
    }

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
