package it.polimi.ingsw.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandReader implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private static List<String> commands;

    static {
        commands = new ArrayList<>();
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

    public synchronized boolean removeOldestCommand() {
        if(CommandReader.commands.size() > 0) {
            CommandReader.commands.remove(0);
            return true;
        }
        return false;
    }
    public static synchronized String getOldestCommand(){
        if(CommandReader.commands.size() > 0) {
            return CommandReader.commands.get(0);
        }
        //-1 implies that there are no available commands;
        return "-1";

    }
    public synchronized List<String> getCommands() {
        return CommandReader.commands;
    }
    static synchronized void setCommands(List<String> commands) {
        CommandReader.commands = commands;
    }
}
