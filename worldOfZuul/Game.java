package worldOfZuul;

import java.util.List;

public class Game {

    public static final int maxTicks = 30;

    private Tile currentTile;
    private int tick = 0;
    private CommandWords commands;
    private Inventory inventory;
    private Tile[][] tiles = new Tile[4][4];

    public Game() {
        createRooms();
        commands = new CommandWordsImplementation();
        currentTile = tiles[0][0];
        this.inventory = new Inventory();
    }

    private void createRooms() {

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j] = new Tile("tile " + i + "." + j);
            }
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {

                if (i > 0) {
                    tiles[i][j].setExit("west", tiles[i - 1][j]);
                }

                if (i < tiles.length - 1) {
                    tiles[i][j].setExit("east", tiles[i + 1][j]);
                }

                if (j > 0) {
                    tiles[i][j].setExit("north", tiles[i][j - 1]);
                }

                if (j < tiles.length - 1) {
                    tiles[i][j].setExit("south", tiles[i][j + 1]);
                }
            }
        }

    }

    public boolean goRoom(Command command) {

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

    public String getRoomDescription() {
        return currentTile.getLongDescription(" and you have " + (maxTicks - this.getTick())+" moves left");
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

    public Tile getCurrentRoom() {
        return this.currentTile;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Tile[][] getRooms() {
        return this.tiles;
    }

    public Boolean isGameFinished() {
        return this.tick == maxTicks;
    }


    public void tickCounter() {

        inventory.calcEco(tiles);
        tick++;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].getForest().saplingGrow();
            }
        }
        if (tick > 0) {
            System.out.printf("Sustain rating: "+"%.2f"+"\n", inventory.calcSustain());
        }

    }

    public int getTick() {
        return this.tick;
    }

}
