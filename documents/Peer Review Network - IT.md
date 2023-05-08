# Peer-Review 2: Network

Andrea Rovelli, Alessandro Pessina, Francesco Riccardi, Luca Tosetti

Gruppo 03

Valutazione del diagramma di sequenza e del diagramma UML delle classi del gruppo 43.

## Lati positivi

L'aspetto positivo che ci ha dato spunto per migliorare il nostro progetto è la gestione della risposta dal server al client relativa ai messaggi di risposta positiva o negativa. Infatti provvederemo ad aggiungere degli stati per la gestione della buona riuscita o meno dell'esecuzione delle azioni richieste dal client.

## Lati negativi

L'unico aspetto negativo che abbiamo riscontrato è la definizione di 3 messaggi diversi per la gestione delle tessere recuperate: basterebbe un messaggio generico MessagePickTiles che, tramite un array di coordinate (x,y), comunica le tessere da recuperare al server che, in combinazione con il client e in base alle regole del gioco, si occuperà di limitare il numero di tiles scelte: in questo caso non più di 3 coppie di coordinate per azione. 

## Confronto tra le architetture

La differenza principale tra le due architetture, che risiede nello scambio dei messaggi relativi alle azioni che sono state compiute dal client verso il server e viceversa, è il modo in cui vengono gestiti i messaggi.

Nel nostro caso usiamo l'interfaccia Command che definisce il metodo execute() che viene reimplementato da ogni tipo di messaggio in maniera diversa, sulla base del tipo di messaggio (ad esempio, la classe ChangeTurnCommand reimplementa il metodo execute(), delegando l'esecuzione del comando al controller, che a sua volta utilizzerà il comando assegnato alla classe ChangeTurnCommand, ovvero changeTurn()).

Nel caso del gruppo 43 invece è l'attributo messageType della classe astratta Message a comandare sul metodo che verrà eseguito all'interno del controller, richiedendo allo stesso di associare il messaggio ricevuto al metodo.