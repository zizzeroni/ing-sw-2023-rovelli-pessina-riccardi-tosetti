package it.polimi.ingsw.utils;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CommandReader extends Thread {

    private final Scanner scanner = new Scanner(System.in);
    private static Queue<String> commands;

    static {
        commands = new ConcurrentLinkedQueue<>();
    }
    public CommandReader() {}

    @Override
    public void run() {
        while (true) {
            try {
                String command = scanner.next();
                CommandReader.commands.add(command);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    public static boolean removeOldestCommand() {
        return CommandReader.commands.poll() != null;
    }
    public static String getOldestCommand(){
        String command;
        if((command = CommandReader.commands.peek()) != null) {
            return command;
        }
        //-1 implies that there are no available commands;
        return "-1";

    }
    public synchronized Queue<String> getCommands() {
        return CommandReader.commands;
    }
    static synchronized void setCommands(Queue<String> commands) {
        CommandReader.commands = commands;
    }
}
