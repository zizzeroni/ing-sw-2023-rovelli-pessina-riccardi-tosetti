package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.view.GameView;

public interface UI {
    public void showNewTurnIntro();

    public void registerListener(ViewListener listener);

    public void removeListener();

    public void setNickname(String nickname);

    public void modelModified(GameView modelUpdated);

    public void printException(GenericException exception);

    public void setAreThereStoredGamesForPlayer(boolean result);

    public void run();


}