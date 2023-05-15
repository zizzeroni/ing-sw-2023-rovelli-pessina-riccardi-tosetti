/*package it.polimi.ingsw;

import it.polimi.ingsw.utils.CommandReader;

public class ChatThread extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                String command = CommandReader.chatCommandQueue.waitAndGetFirstCommandAvailable();
                switch (command.split(" ")[0]) {
                    //use controller to call server
                    /* case "/all" -> ;
                    case "/private" ->
                    default -> CommandReader.standardCommandQueue.addCommand(command);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }

}*/