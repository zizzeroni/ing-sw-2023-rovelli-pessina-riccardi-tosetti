/* package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.GameController;
import java.io.*;

public class PersistentSaves {


    public class SaveGame {

        private SaveGame() {
            throw new IllegalStateException("Utility Class");
        }

        public static void saveGame(GameController gameManager) {
            PersistencyClass persistencyClass = new PersistencyClass(gameManager);

            try (FileOutputStream gameSaved = new FileOutputStream(new File("gameSaved"))) {
                ObjectOutputStream outGame = new ObjectOutputStream(gameSaved);



                outGame.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
} */
