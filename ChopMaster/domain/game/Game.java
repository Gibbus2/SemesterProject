package ChopMaster.domain.game;

import ChopMaster.domain.commands.Command;
import ChopMaster.domain.commands.CommandImplementation;
import ChopMaster.domain.commands.CommandWords;
import ChopMaster.domain.commands.CommandWordsImplementation;
import ChopMaster.domain.inventory.Inventory;
import ChopMaster.domain.tiles.Tile;
import ChopMaster.domain.tiles.forests.*;

import java.util.List;
import java.lang.Math;

public class Game {

    public static final int maxTicks = 30;

    private Tile currentTile;
    private int tick = 0;
    private CommandWords commands;
    private Inventory inventory;
    private Tile[][] tiles = new Tile[4][4]; //double array to loop through giving name and direction. (16 tiles total)

    public Game() {
        createTiles();
        commands = new CommandWordsImplementation();
        currentTile = tiles[0][0]; // starting tile.
        this.inventory = new Inventory();
    }

    private void createTiles() { //using the tile[][]

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                double spawnDouble = Math.random(); // Determine random Forest type (at the current tile).
                if (spawnDouble < ForestSpawnChances.getRainForestSpawnChances()) {
                    tiles[i][j] = new Tile("Rainforest at [" + i + "," + j + "]", new JungleForest());
                } else {
                    spawnDouble -= ForestSpawnChances.getRainForestSpawnChances();
                    if (spawnDouble < ForestSpawnChances.getOakForestSpawnChance()) {
                        tiles[i][j] = new Tile("Oak forest at [" + i + "," + j + "]", new OakForest());
                    } else {
                        tiles[i][j] = new Tile("Pine forest at [" + i + "," + j + "]", new PineForest());
                    }
                }
            }
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) { //giving direction to neighbor rooms. 4 independent if-statements.

                if (i > 0) { //if i > 0. (NOT all the way to the left) The current room for sure has the direction west.
                    tiles[i][j].setExit("west", tiles[i - 1][j]);
                }

                if (i < tiles.length - 1) {
                    tiles[i][j].setExit("east", tiles[i + 1][j]);
                }

                if (j > 0) { //if j is bigger than 0 it the room for sure has the direction north.
                    tiles[i][j].setExit("north", tiles[i][j - 1]);
                }

                if (j < tiles.length - 1) {
                    tiles[i][j].setExit("south", tiles[i][j + 1]);
                }
            }
        }
    }

    public boolean goTile(Command command) {

        if (!command.hasCommandValue()) {
            //No direction on command.
            //Can't continue with GO command.
            return false;
        }

        String direction = command.getCommandValue();

        Tile nextTile = currentTile.getExit(direction);

        if (nextTile == null) {
            return false;
        } else {
            currentTile = nextTile;
            tickCounter();
            return true;
        }
    }

    public boolean quit(Command command) {
        if (command.hasCommandValue()) {
            return false;
        } else {
            return true;
        }
    }

    //used by CLI to get a descrption about a title thats its called on
    public String getTileDescription() {
        return currentTile.getLongDescription(" and you have " + (maxTicks - this.getTick()) + " moves left");
    }

    public CommandWords getCommands() {
        return commands;
    }

    public List<String> getCommandDescriptions() {
        return commands.getCommandWords();
    }

    public Command getCommand(String word1, String word2) {
        return new CommandImplementation(commands.getCommand(word1), word2);
    }

    public Boolean isGameFinished() { //game will finish if sub-goals are not full-filled.
        if ((getInventory().getWoodChopped() < 150) && (tick == 10)) {
            return true;
        }
        if ((getInventory().getWoodChopped() < 400) && (tick == 20)) {
            return true;
        }
        if ((getInventory().getWoodChopped() < 1000) && (tick == 30)) {
            return true;
        }
        return this.tick == maxTicks;
    }


    public void tickCounter() { //count every move/tick to another room.

        inventory.calcEco(tiles); //it will calculate the Eco for every room after every tick.
        tick++;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].getForest().saplingGrow(); //call the saplingGrow method to grow sapling after every tick.
            }
        }
    }

    //getters
    public Tile getCurrentTile() {
        return this.currentTile;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getTick() {
        return this.tick;
    }

}
