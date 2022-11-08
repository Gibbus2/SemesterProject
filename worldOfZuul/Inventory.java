package worldOfZuul;

public class Inventory {

    private int moneyScore = 0;
    private double sustainRating;
    private double ecoScore = 0;
    private double[] ecoArray= new double[Game.maxTicks+1];
    private int index =0;


    public Inventory() {
        this.moneyScore = 100; //Starting with 100 Euro-dollars
    }

    public void calcMoney(int woodInput) {
        this.moneyScore += woodInput;
    }

    public double calcEco(Room[][] rooms) {
        double ecoScoreInterim = 0;
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms.length; j++) {
                ecoScoreInterim += rooms[i][j].getForest().getTreePop();
            }
        }
        this.ecoArray[this.index]=this.ecoScore = ecoScoreInterim / 1600;
        return this.ecoArray[this.index];
        //Will give a number from 0.0 to 1.0
    }

    //Sustainability (bæredygtighed) score is based upon how much money you've earned, times how many trees are left (ecoscore)
    public double calcSustain() {
        this.sustainRating = (this.moneyScore * CalcAverageEcoScore());
        return this.sustainRating;
    }

    private double CalcAverageEcoScore(){
        double average=0;
        for (int i = 0; i < this.index; i++) {
            average+=this.ecoArray[i];
        }
        this.index++;
        return average/this.index;
    }

    //Getters
    public double getEcoScore() {
        return this.ecoScore;
    }

    public int getMoneyScore() {
        return this.moneyScore;
    }


}
