package it.polimi.ingsw.utils;

import java.util.Scanner;

public class CommandReader extends Thread {

    private final Scanner scanner = new Scanner(System.in);
    public final static CommandQueue chatCommandQueue;
    public final static CommandQueue standardCommandQueue;
    
    static {
        chatCommandQueue = new CommandQueue();
        standardCommandQueue = new CommandQueue();
    }

    public CommandReader() {
        super();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String command = scanner.nextLine();

                switch (command.split(" ")[0]) {
                    case "/all", "/private", "/showChat" -> CommandReader.chatCommandQueue.addCommand(command);
                    default -> CommandReader.standardCommandQueue.addCommand(command);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}
