package worldOfZuul.domain.tiles.forests;

public class OakForest extends AbstractForest {
    // static attributes


    private final static int maxPop = 100;
    private final static int saplingTurnsToGrow = 4;
    private final static int treePrice = 8;
    private final static int saplingPrice = 6;
    private final static int treeEcoValue = 25;

    private final static int minStartingTreePop = 20;
    private final static int maxStartingTreePop = 40;

    public OakForest() {
        super(maxPop, saplingTurnsToGrow, treePrice, saplingPrice,
                treeEcoValue, minStartingTreePop, maxStartingTreePop);
    }
}
