# Peer-Review 2: Network

Andrea Rovelli, Alessandro Pessina, Francesco Riccardi, Luca Tosetti

Group 03

Evaluation of sequence and UML classes diagrams of group 43.

## Positives

The positive note that gave us inspiration to improve our project is the way group 43 has handled server's positive and negative response messages to the client. We will add states for the management of the success or failure of the actions' execution requested by the client.

## Negatives

The only downside we found is the use of 3 different messages for the management of the retrieved tiles: it would be enough a generic message MessagePickTiles that, through an array of coordinates (x,y), notifies the retrieved tiles to the server that, in combination with client and game's rules, will limit the number of choosable tiles: in this case not more than 3 pairs of coordinates per action. 

## Comparing architectures

The main difference between the architectures, which lies in the exchange of executed actions' messages by the client towards the server and viceversa, is how messages are handled.

In our case, we use the Command interface that defines the method execute()  which is reimplemented by every type of message in a different way, based on the type of message (for example, the ChangeTurnCommand class reimplements the execute() method, entrusting the command's execution to the controller, which in turn will use the class' assigned command, changeTurn() for the sake of our example).

In group 43 case instead it's the Message abstract class' attribute messageType in charge of deciding which method will be executed in the controller, requiring the controller to pair received message and method.