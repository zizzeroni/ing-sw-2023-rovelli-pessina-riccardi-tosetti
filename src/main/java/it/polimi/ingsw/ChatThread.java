package it.polimi.ingsw;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Message;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.CommandReader;

import java.util.List;

public class ChatThread extends Thread {

    private final ViewListener controller;

    private GameView gameView;
    private final String nickname;

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
                System.out.println(this);
                String command = CommandReader.chatCommandQueue.waitAndGetFirstCommandAvailable();
                System.out.println("controller: " + this.controller);
                System.out.println("gameview: " + this.gameView);
                switch (command.split(" ")[0]) {
                    //use controller to call server
                    case "/all" -> {
                        String content = command.split(" ", 2)[1];
                        this.controller.sendBroadcastMessage(this.nickname, content);
                    }
                    case "/private" -> {
                        String receiver = command.split(" ", 3)[1];
                        String content = command.split(" ", 3)[2];
                        System.out.println("receiver: " + receiver + " content: " + content);
                        this.controller.sendPrivateMessage(receiver, this.nickname, content);
                    }
                    case "/showChat" -> {
                        List<Message> fullChat = this.gameView.getPlayerViewFromNickname(this.nickname).getChat();

                        for (Message message : fullChat.size() > 24 ? fullChat.subList(fullChat.size() - 24, fullChat.size()) : fullChat) {
                            System.out.println(message.toString());
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

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

}