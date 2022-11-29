package worldOfZuul.domain.inventory;

import worldOfZuul.domain.tiles.Tile;

public class Inventory {

    private int moneyScore;

    private int woodChopped = 0;

    public Inventory() {
        this.moneyScore = 100; //Starting with 100 Euro-dollars
    }

    public void calcMoney(int woodInput) {
        this.moneyScore += woodInput;
    }



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

    public int getMoneyScore() {
        return this.moneyScore;
    }


    public int getWoodChopped() {
        return woodChopped;
    }

    public void setWoodChopped(int woodChopped) {
        this.woodChopped += woodChopped;
    }
}
