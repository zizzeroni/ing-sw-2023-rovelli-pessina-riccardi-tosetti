package it.polimi.ingsw.network;

import it.polimi.ingsw.model.Choice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    //Methods used by the clients to notify the Server of some events
    public void changeTurn() throws RemoteException;

    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException;

    public void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException;

    public void sendBroadcastMessage(String sender, String content) throws RemoteException;

    public void addPlayer(Client client, String nickname) throws RemoteException;

    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException;

    public void startGame() throws RemoteException;

    public void disconnectPlayer(String nickname) throws RemoteException;

    public void restoreGameForPlayer(String nickname) throws RemoteException;

    public void areThereStoredGamesForPlayer(String nickname) throws RemoteException;

    //Method used by the clients in order to register to a specific remote server
    public void register(Client client, String nickname) throws RemoteException;

    public void ping() throws RemoteException;

}
