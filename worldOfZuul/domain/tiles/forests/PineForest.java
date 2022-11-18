package worldOfZuul.domain.tiles.forests;

public class PineForest extends AbstractForest {
    // static attributes


    private final static int maxPop = 100;
    private final static int saplingTurnsToGrow = 4;
    private final static int treePrice = 10;
    private final static int saplingPrice = 6;
    private final static int treeEcoValue = 15;

    private final static int minStartingTreePop = 40;
    private final static int maxStartingTreePop = 70;

    public PineForest() {
        super(maxPop, saplingTurnsToGrow, treePrice, saplingPrice,
                treeEcoValue, minStartingTreePop, maxStartingTreePop);
    }
}
