package worldOfZuul;

public class Inventory {

    private int moneyScore = 0;
    private int sustainRating = 0;
    private double ecoScore = 0;

    public void calcMoney(int x) {
        moneyScore += x;
    }

    public void calcEco(Room[] rooms) {
        double ecoScoreInterim = 0;
        for (int i = 0; i < rooms.length; i++) {
            ecoScoreInterim += rooms[i].getForest().getTreePop();
        }
        ecoScore = ecoScoreInterim / 16;
    }

    public double calcSustain() {
        return this.moneyScore * sustainRating;
    }

    //Skal der være parameter?
    public int getMoneyScore() {
        return this.moneyScore;
    }

    // fjffjfj

}
