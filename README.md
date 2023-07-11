# Progetto - IGSW (MyShelfie)

### Corso: PROVA FINALE (INGEGNERIA DEL SOFTWARE)
`Docente: Cugola Giampaolo` \
`A.A: 2022/2023` \
`Gruppo 03: Rovelli Andrea - Tosetti Luca - Riccardi Francesco - Pessina Alessandro` \
`Voto: 30/30` 

\
NOTA: My Shelfie è un gioco da tavolo sviluppato ed edito da Cranio Creations Srl. I contenuti grafici di questo progetto riconducibili al prodotto editoriale da tavolo sono utilizzati previa approvazione di Cranio Creations Srl a solo scopo didattico. È vietata la distribuzione, la copia o la riproduzione dei contenuti e immagini in qualsiasi forma al di fuori del progetto, così come la redistribuzione e la pubblicazione dei contenuti e immagini a fini diversi da quello sopracitato. È inoltre vietato l'utilizzo commerciale di suddetti contenuti.

# Feature implementate

| Requisiti                   | Voto  | Implementato |
|----------------------------------------|-------|--------------|
| Regole Semplificate + TUI + RMI o Socket | `18`  | ✓   |
| Regole Complete + TUI + RMI o Socket | `20`  | ✓            |
| Regole Complete + TUI + RMI o Socket + 1 FA | `22`  | ✓            |
| Regole Complete + TUI + GUI + RMI o Socket + 1 FA | `24`  | ✓            |
| Regole Complete + TUI + GUI + RMI + Socket + 1 FA | `27`  | ✓            |
| Regole Complete + TUI + GUI + RMI + Socket + 2 FA | `30`  | ✓            |
| Regole Complete + TUI + GUI + RMI + Socket + 3 FA | `30L` | ✗            |

Le funzionalità avanzate implementate sono:
 
 - **Resilienza alle disconnessioni**
 - **Persistenza**
 - **Chat** *

\
*Per quanto riguarda la funazionalità avanzata della Chat, siamo riusciti a implementarla
correttamente sia su TUI, che GUI. Tuttavia funziona solamente tramite l'uso di socket e
non RMI (il quale fa crashare il client utilizzatore ma, da nostri test, non incidendo
sull'andamento effettivo del game, semplicemente dopo un po' il giocatore viene disconnesso).\
Per tale motivo non l'abbiamo segnalata tra i requisiti completati.

# Copertura Test

![coverageModelController.png](https://github.com/zizzeroni/ing-sw-2023-rovelli-pessina-riccardi-tosetti/blob/main/documents/coverageModelController.png)

# Modalità di esecuzione Jar

Per l'esecuzione dei Jar è sufficiente entrare nella cartella in cui si trova il Jar e digitare:
`java -jar [nome del jar]`.\
Questo vale sia per il Jar del server che quello del client. \
Durante l'esecuzione del server verrà creata una serie di directories (`src\main\resources\storage`) nello stesso luogo in cui si trova il jar del server. Tutto ciò al fine di memorizzare due file json: `games.json` e `games-bkp.json`,
che conterranno i salvataggi dei game in caso di caduta del server.

# Ulteriori indicazioni

Per far in modo che i jar vengano eseguiti senza problemi e la connessione (in particolare RMI) funzioni:
- Consigliamo la disattivazione di qualsiasi firewall presente sul PC
- Consigliamo di disattivare tutte le schede di rete superflue (diverse da quella che si intende utilizzare per instaurare la connessione, in particolare quelle virtuali) 
,infatti il software procederà a prendere per ogni client una scheda di rete tra quelle attive e che non sia di loopback).

Per l'utilizzo della chat nella TUI, abbiamo predisposto una serie di comandi scrivibili da terminale per inviare e visualizzare i messaggi:
  - `/all [contenuto del messaggio]` per inviare a tutti un messaggio
  - `/private [nickname utente destinatario] [contenuto del messaggio]` per inviare un messaggio ad uno specifico player
  - `/showChat` per poter visualizzare la chat


