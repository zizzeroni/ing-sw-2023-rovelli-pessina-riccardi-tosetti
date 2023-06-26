package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.CreationState;
import it.polimi.ingsw.controller.FinishingState;
import it.polimi.ingsw.controller.OnGoingState;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Message;
import it.polimi.ingsw.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface represents the Game's server.
 * It contains a series of methods used by the clients to notify the Server of some client-related events.
 * Also contains methods for server management and server's events in game handling.
 *
 * @see Client
 * @see Server
 * @see ServerImpl
 * @see it.polimi.ingsw.model.Game
 * @see it.polimi.ingsw.model.Player
 */
public interface Server extends Remote {
    //Methods used by the clients to notify the Server of some events

    /**
     * Change the turn in the server's context.
     *
     * @see OnGoingState#changeTurn()
     */
    public void changeTurn() throws RemoteException;

    /**
     * Allows the {@code Player} communicating with the server
     * to insert {@code Tile}s, in a given order (contained in {@code Choice}) into the {@code Board} .
     *
     * @param playerChoice the choice made by the player.
     * @throws RemoteException
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     * @see Choice
     */
    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException;

    /**
     * This method is used to stream a message privately.
     * Only the specified receiver will be able to read the message
     * in any chat. It builds a new object message at each call, setting
     * the {@code nickname}s of the receiving {@code Player}s and its message type to {@code PRIVATE}.
     *
     * @param receiver the {@code Player} receiving the message.
     * @param sender the {@code Player} sending the message.
     * @param content the text of the message being sent.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    public void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException;

    /**
     * This method implementations allow to send
     * broadcast messages to all the {@code Player}s.
     *
     * @param sender the sender of the broadcast {@code Message}.
     * @param content the text of the message.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see it.polimi.ingsw.model.Player
     * @see Message
     */
    public void sendBroadcastMessage(String sender, String content) throws RemoteException;

    /**
     * This method is used to add a {@code Player} to the current {@code Game}
     * through the knowledge of the nickname he has chosen during game creation and the client
     * he has been assigned to.
     *
     *
     * @param client is the player's client
     * @param nickname is the reference for the name of the {@code Player} being added.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Client
     * @see Game
     * @see Player
     */
    public void addPlayer(Client client, String nickname) throws RemoteException;

    public void tryToResumeGame() throws RemoteException;

    /**
     * Method to implement the selection of the number of {@code Player}s for the {@code Game}.
     *
     * @param chosenNumberOfPlayers identifies the number of players present
     *                              in the lobby during the game creation.
     *
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Game
     * @see Game#getPlayers()
     * @see Player
     *
     */
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException;

    /**
     * Controls that all the necessary preparing has been done due to initiating the {@code Game}.
     *
     * @throws RemoteException called if a communication error occurs.
     *
     * @see CreationState#startGame()
     * @see FinishingState#startGame()
     * @see OnGoingState#startGame()
     */
    public void startGame() throws RemoteException;

    /** Disconnects the selected {@code Player} from the {@code Game}
     * by changing his connectivity state.
     * (only possible when the {@code Game} has already started).
     *
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Player
     * @see Game
     * @see Player#setConnected(boolean)
     */
    public void disconnectPlayer(String nickname) throws RemoteException;

    /**
     * Method used by the clients in order to register to a specific remote server.
     *
     * @param client   is the client registering to the server.
     * @param nickname the player's nickname related to the client.
     * @throws RemoteException called if a communication error occurs.
     * @throws RemoteException called if a communication error occurs.
     */
    public void register(Client client, String nickname) throws RemoteException;

    /*
     * TODO
     */
    public void restoreGameForPlayer(String nickname) throws RemoteException;

    /*
     * TODO
     */
    public void areThereStoredGamesForPlayer(String nickname) throws RemoteException;

    /**
     * Allows to ping the server.
     *
     * @throws RemoteException called if a communication error occurs.
     *
     * @throws RemoteException signals the occurrence of a communication error with the server.
     *
     * @see Server
     */
    public void ping() throws RemoteException;

}
