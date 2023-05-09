package it.polimi.ingsw.model.tile;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public enum TileColor {
    GREEN, YELLOW, WHITE, BLUE, CYAN, PURPLE;

    @Override
    public String toString() {
        StringBuilder stringa = new StringBuilder();
        //stringa.append();
        if(this.equals(BLUE)){
            return stringa.append(ansi().fg(Ansi.Color.BLUE).a(this.name().charAt(0)).fg(Ansi.Color.DEFAULT)).toString();
            //return "\u001B[0;34m"+ this.name().charAt(0) +"DEFAULT";
        }
        if(this.equals(GREEN)){
            return stringa.append(ansi().fg(Ansi.Color.GREEN).a(this.name().charAt(0)).fg(Ansi.Color.DEFAULT)).toString();
        }
        if(this.equals(CYAN)){
            return stringa.append(ansi().fg(Ansi.Color.CYAN).a(this.name().charAt(0)).fg(Ansi.Color.DEFAULT)).toString();
        }
        if(this.equals(YELLOW)){
            return stringa.append(ansi().fg(Ansi.Color.YELLOW).a(this.name().charAt(0)).fg(Ansi.Color.DEFAULT)).toString();
        }
        if(this.equals(WHITE)){
            return stringa.append(ansi().fg(Ansi.Color.WHITE).a(this.name().charAt(0)).fg(Ansi.Color.DEFAULT)).toString();
        }
        if(this.equals(PURPLE)){
            return stringa.append(ansi().fg(Ansi.Color.MAGENTA).a(this.name().charAt(0)).fg(Ansi.Color.DEFAULT)).toString();
        }
        return this.name().charAt(0) + "";
    }
}