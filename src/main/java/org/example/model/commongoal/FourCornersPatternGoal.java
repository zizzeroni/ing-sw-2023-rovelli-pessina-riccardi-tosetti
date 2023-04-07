package org.example.model.commongoal;


import org.example.model.Bookshelf;

public class FourCornersPatternGoal extends CommonGoal {
    public FourCornersPatternGoal() {
    }

    public FourCornersPatternGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID, patternRepetition, type);
    }

    public FourCornersPatternGoal(int imageID, int patternRepetition, CheckType type, int numberOfPlayers) {
        super(imageID, patternRepetition, type, numberOfPlayers);
    }

    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        return (bookshelf.getSingleTile(0,0)!=null && bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns()-1)!=null
                    && bookshelf.getSingleTile(bookshelf.getNumberOfRows()-1,0)!=null && bookshelf.getSingleTile(bookshelf.getNumberOfRows()-1, bookshelf.getNumberOfColumns()-1)!=null)

                        && (bookshelf.getSingleTile(0,0).getColor().equals(bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns()-1).getColor())
                            && bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns()-1).getColor().equals(bookshelf.getSingleTile(bookshelf.getNumberOfRows()-1,0).getColor())
                                && bookshelf.getSingleTile(bookshelf.getNumberOfRows()-1,0).getColor().equals(bookshelf.getSingleTile(bookshelf.getNumberOfRows()-1, bookshelf.getNumberOfColumns()-1).getColor())) ? 1 : 0;
    }
}
