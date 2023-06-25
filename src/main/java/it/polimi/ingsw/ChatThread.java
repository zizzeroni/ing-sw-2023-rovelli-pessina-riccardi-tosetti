package it.polimi.ingsw;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Message;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.CommandReader;

import java.util.List;

/**
 *
 */
public class ChatThread extends Thread {

    private final ViewListener controller;
    private GameView gameView;
    private String nickname;

    public ChatThread() {
        this.gameView = null;
        this.controller = null;
        this.nickname = null;
    }

    public ChatThread(ViewListener controller, String nickname) {
        this.controller = controller;
        this.nickname = nickname;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String command = CommandReader.chatCommandQueue.waitAndGetFirstCommandAvailable();

                switch (command.split(" ")[0]) {
                    //use controller to call server
                    case "/all" -> {
                        String[] commandAndContent = command.split(" ", 2);
                        if (commandAndContent.length > 1) {
                            this.controller.sendBroadcastMessage(this.nickname, commandAndContent[1]);
                        } else {
                            System.err.println("Can't send broadcast messages without content!");
                        }
                    }
                    case "/private" -> {
                        String[] commandReceiverAndContent = command.split(" ", 2);
                        if (commandReceiverAndContent.length > 2) {
                            String receiver = commandReceiverAndContent[1];
                            String content = commandReceiverAndContent[2];
                            this.controller.sendPrivateMessage(receiver, this.nickname, content);
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
                    default ->
                            throw new Exception("Command not recognized: chat behaviour is handled only for broadcast and private messages");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

}