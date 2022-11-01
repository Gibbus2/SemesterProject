package worldOfZuul;

import java.lang.Math;

public class Forest{
    private static int maxPop = 100;
    private static int maxAge = 4;
    private int treePop, saplingPop, saplingAge;

    public Forest(){
        this.treePop = (int)(maxPop * Math.random() + 1);
        this.saplingPop = 0;
        this.saplingAge = 0;
    }

    //getters
    public int getTreePop(){
        return this.treePop;
    }

    public int getSaplingPop(){
        return this.saplingPop;
    }

    public int getSaplingAge(){
        return this.saplingAge;
    }


    //TODO: interact with inventory
    //Chops down given amount of trees, or all if amount is too high.
    //Adds money to inventory
    //returns massage to user
    public String chop(int amount){
        if(amount <= this.treePop){
            this.treePop = this.treePop - amount;
        }else{
            this.treePop = 0;
        }

        return amount + " trees chopped.\nTrees ramaining: " + this.treePop; 
    }

    //TODO: interact with inventory
    //Plants the given amount of trees, or max allowed.
    //Subtracts money from inventory
    //returns message to user
    public String plant(int amount){
        if(this.saplingAge != 0){
            return "Wait " + this.saplingAge + " turns before planting new saplings";
        }
        this.saplingPop = (amount + this.treePop > maxPop) ? maxPop - this.treePop : amount; 
        this.saplingAge = maxAge;
        return "Planted " + this.saplingPop + " saplings.";
    }

    //Incress saping age and converts them to trees
    public void saplingGrow(){
        this.saplingAge -= 1;
        if(this.saplingAge == 0){
            this.treePop += this.saplingPop;
            this.saplingAge = 0;
        }
    }
}