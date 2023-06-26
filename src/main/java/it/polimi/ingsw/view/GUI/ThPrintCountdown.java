package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.utils.OptionsValues;

import java.util.concurrent.atomic.AtomicInteger;

public class ThPrintCountdown extends Thread {
    private int countdown;

    public ThPrintCountdown(int countdown) {
        this.countdown = countdown;
    }

    @Override
    public void run() {
        AtomicInteger countdownAtomic = new AtomicInteger(countdown - 1);
        System.out.println("You are the last player in the game, " + (OptionsValues.MILLISECOND_COUNTDOWN_VALUE / 1000) + " seconds remaining to win the game...");
        System.out.print(countdown);
        for (; countdownAtomic.get() > 0; countdownAtomic.getAndDecrement()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("\nA player has rejoined, game resumed...");
                return;
            }
            System.out.print("," + countdownAtomic);
        }
        System.out.println();

    }
}

