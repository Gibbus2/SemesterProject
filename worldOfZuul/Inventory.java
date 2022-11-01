package worldOfZuul;

public class Inventory {

    public Inventory() {
        this.moneyScore = 100; //Starting with 100 Euro-dollars
    }


    private int moneyScore = 0;
    private int sustainRating = 0;
    private double ecoScore = 0;

    public void calcMoney(int woodInput) {
        moneyScore += woodInput;
    }

    public void calcEco(Room[][] rooms) {
        double ecoScoreInterim = 0;
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms.length; j++) {
                ecoScoreInterim += rooms[i][j].getForest().getTreePop();
            }
        }
        ecoScore = ecoScoreInterim / 1600;
        //Will give a number from 0.0 to 1.0
    }

    //Sustainability (bæredygtighed) score is based upon how much money you've earned, times how many trees are left (ecoscore)
    public double calcSustain() {
        return this.moneyScore * sustainRating;
    }


    //Getters
    public double getEcoScore() {
        return this.ecoScore;
    }

    public int getMoneyScore() {
        return this.moneyScore;
    }


}
