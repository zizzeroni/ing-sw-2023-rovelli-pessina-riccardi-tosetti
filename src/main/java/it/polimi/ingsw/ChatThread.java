package it.polimi.ingsw;

import it.polimi.ingsw.model.Message;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.CommandReader;
import it.polimi.ingsw.view.TUI.TextualUI;
import it.polimi.ingsw.view.ViewListener;

import java.util.List;

/**
 * Class used to represent the Chat's thread, a thread class extension.
 *
 * @see Thread
 */
public class ChatThread extends Thread {

    private final ViewListener controller;
    private GameView gameView;
    private String nickname;

    /**
     * Class constructor.
     * Initialize class attributes to their default values.
     */
    public ChatThread() {
        this.gameView = null;
        this.controller = null;
        this.nickname = null;
    }

    /**
     * Class constructor.
     * Initialize the employed controller and client chat's nickname to the given values.
     *
     * @param controller the game's controller.
     * @param nickname   the player's nickname used in the chatroom.
     * @see it.polimi.ingsw.controller.GameController
     * @see ViewListener
     * @see it.polimi.ingsw.network.Client
     */
    public ChatThread(ViewListener controller, String nickname) {
        this.controller = controller;
        this.nickname = nickname;
    }

    /**
     * Allows to use the controller to call the server in order to show chat and stream a private or broadcast message,
     * based on the type of string entered in CLI (TextualUI) or the GUI's chat selection.
     *
     * @see it.polimi.ingsw.view.GUI
     * @see TextualUI
     * @see CommandReader
     */
    @Override
    public void run() {
        while (true) {
            try {
                String command = CommandReader.chatCommandQueue.waitAndGetFirstCommandAvailable();

                switch (command.split(" ")[0]) {
                    case "/all" -> {
                        String[] commandAndContent = command.split(" ", 2);
                        if (commandAndContent.length > 1) {
                            this.controller.sendBroadcastMessage(this.nickname, commandAndContent[1]);
                        } else {
                            System.err.println("Can't send broadcast messages without content!");
                        }
                    }
                    case "/private" -> {
                        String[] commandReceiverAndContent = command.split(" ", 3);
                        if (commandReceiverAndContent.length > 2) {
                            String receiver = commandReceiverAndContent[1];

                            if (this.gameView.isPlayerInGame(receiver)) {
                                String content = commandReceiverAndContent[2];
                                this.controller.sendPrivateMessage(receiver, this.nickname, content);
                            } else {
                                System.out.println("There are no players named " + receiver + " in this game");
                            }
                        } else if (commandReceiverAndContent.length > 1) {
                            System.err.println("Can't send private messages without content!");
                        } else {
                            System.err.println("Can't send private messages without specifying a receiver!");
                        }
                    }
                    case "/showChat" -> {
                        List<Message> fullChat = this.gameView.getPlayerViewFromNickname(this.nickname).getChat();

                        if (fullChat.size() != 0) {
                            for (Message message : fullChat.size() > 24 ? fullChat.subList(fullChat.size() - 24, fullChat.size()) : fullChat) {
                                System.out.println(message.toString());
                            }
                        } else {
                            System.out.println("No message found");
                        }
                    }
                    default -> throw new Exception("Chat command not recognized");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sets the chat's nickname.
     *
     * @param nickname the player's nickname associated with the chat's thread.
     * @see it.polimi.ingsw.model.Player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Sets the model used by the chat in order to take care of the logic behind
     * sending messages.
     *
     * @param gameView the game's view
     * @see it.polimi.ingsw.model.Player
     */
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

}