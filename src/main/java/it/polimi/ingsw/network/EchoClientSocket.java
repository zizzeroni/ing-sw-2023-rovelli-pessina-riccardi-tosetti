package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClientSocket {
    private Socket socket;
    private String ip;
    private final int port = 1337;

    public EchoClientSocket(String ip) throws IOException {
        this.socket = new Socket(ip, port);
        this.ip = ip;
    }

    public static void main(String[] args) throws IOException {
        EchoClientSocket client = new EchoClientSocket("127.0.0.1");
        new Thread(client::readLoop).start();
        new Thread(client::writeLoop).start();
    }
    //Ciclo che legge tutto quello che viene inviato dal server nello stream del client
    public void readLoop() {
        try (Scanner socketIn = new Scanner(socket.getInputStream())) {
            System.out.println("Connection established");
            while (true) {
                String socketLine = socketIn.nextLine();
                System.out.println(socketLine);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Ciclo che scrive tutto quello che viene inviato dal server nello stream del client
    public void writeLoop() {
        try (
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
                Scanner stdinput = new Scanner(System.in);
        ) {
            while (true) {
                String inputLine = stdinput.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
