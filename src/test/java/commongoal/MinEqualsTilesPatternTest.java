package commongoal;

import model.commongoal.CheckType;
import model.commongoal.Direction;
import model.commongoal.MinEqualsTilesPattern;
import org.junit.*;
import org.junit.jupiter.api.BeforeAll;

public class MinEqualsTilesPatternTest {

    private MinEqualsTilesPattern objTest;

    @BeforeAll
    public void cleanGoal() {
        objTest=new MinEqualsTilesPattern("", 4, CheckType.DIFFERENT, Direction.HORIZONTAL, 2);

    }



}
