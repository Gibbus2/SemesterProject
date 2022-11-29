package worldOfZuul.domain.tiles.forests;

import worldOfZuul.domain.inventory.Inventory;

import java.lang.Math;

public abstract class AbstractForest {

    // final attributes

    // maxPop describes the max amount of seeds and trees.
    // saplingTurnsToGrow describes the number of turns it takes for a sapling to grow.
    // treePrice describes how much money a tree gives when chopped.
    // saplingPrice describes how much money it costs to plant a sapling.
    // treeEcoValue describes how much a single tree contributes to your total ecoValue.
    private final int maxPop, saplingTurnsToGrow, treePrice, saplingPrice, treeEcoValue;

    // minStartingTreePop describes the minimum starting population.
    // maxStartingTreePop describes the maximum starting population.
    private final int minStartingTreePop, maxStartingTreePop;

    // attributes

    // treePop describes the current tree population of a given instance of AbstractForest.
    // sapPop describes the current sapling population of a given instance of AbstractForest.
    // sapTurnsLeft describes the current number of turns left until saplings grow of a given instance of AbstractForest.
    private int treePop, saplingPop, saplingTurnsLeft;

    // constructor
    // Constructs an AbstractForest, and automatically fills it with some amount of trees,
    // determined by minStartingTreePopDouble and maxStartingTreePopDouble;

    protected AbstractForest(int maxPop,int saplingTurnsToGrow,int treePrice,int saplingPrice,int treeEcoValue,
                             int minStartingTreePop, int maxStartingTreePop) {

        this.maxPop = maxPop;
        this.saplingTurnsToGrow = saplingTurnsToGrow;
        this.treePrice = treePrice;
        this.saplingPrice = saplingPrice;
        this.treeEcoValue = treeEcoValue;

        this.minStartingTreePop = minStartingTreePop;
        this.maxStartingTreePop = maxStartingTreePop;

        this.treePop = getRandomTreePop();
        this.saplingPop = 0;
        this.saplingTurnsLeft = 0;
    }

    // static methods

    // getRandomTreePop() method. Returns a random tree population between
    // minStartingTreePop and maxStartingTreePop.
    public int getRandomTreePop() {
        int range = (maxStartingTreePop - minStartingTreePop);
        return (int)(Math.random() * range + minStartingTreePop) + 1;
    }

    // dynamic methods

    // chop() method.
    // Chops down given amount of trees, or all if amount is too high.
    // Adds money to inventory.
    // Returns message to user.
    public String chop(String userInput, Inventory inventory) {
        int amount = this.parseNumber(userInput);
        String msg;
        if(this.isValidNumber(amount)){
            if (amount <= this.treePop) {
                this.treePop = this.treePop - amount;
                inventory.setWoodChopped(amount);
            } else {
                amount = this.treePop;
                this.treePop = 0;
                inventory.setWoodChopped(amount);
            }
            inventory.calcMoney(amount * treePrice);        
            msg = amount + " trees chopped.\nTrees remaining: " + this.treePop;
        }else{
            msg = "Invalid number";
        }
        return msg;
    }

    // plant() method.
    // Plants the given amount of trees, or max allowed.
    // Subtracts money from inventory.
    // Returns message to user.
    public String plant(String userInput, Inventory inventory) {
        int amount = this.parseNumber(userInput);
        String msg;
        if(this.isValidNumber(amount)){
            if(this.saplingTurnsLeft == 0 || this.saplingTurnsLeft == this.saplingTurnsToGrow){
                if(amount + this.treePop + this.saplingPop >= maxPop){
                    amount = maxPop - (this.treePop+ this.saplingPop);
                }        
                if(amount * saplingPrice > inventory.getMoneyScore()){
                    amount = (int)(inventory.getMoneyScore() / saplingPrice);
                }
    
                this.saplingPop += amount;
                this.saplingTurnsLeft = this.saplingTurnsToGrow;
                inventory.calcMoney(-amount * saplingPrice);

                msg =  "Planted " + this.saplingPop + " saplings.";

            }else{
                msg = "Wait " + this.saplingTurnsLeft + " turns before planting new saplings";
            }
          
        }else{
            msg = "Invalid number";
        }

        return msg;
    }

    // saplingGrow() method.
    // Increase sapling age and converts them to trees
    public void saplingGrow() {
        if(this.saplingTurnsLeft > 0){
            // If the saplings have any turns left, subtract 1 turn.
            this.saplingTurnsLeft -= 1;
        }else{
            // Else grow the saplings.
            this.treePop += this.saplingPop;
            this.saplingPop = 0;
            this.saplingTurnsLeft = 0;
        }
    }


    private int parseNumber(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }
    private boolean isValidNumber(int i){
        return i > 0;
    }

    // getters

    public int getMaxPop() {
        return maxPop;
    }

    public int getSaplingTurnsToGrow() {
        return saplingTurnsToGrow;
    }

    public int getTreePrice() {
        return treePrice;
    }

    public int getSaplingPrice() {
        return saplingPrice;
    }

    public int getTreeEcoValue() {
        return treeEcoValue;
    }

    public int getMinStartingTreePop() {
        return minStartingTreePop;
    }

    public int getMaxStartingTreePop() {
        return maxStartingTreePop;
    }

    public int getTreePop() {
        return treePop;
    }

    public int getSaplingPop() {
        return saplingPop;
    }

    public int getSaplingTurnsLeft() {
        return saplingTurnsLeft;
    }
}