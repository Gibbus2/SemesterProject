package ChopMaster.domain.inventory;

import ChopMaster.domain.tiles.Tile;

public class Inventory {
    //used to keep track of how much money you have
    private int moneyScore;
    //used to keep track of how much wood you have chopped
    private int woodChopped = 0;
    //used to keep track of how many trees you have planted
    private int treesPlanted = 0;

    // constructor
    public Inventory() {
        this.moneyScore = 100; //Starting with 100 Euro-dollars
    }

    //increase your moneyscore
    public void calcMoney(int woodInput) {
        this.moneyScore += woodInput;
    }


    // method for calculating your eco score, which is based on many tree are on the map, and what kinda
    public double calcEco(Tile[][] tiles) {
        double finalEcoScore = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                finalEcoScore += (tiles[i][j].getForest().getTreePop() * tiles[i][j].getForest().getTreeEcoValue());
            }
        }
        return finalEcoScore;
        //Will give a number from 0.0 to 1.0
    }


    //getters
    public int getMoneyScore() {
        return this.moneyScore;
    }

    public int getTreesPlanted() {
        return treesPlanted;
    }

    public int getWoodChopped() {
        return woodChopped;
    }

    //setters
    public void setWoodChopped(int woodChopped) {
        this.woodChopped += woodChopped;
    }

    public void setTreesPlanted(int treesPlanted) {
        this.treesPlanted += treesPlanted;
    }
}
