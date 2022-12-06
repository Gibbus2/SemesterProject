package ChopMaster.domain.tiles.forests;

public record ForestSpawnChances() {
    // attributes
    private static final double rainForestSpawnChance = 0.2;
    private static final double oakForestSpawnChance = 0.5;
    private static final double pineForestSpawnChance = 0.3;

    // methods
    // getters
    public static double getRainForestSpawnChances() {
        return rainForestSpawnChance;
    }

    public static double getOakForestSpawnChance() {
        return oakForestSpawnChance;
    }

    public static double getPineForestSpawnChance() {
        return pineForestSpawnChance;
    }
}
