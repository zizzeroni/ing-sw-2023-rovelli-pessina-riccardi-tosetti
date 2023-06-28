package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.GUI.UI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class indicates the client's implementation.
 * It is an extension of the UnicastRemoteObject and also a ModelListener's implementation.
 * It contains methods that implements all the client's functionalities described in
 * the client's interface class (and methods used for forwarding notifications from view to the server, ...).
 *
 * @see Client
 * @see ModelListener
 * @see UnicastRemoteObject
 */
public class ClientImpl extends UnicastRemoteObject implements Client, ViewListener, Runnable {
    private final Server serverConnectedTo;
    private final UI view;

    /**
     * Class constructor.
     * initialize the client's server, client's view and nickname to the given values.
     * Registers the view's listener.
     *
     * @param server the current server.
     * @param view the UI's view.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Server
     * @see UI
     * @see Player#getNickname()
     */
    public ClientImpl(Server server, UI view) throws RemoteException {
        super();
        this.serverConnectedTo = server;
        this.view = view;
        server.register(this, null);
        view.registerListener(this);
    }

    /**
     * Class constructor.
     * initialize the client's server, client's view and nickname to the given values.
     * Registers the view's listener.
     *
     * @param server the current server.
     * @param view the UI's view.
     * @param nickname the client's (player's) nickname.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Server
     * @see UI
     * @see Player#getNickname()
     */
    public ClientImpl(Server server, UI view, String nickname) throws RemoteException {
        super();
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNickname(nickname);
        server.register(this, nickname);
        view.registerListener(this);
    }

    /**
     * Class constructor.
     * initialize the client's server, client's view and nickname to the given values.
     * Registers the view's listener.
     *
     * @param server the current server.
     * @param view the UI's view.
     * @param nickname the client's (player's) nickname.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Server
     * @see UI
     * @see Player#getNickname()
     */
    public ClientImpl(Server server, UI view, String nickname, int port) throws RemoteException {
        super(port);
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNickname(nickname);
        server.register(this, nickname);
        view.registerListener(this);
    }

    /**
     * Class constructor.
     * initialize the client's server, client's view and nickname to the given values.
     * initialize the server's ip and port to the given values.
     * Registers the view's listener.
     *
     * @param port the server's port number.
     * @param csf the client socket factory employed for the RMI.
     * @param ssf the server socket factory employed for the RMI.
     * @param server the current server.
     * @param view the UI's view.
     * @param nickname the client's (player's) nickname.
     *
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Server
     * @see RMIClientSocketFactory
     * @see RMIServerSocketFactory
     * @see Server
     * @see UI
     * @see Player#getNickname()
     */
    public ClientImpl(Server server, UI view, String nickname, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNickname(nickname);
        server.register(this, nickname);
        view.registerListener(this);
    }

    /**
     * Forwards to the view the updates received from the server.
     *
     * @param modelUpdated contains the model updates.
     * @throws RemoteException if a communication error occurs.
     */
    //Update coming from the server, I forward it to the view
    @Override
    public synchronized void updateModelView(GameView modelUpdated) throws RemoteException {
        this.view.modelModified(modelUpdated);
    }

    /**
     * Receives the ping from server.
     *
     * @throws RemoteException if a communication error occurs.
     */
    @Override
    public synchronized void ping() throws RemoteException {
        //Receiving ping from server... do nothing.
    }

    /**
     * Receives the generic type exception.
     *
     * @param exception the GENERIC except being received.
     * @throws RemoteException if a communication error occurs.
     */
    @Override
    public synchronized void receiveException(GenericException exception) throws RemoteException {
        this.view.printException(exception);
    }

    /*
     * TODO
     */
    @Override
    public void setAreThereStoredGamesForPlayer(boolean result) throws RemoteException {
        this.view.setAreThereStoredGamesForPlayer(result);
    }
    
    /**
     * Allows to transmit the information about turns management to the view.
     */
    @Override
    public void changeTurn() {
        try {
            this.serverConnectedTo.changeTurn();
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"changeTurn()\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    /**
     * Allows to transmit the information about the choices of the player.
     *
     * @param playerChoice the choice made by the player.
     */
    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        try {
            this.serverConnectedTo.insertUserInputIntoModel(playerChoice);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"insertUserInputIntoModel(Choice)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    /**
     * Allows to send a message to a specified player (receiver)
     * in the game's lobby while he is connected.
     *
     * @param receiver the receiver of the private message.
     * @param sender   the sender of the broadcast {@code Message}.
     * @param content  the text of the message.
     */
    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        try {
            this.serverConnectedTo.sendPrivateMessage(receiver, sender, content);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"sendPrivateMessage(String,String,String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    /**
     * Allows to broadcast a message to all the active players
     * in the game's lobby while they're connected.
     *
     * @param sender  the sender of the broadcast {@code Message}.
     * @param content the text of the message.
     */
    @Override
    public void sendBroadcastMessage(String sender, String content) {
        try {
            this.serverConnectedTo.sendBroadcastMessage(sender, content);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"sendBroadcastMessage(String,String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    /**
     * Signals the adding of a player to the current game.
     *
     * @param nickname the nickname of the {@code Player}.
     *
     * @see Player
     * @see Game
     */
    @Override
    public void addPlayer(String nickname) {
        try {
            this.serverConnectedTo.addPlayer(this, nickname);
            this.serverConnectedTo.tryToResumeGame();
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"addPlayer(String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    /**
     * Communicates the choice of the players number.
     *
     * @param chosenNumberOfPlayers the number of players joining the {@code Game}.
     *
     * @see Player
     * @see Game
     */
    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        try {
            this.serverConnectedTo.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server: " + this.serverConnectedTo + ", error caused by \"chooseNumberOfPlayerInTheGame(int)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    /**
     * Updates the server game's state to signal that the game has started.
     *
     * @see Game
     * @see ServerImpl
     * @see Server
     */
    @Override
    public void startGame() {
        try {
            this.serverConnectedTo.startGame();
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server: " + this.serverConnectedTo + ", error caused by \"startGame()\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    /** Signals the disconnection of the selected {@code Player} from the current game to the server
     * by changing his connectivity state.
     * (only possible when the {@code Game} has already started).
     *
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     *
     * @see Player
     * @see Server
     * @see Game
     * @see Player#setConnected(boolean)
     */
    @Override
    public void disconnectPlayer(String nickname) {
        try {
            this.serverConnectedTo.disconnectPlayer(nickname);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server: " + this.serverConnectedTo + ", error caused by \"disconnectPlayer(String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void restoreGameForPlayer(String nickname) {
        try {
            this.serverConnectedTo.restoreGameForPlayer(nickname);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server: " + this.serverConnectedTo + ", error caused by \"restoreGameForPlayer(String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void areThereStoredGamesForPlayer(String nickname) {
        try {
            this.serverConnectedTo.areThereStoredGamesForPlayer(nickname);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server: " + this.serverConnectedTo + ", error caused by \"areThereStoredGamesForPlayer(String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    /**
     * Consents to run the Client's implementation.
     * It basically waits to receive the nickname from the player's client and then
     * registers the client associated with the nickname received.
     *
     * @see Client
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void run() {
        this.view.run();
    }

}
