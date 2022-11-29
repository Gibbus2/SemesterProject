package worldOfZuul.domain.tiles.forests;

public class JungleForest extends AbstractForest {
    // static attributes
    private final static int maxPop = 100;
    private final static int saplingTurnsToGrow = 6;
    private final static int treePrice = 15;
    private final static int saplingPrice = 15;
    private final static int treeEcoValue = 100;

    private final static int minStartingTreePop = 5;
    private final static int maxStartingTreePop = 15;

    public JungleForest() {
        super(maxPop, saplingTurnsToGrow, treePrice, saplingPrice,
                treeEcoValue, minStartingTreePop, maxStartingTreePop);
    }
}
