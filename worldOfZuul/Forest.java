package worldOfZuul;

import java.lang.Math;

public class Forest {
    private static int maxPop = 100;
    private static int maxAge = 4;
    private static int treePrice = 10;
    private static int saplingPrice = 6;
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
    public String chop(String userInput, Inventory inventory) {
        int amount = this.parseNumber(userInput);
        String msg;
        if(this.isValidNumber(amount)){
            if (amount <= this.treePop) {
                this.treePop = this.treePop - amount;
            } else {
                amount = this.treePop;
                this.treePop = 0;
            }
            inventory.calcMoney(amount * treePrice);        
            msg = amount + " trees chopped.\nTrees remaining: " + this.treePop;
        }else{
            msg = "Invalid number";
        }
        return msg;
    }

    //Plants the given amount of trees, or max allowed.
    //Subtracts money from inventory
    //returns message to user
    public String plant(String userInput, Inventory inventory) {
        int amount = this.parseNumber(userInput);
        String msg;
        if(this.isValidNumber(amount)){ 
            if(this.saplingAge == 0){
                if(amount + this.treePop >= maxPop){
                    amount = maxPop - this.treePop;
                }        
                if(amount * saplingPrice > inventory.getMoneyScore()){
                    amount = (int)(inventory.getMoneyScore() / saplingPrice);
                }
    
                this.saplingPop = amount;
                this.saplingAge = maxAge;
                inventory.calcMoney(-this.saplingPop * saplingPrice);

                msg =  "Planted " + this.saplingPop + " saplings.";

            }else{
                msg = "Wait " + this.saplingAge + " turns before planting new saplings";
            }
          
        }else{
            msg = "Invalid number";
        }

        return msg;
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
}