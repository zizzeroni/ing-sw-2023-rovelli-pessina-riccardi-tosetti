package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.view.TileView;

import java.util.List;

public interface ControllerListener {
    public void changeTurn();

    public void insertUserInputIntoModel(Choice playerChoice);

    public void sendPrivateMessage(Player receiver, Player sender, String content);

    public void sendBroadcastMessage(Player sender, String content);
}
