package worldOfZuul;

import java.lang.Math;

public class Forest {
    private static int maxPop = 100;
    private static int maxAge = 4;
    private static int treePrice = 10;
    private static int saplingPrice = 1;
    private int treePop, saplingPop, saplingAge;

    public Forest() {
        this.treePop = (int) (maxPop * Math.random() + 1);
        this.saplingPop = 0;
        this.saplingAge = 0;
    }

    //getters
    public int getTreePop() {
        return this.treePop;
    }

    public int getSaplingPop() {
        return this.saplingPop;
    }

    public int getSaplingAge() {
        return this.saplingAge;
    }


    //Chops down given amount of trees, or all if amount is too high.
    //Adds money to inventory
    //returns message to user
    public String chop(int amount, Inventory inventory) {
        if (amount <= this.treePop) {
            this.treePop = this.treePop - amount;
        } else {
            amount = this.treePop;
            this.treePop = 0;
        }

        inventory.calcMoney(amount * treePrice);        
        return amount + " trees chopped.\nTrees remaining: " + this.treePop;
    }

    //Plants the given amount of trees, or max allowed.
    //Subtracts money from inventory
    //returns message to user
    public String plant(int amount, Inventory inventory) {
        if (this.saplingAge != 0) {
            return "Wait " + this.saplingAge + " turns before planting new saplings";
        }
        this.saplingPop = (amount + this.treePop >= maxPop) ? maxPop - this.treePop : amount;
        this.saplingAge = maxAge;

        inventory.calcMoney(-amount * saplingPrice);
        return "Planted " + this.saplingPop + " saplings.";
    }

    //Increase sapling age and converts them to trees
    public void saplingGrow() {
        if(this.saplingAge > 0){
            this.saplingAge -= 1;
        }else{
            this.treePop += this.saplingPop;
            this.saplingPop = 0;
            this.saplingAge = 0;
        }
    }
}