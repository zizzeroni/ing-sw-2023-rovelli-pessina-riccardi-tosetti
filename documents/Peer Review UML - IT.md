# Peer-Review 1: UML

Andrea Rovelli, Alessandro Pessina, Francesco Riccardi, Luca Tosetti

Gruppo 03

Valutazione del diagramma UML delle classi del gruppo 43.

## Lati positivi

Una nota positiva è data dalla scomposizione dettagliata delle varie classi, il che permette un controllo più preciso di ciò che si vuole fare. Anche in un'ottica di implementazione futura di nuove funzioni, ciò permette di avere moduli estendibili e integrabili fra di loro.

## Lati negativi

Dall'UML non risulta un modo per comprendere quali obiettivi comuni siano già stati completati dai giocatori: è presente un gruppo di metodi per aggiornare il punteggio, ma se un player ha già completato un obiettivo comune, potrebbe essergli assegnato un'altra tessera punteggio per lo stesso obiettivo comune.

Nella classe Game è presente l'attributo ended, che però risulta ridondante in quanto la combinazione a fine turno di EndGameToken.isTaken() e giocatore attivo == giocatore ad aver iniziato è sufficiente a ricavare lo stesso dato. Non è inoltre chiaro come venga stabilito chi sia il giocatore attivo in un determinato turno.

Nella classe ItemTile non è presente un attributo che permetta di identificare l'immagine associata alla singola tessera, il che causa l'ottenimento di viste diverse per i singoli giocatori.

Nella classe Player viene effettuato il controllo sul completamento dell'obiettivo personale, ma avendo a disposizione la classe PersonalGoalCard avrebbe più senso implementarlo al suo interno.

Nella classe ShelfSlot (o BoardSlot) è presente l'attributo empty (o Full) per indicare se la cella è vuota o meno, ma basterebbe gestire il valore null all'interno dell'attributo Tile (o Item), oppure controllare l'assenza di tipo assegnato tramite Tile.getTileType() (o Tile.getTileType()), seguendo l'implementazione.

## Confronto tra le architetture

Una scelta interessante è quella di utilizzare un'attributo per capire se una cella sia utilizzabile o meno, il che risulta in una struttura più chiara e pulita rispetto alla nostra combinazione di cella non presente e cella presente, ma senza colore.
