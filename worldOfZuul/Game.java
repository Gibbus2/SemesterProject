package worldOfZuul;

import java.util.List;

public class Game {

    public static final int maxTicks = 30;

    private Room currentRoom;
    private int tick = 0;
    private CommandWords commands;
    private Inventory inventory;

    public Room[][] rooms = new Room[4][4];

    public Game() {
        createRooms();
        commands = new CommandWordsImplementation();
        currentRoom = rooms[0][0];
        this.inventory = new Inventory();
    }

    private void createRooms() {

        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms.length; j++) {
                rooms[i][j] = new Room("tile " + i + "." + j);
            }
        }

        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms.length; j++) {

                if (i > 0) {
                    rooms[i][j].setExit("west", rooms[i - 1][j]);
                }

                if (i < rooms.length - 1) {
                    rooms[i][j].setExit("east", rooms[i + 1][j]);
                }

                if (j > 0) {
                    rooms[i][j].setExit("north", rooms[i][j - 1]);
                }

                if (j < rooms.length - 1) {
                    rooms[i][j].setExit("south", rooms[i][j + 1]);
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

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            return false;
        } else {
            currentRoom = nextRoom;
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
        return currentRoom.getLongDescription(" And you have " + (maxTicks - this.getTick())+" moves left");
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

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Room[][] getRooms() {
        return this.rooms;
    }

    public Boolean isGameFinished() {
        return this.tick == maxTicks;
    }


    public void tickCounter() {

        inventory.calcEco(rooms);
        tick++;

        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms.length; j++) {
                rooms[i][j].getForest().saplingGrow();
            }
        }
        if (tick > 0) {
            System.out.println("Sustain rating: " + inventory.calcSustain());
        }




       /*
        if (tick == 10) {
            System.out.println("Ding! Ding! Round 10, your money-score is: "+ this.inventory.getEcoScore()+" and your eco-score is: "+this.igetMoneyScore);
        }
        if (tick == 20) {
            System.out.println();
        }

        */

        /*
        if (tick != 30) {

        } else {
            System.out.println("Tick count: " + tick);
            //TODO: INSERT QUIT



        }

         */
    }

    public int getTick() {
        return this.tick;
    }

}
