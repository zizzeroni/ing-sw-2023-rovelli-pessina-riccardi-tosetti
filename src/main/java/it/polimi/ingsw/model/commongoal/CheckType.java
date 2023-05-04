package it.polimi.ingsw.model.commongoal;

/*
Enumeration that defines the type of check must be done on common goal pattern.
*/

public enum CheckType {
    //Whether the tiles in the line must be of the same color.
    EQUALS,
    //Whether the tiles in the line must be different.
    DIFFERENT,
    //Whether the tiles in the line can be random.
    INDIFFERENT,
}
