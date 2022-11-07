package worldOfZuul;

public class Inventory {

    private int moneyScore = 0;
    private double sustainRating;
    private double ecoScore = 0;
    private double[] ecoArray= new double[30];
    private int index =0;


    public Inventory() {
        this.moneyScore = 100; //Starting with 100 Euro-dollars
    }

    public void calcMoney(int woodInput) {
        moneyScore += woodInput;
    }

    public double calcEco(Room[][] rooms) {
        double ecoScoreInterim = 0;
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms.length; j++) {
                ecoScoreInterim += rooms[i][j].getForest().getTreePop();
            }
        }
        ecoArray[index]=ecoScore = ecoScoreInterim / 1600;
        index++;
        return ecoArray[index-1];
        //Will give a number from 0.0 to 1.0
    }

    //Sustainability (bæredygtighed) score is based upon how much money you've earned, times how many trees are left (ecoscore)
    public double calcSustain() {
        this.sustainRating = (moneyScore * CalcAverageEcoScore());
        return sustainRating;
    }

    private double CalcAverageEcoScore(){
        double average=0;
        for (int i = 0; i < 30; i++) {
            average+=ecoArray[i];
        }
        return average/index;
    }

    //Getters
    public double getEcoScore() {
        return this.ecoScore;
    }

    public int getMoneyScore() {
        return this.moneyScore;
    }


}
