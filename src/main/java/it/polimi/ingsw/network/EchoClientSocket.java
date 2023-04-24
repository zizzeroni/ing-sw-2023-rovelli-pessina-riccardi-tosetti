package it.polimi.ingsw.network;

import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.TextualUI;
import it.polimi.ingsw.view.UI;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

//REMINDER: This class is not used anymore
public class EchoClientSocket {
    private Socket socket;
    private String ip;
    private final int port = 1337;
    private static String nome;
    private static GameView gameView;

    public EchoClientSocket(String ip, String nome) throws IOException {
        this.socket = new Socket(ip, port);
        this.ip = ip;
        this.nome = nome;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci il nickname che vuoi assegnato");
        nome = in.next();
        EchoClientSocket client = new EchoClientSocket("127.0.0.1", nome);

        //Mettere in attesa di una GameView

        //Attesa della ricezione di una GameView da parte del server

//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
//        ObjectInputStream inputStream = new ObjectInputStream(is);
//        gameView = (GameView) inputStream.readObject();

        new Thread(client::readLoop).start();
        new Thread(client::writeLoop).start();

        String scelta;
        System.out.println("Quale interfaccia vuoi usare? TextualUI o GUI");
        scelta = in.next();
        while(!scelta.equals("TextualUI") && !scelta.equals("GUI")){
            System.out.println("Scelta ERRATA \n Quale interfaccia vuoi usare? TextualUI o GUI");
            scelta = in.next();
        }
        if(scelta.equals("TextualUI")){

            //Passare la GameView Ricevuta precedentemente

            TextualUI textualUI = new TextualUI(gameView);
            new Thread(textualUI).start();

        }else{
            //GUI
        }
        in.close();
    }

    //Ciclo che legge tutto quello che viene inviato dal server nello stream del client
    public void readLoop() {
        try (Scanner socketIn = new Scanner(socket.getInputStream())) {

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
