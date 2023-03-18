package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class GoalPattern_1_3_4 extends CommonGoal{

    public int goalPattern(Bookshelf B) {

        //Start from the first tile in the first columns
        int r; //rows
        //int c=1; //columns

        int counter = 0;
        int gruppo = 2;

        //Matrice di appoggio che contiene gli elementi visitati della bookshelf
        int support_matrix[][] = new int[6][5];

        //Inizializzo la matrice con 1 nella posizione di ogni elmento non nullo della bookshelf
        //Per gli elementi nulli inserisco 0 (Gia visitati)
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (B.getSingleTile(i, j) == null) {
                    support_matrix[i][j] = 1;
                } else {
                    support_matrix[i][j] = 0;
                }
            }
        }

        //Le matrici partono dall'elemento 0 ==>	0-4 per le colonne
        //											0-5 per le righe

        //Questo ciclo passa ogni elemento 0 della matrice di supporto

        //Qua abbiamo i 5 cicli delle colonne
        for (int c = 0; c < 5; c++) {
            //Per ogni colonna rimandiamo il puntatore riga alla prima riga
            r = 5;

            //Passo ogni singolo elementi della matrice, non posso uscire se trovo un true perchè potrebbero esserci
            //elementi true con sopra di essi elementi false dato che vengono segnati i blocchi

            while ((r >= 0) && support_matrix[r][c] != 1) {

                //Se l'elemento non è già stato visitato
                if (support_matrix[r][c] == 0) {
                    support_matrix[r][c] = gruppo;
                    //Deve far parte di un nuovo gruppo

                    TileColor currentTileColor = B.getSingleTile(r, c).getColor();
                    
                    if ((r + 1 < 6) && (currentTileColor.equals(B.getSingleTile(r + 1, c) != null ? B.getSingleTile(r + 1, c).getColor() : null)) && (support_matrix[r + 1][c] == 0)) {
                        support_matrix[r + 1][c] = gruppo;
                    }
                    if ((c != 0) && (currentTileColor.equals(B.getSingleTile(r, c - 1) != null ? B.getSingleTile(r, c - 1).getColor() : null)) && (support_matrix[r][c - 1] == 0)) {
                        support_matrix[r][c - 1] = gruppo;
                    }
                    if ((c + 1 < 5) && (currentTileColor.equals(B.getSingleTile(r, c + 1) != null ? B.getSingleTile(r , c + 1).getColor() : null)) && (support_matrix[r][c + 1] == 0)) {
                        support_matrix[r][c + 1] = gruppo;
                    }

                    //Sistema
                    if ((r != 0) && (currentTileColor.equals(B.getSingleTile(r - 1, c) != null ? B.getSingleTile(r - 1, c).getColor() : null)) && (support_matrix[r - 1][c] == 0)) {
                        support_matrix[r - 1][c] = gruppo;
                    }


                    gruppo++;
                } else if (support_matrix[r][c] != 1) {
                    //Fa parte di un gruppo già creato


                    if ((r + 1 < 6) && ((B.getSingleTile(r, c).getColor()).equals(B.getSingleTile(r + 1, c))) && (support_matrix[r + 1][c] == 0)) {
                        support_matrix[r + 1][c] = support_matrix[r][c];
                    }
                    if ((c != 0) && ((B.getSingleTile(r, c).getColor()).equals(B.getSingleTile(r, c - 1))) && (support_matrix[r][c - 1] == 0)) {
                        support_matrix[r][c - 1] = support_matrix[r][c];
                    }
                    if ((c + 1 < 5) && ((B.getSingleTile(r, c).getColor()).equals(B.getSingleTile(r, c + 1))) && (support_matrix[r][c + 1] == 0)) {
                        support_matrix[r][c + 1] = support_matrix[r][c];
                    }
                    //Sistema
                    if ((r != 0) && ((B.getSingleTile(r, c).getColor()).equals(B.getSingleTile(r - 1, c))) && (support_matrix[r - 1][c] == 0)) {
                        support_matrix[r - 1][c] = support_matrix[r][c];
                    }

                }
                r--;
            }
        }

        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                System.out.println(support_matrix[i][j]);


        int h = 1;

        int tempV = 0;
        int tempO = 0;

        int counter_gruppo = 0;
        //Per ogni gruppo


        //Si potrebbero fare tutti con degli while evitando di fare troppe volte il ciclo
        //Il cocetto generale è che per ogni elemento di un gruppo, guardo il numero di elementi dello stesso gruppo
        //Sia superiori, che di fianco, e se ce ne dovessero essere almeno C consecutivi, allora il gruppo soddisfa la richiesta
        //E quindi devo aumentare il contatore di uno


        //[----][-
        //       -
        //       -
        //       -]
        //[--
        // --]
        //
        //[--]
        //[-
        // -]

        return 0;
    }


}
