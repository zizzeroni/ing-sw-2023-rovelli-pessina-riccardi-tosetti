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

        int temp=0; //Gruppo eliminato, servirà dopo

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
            int contare_sotto = 1;
            while ((r >= 0) && support_matrix[r][c] != 1) {
                System.out.println("Sto guardando alla tile in posizione--> riga: " + r + ", colonna: " + c);
                //Se l'elemento non è già stato visitato
                if (support_matrix[r][c] == 0) {
                    support_matrix[r][c] = gruppo;
                    //Deve far parte di un nuovo gruppo

                    TileColor currentTileColor = B.getSingleTile(r, c).getColor();

                    System.out.println("Creo il gruppo" + gruppo + " di colore" + currentTileColor + "  in posizione " + r + " ," + c);

                    if ((r + 1 < 6) && (currentTileColor.equals(B.getSingleTile(r + 1, c) != null ? B.getSingleTile(r + 1, c).getColor() : null)) && (support_matrix[r + 1][c] == 0)) {
                        support_matrix[r + 1][c] = gruppo;

                        System.out.println(B.getSingleTile(r + 1, c).getColor() + " aggiunto al gruppo: " + gruppo + " posizione--> riga: " + (r+1) + ", colonna: " + c);
                    }/*else if((r + 1 < 6) && (currentTileColor.equals(B.getSingleTile(r + 1, c) != null ? B.getSingleTile(r + 1, c).getColor() : null)) && (support_matrix[r + 1][c] != 0) && (support_matrix[r + 1][c] != 1)){
                        //Sono dello stesso colore ma di due gruppi diversi ==> cambio il gruppo dell'ultimo creato
                        support_matrix[r][c]=support_matrix[r + 1][c];

                        System.out.println("Riassegno alla tile in posizione " + r + ", " + c + " Il gruppo: " + support_matrix[r+1][c]);
                    }*/


                    if ((c != 0) && (currentTileColor.equals(B.getSingleTile(r, c - 1) != null ? B.getSingleTile(r, c - 1).getColor() : null)) && (support_matrix[r][c - 1] == 0)) {
                        support_matrix[r][c - 1] = gruppo;

                        System.out.println(B.getSingleTile(r, c-1).getColor() + " aggiunto al gruppo: " + gruppo + " posizione--> riga: " + r + ", colonna: " + (c-1));
                    }/*else if((c != 0) && (currentTileColor.equals(B.getSingleTile(r, c - 1) != null ? B.getSingleTile(r, c - 1).getColor() : null)) && (support_matrix[r][c - 1] != 0) && (support_matrix[r][c - 1] != 1)){
                        //Sono dello stesso colore ma di due gruppi diversi ==> cambio il gruppo dell'ultimo creato
                        support_matrix[r][c]=support_matrix[r][c-1];
                        System.out.println("Riassegno alla tile in posizione " + r + ", " + c + " Il gruppo: " + support_matrix[r][c-1]);
                    }*/
                    if ((c + 1 < 5) && (currentTileColor.equals(B.getSingleTile(r, c + 1) != null ? B.getSingleTile(r , c + 1).getColor() : null)) && (support_matrix[r][c + 1] == 0)) {
                        support_matrix[r][c + 1] = gruppo;

                        System.out.println(B.getSingleTile(r , c +1).getColor() + " aggiunto al gruppo: " + gruppo+ " posizione--> riga: " + r + ", colonna: " + (c+1));
                    }/*else if((c+1 <5) && (currentTileColor.equals(B.getSingleTile(r, c + 1) != null ? B.getSingleTile(r, c + 1).getColor() : null)) && (support_matrix[r][c + 1] != 0) && (support_matrix[r][c + 1] != 1)){
                        //Sono dello stesso colore ma di due gruppi diversi ==> cambio il gruppo dell'ultimo creato
                        support_matrix[r][c]=support_matrix[r][c+1];
                        System.out.println("Riassegno alla tile in posizione " + r + ", " + c + " Il gruppo: " + support_matrix[r][c+1]);
                    }*/

                    /*while((r != 0) && (currentTileColor.equals(B.getSingleTile(r - contare_sotto, c) != null ? B.getSingleTile(r -  contare_sotto, c).getColor() : null)) && (support_matrix[r -  contare_sotto][c] == 0) ){
                        support_matrix[r -  contare_sotto][c] = gruppo;

                        System.out.println(B.getSingleTile(r -  contare_sotto, c).getColor() + " aggiunto al gruppo: " + support_matrix[r][c]);
                        contare_sotto++;
                    }
                    contare_sotto=1;*/

                    if ((r != 0) && (currentTileColor.equals(B.getSingleTile(r - 1, c) != null ? B.getSingleTile(r - 1, c).getColor() : null)) && (support_matrix[r - 1][c] == 0)) {
                        support_matrix[r - 1][c] = gruppo;

                        System.out.println(B.getSingleTile(r - 1, c).getColor() + " aggiunto al gruppo: " + gruppo + " posizione--> riga: " + (r-1) + ", colonna: " + c);
                    }/*else if ((r != 0) && (currentTileColor.equals(B.getSingleTile(r - 1, c) != null ? B.getSingleTile(r - 1, c).getColor() : null)) && (support_matrix[r - 1][c] != 0) && (support_matrix[r - 1][c] != 1)) {
                        support_matrix[r][c] = support_matrix[r - 1][c];

                        System.out.println("Riassegno alla tile in posizione " + r + ", " + c + " Il gruppo: " + support_matrix[r-1][c]);
                    }*/
                        gruppo++;
                } else if (support_matrix[r][c] != 1) {
                    //Fa parte di un gruppo già creato

                    TileColor currentTileColor = B.getSingleTile(r, c).getColor();

                    if ((r + 1 < 6) && (currentTileColor.equals(B.getSingleTile(r + 1, c) != null ? B.getSingleTile(r + 1, c).getColor() : null)) && (support_matrix[r + 1][c] == 0)) {
                        support_matrix[r + 1][c] = support_matrix[r][c];

                        System.out.println(B.getSingleTile(r + 1, c).getColor() + " aggiunto al gruppo: " + support_matrix[r][c] + " posizione--> riga: " + (r+1) + ", colonna: " + c);

                    }else if(
                                    (r + 1 < 6) && support_matrix[r][c]==support_matrix[r+1][c] &&
                                    (currentTileColor.equals(B.getSingleTile(r + 1, c) != null ? B.getSingleTile(r + 1, c).getColor() : null)) &&
                                    (support_matrix[r + 1][c] != 0) && (support_matrix[r + 1][c] != 1)){

                        //Sono dello stesso colore ma di due gruppi diversi ==> cambio il gruppo dell'ultimo creato
                        temp = support_matrix[r+1][c];
                        support_matrix[r][c] = support_matrix[r+1][c];

                        System.out.println("Riassegno alla tile in posizione " + r + ", " + c + " dal gruppo: " + temp + " al gruppo: " + support_matrix[r+1][c] );
                    }


                    if ((c != 0) && (currentTileColor.equals(B.getSingleTile(r, c - 1) != null ? B.getSingleTile(r, c - 1).getColor() : null)) && (support_matrix[r][c - 1] == 0)) {
                        support_matrix[r][c - 1] = support_matrix[r][c];

                        System.out.println(B.getSingleTile(r, c -1).getColor() + " aggiunto al gruppo: " + support_matrix[r][c] + " posizione--> riga: " + r + ", colonna: " + (c-1));

                    }else if(

                                    (c != 0) && support_matrix[r][c]==support_matrix[r][c-1] &&
                                    (currentTileColor.equals(B.getSingleTile(r, c - 1) != null ? B.getSingleTile(r, c - 1).getColor() : null)) &&
                                    (support_matrix[r][c - 1] != 0) && (support_matrix[r][c - 1] != 1)){

                        //Sono dello stesso colore ma di due gruppi diversi ==> cambio il gruppo dell'ultimo creato
                        temp = support_matrix[r][c - 1];
                        support_matrix[r][c] = support_matrix[r][c - 1];

                        System.out.println("Riassegno alla tile in posizione " + r + ", " + c + " dal gruppo: " + temp + " al gruppo: " + support_matrix[r][c-1] );
                    }

                    if ((c + 1 < 5) && (currentTileColor.equals(B.getSingleTile(r, c + 1) != null ? B.getSingleTile(r, c + 1).getColor() : null)) && (support_matrix[r][c + 1] == 0)) {
                        support_matrix[r][c + 1] = support_matrix[r][c];

                        System.out.println(B.getSingleTile(r, c +1).getColor() + " aggiunto al gruppo: " + support_matrix[r][c] + " posizione--> riga: " + r + ", colonna: " + (c+1));

                    }else if(
                                    (c+1 <5) && support_matrix[r][c]==support_matrix[r][c+1] &&
                                    (currentTileColor.equals(B.getSingleTile(r, c + 1) != null ? B.getSingleTile(r, c + 1).getColor() : null)) &&
                                    (support_matrix[r][c + 1] != 0) && (support_matrix[r][c + 1] != 1)){

                        //Sono dello stesso colore ma di due gruppi diversi ==> cambio il gruppo dell'ultimo creato
                        temp = support_matrix[r][c+1];
                        support_matrix[r][c] = support_matrix[r][c+1];

                        System.out.println("Riassegno alla tile in posizione " + r + ", " + c + " dal gruppo: " + temp + " al gruppo: " + support_matrix[r][c+1] );
                    }
                    //Sistema

                    /*while((r-contare_sotto >= 0) && (currentTileColor.equals(B.getSingleTile(r - contare_sotto, c) != null ? B.getSingleTile(r -  contare_sotto, c).getColor() : null)) && (support_matrix[r -  contare_sotto][c] == 0) ){
                        support_matrix[r -  contare_sotto][c] = support_matrix[r][c];

                        System.out.println(B.getSingleTile(r -  contare_sotto, c).getColor() + " aggiunto al gruppo: " + support_matrix[r][c]);
                        contare_sotto++;
                    }
                    contare_sotto=1; */

                    if ((r != 0) && (currentTileColor.equals(B.getSingleTile(r - 1, c) != null ? B.getSingleTile(r - 1, c).getColor() : null)) && (support_matrix[r - 1][c] == 0)) {
                        support_matrix[r - 1][c] = support_matrix[r][c];

                        System.out.println(B.getSingleTile(r - 1, c).getColor() + " aggiunto al gruppo: " + support_matrix[r][c] + " posizione--> riga: " + (r-1) + ", colonna: " + c);

                    }else if (
                                    (r != 0) && support_matrix[r][c]==support_matrix[r-1][c] &&
                                    (currentTileColor.equals(B.getSingleTile(r - 1, c) != null ? B.getSingleTile(r - 1, c).getColor() : null)) &&
                                    (support_matrix[r - 1][c] != 0) && (support_matrix[r - 1][c] != 1)) {
                        temp = support_matrix[r - 1][c];
                        support_matrix[r][c] = support_matrix[r - 1][c];

                        System.out.println("Riassegno alla tile in posizione " + r + ", " + c + " dal gruppo: " + temp + " al gruppo: " + support_matrix[r-1][c] );
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
