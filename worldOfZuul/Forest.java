package worldOfZuul;

public class Forest {

    private static int maxPop = 100; // Every Forest is maximum 100 trees+saplings for now. - Aura
    private static int maxSaplingAge = 4; // Saplings will grow when their age exceeds 4 for now - Aura.
    private int treePop, saplingPop, saplingAge;

    /** I set every Forest to be fully filled out with
     * trees at construction for now.
     * - Aura
     */
    public Forest () {
        this.treePop = maxPop;
        this.saplingPop = 0;
        this.saplingAge = 0;
    }

    /** chop() method. For now, chopping trees
     * doesn't net any money, nor decrease sustainability.
     * - Aura
     */
    public void chop (int trees) {
        if (trees > treePop) {
            trees = treePop; // If inputted number is too high, all trees are just chopped.
        }
        treePop = treePop - trees;
    }

    /** plant() method. For now, planting saplings
     * doesn't cost any money, nor increase sustainability.
     * For now, planting new saplings will reset the age of already planted saplings.
     * - Aura
     */
    public void plant (int saplings) {
        if (treePop + saplingPop + saplings > maxPop) {
            /** If inputted number is too high, all empty spots in the Forest are filled.**/
            saplings = maxPop - treePop - saplingPop;
        }
        saplingPop = saplingPop + saplings;
        saplingAge = 0;
    }

    /** saplingGrow() method. Grows saplings by some number of ticks.
     * Turns them into trees if saplingAge exceed maxSaplingAge.
     * - Aura
     */
    public void saplingGrow (int ticks) {
        saplingAge = saplingAge + ticks;
        if (saplingAge > maxSaplingAge) {
            saplingAge = 0;
            treePop = treePop + saplingPop;
            saplingPop = 0;
        }
    }

    /** Getters.
     * - Aura
     */
    public int getTreePop() {
        return treePop;
    }

    public int getSaplingPop() {
        return saplingPop;
    }

    public int getSaplingAge() {
        return saplingAge;
    }
}
