/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChopMaster.textUI;

import ChopMaster.domain.commands.Command;
import ChopMaster.domain.commands.Commands;
import ChopMaster.domain.game.Game;

/**
 *
 * @author ancla
 */
public class CommandLineClient {

    private Parser parser;
    private Game game;

    public CommandLineClient() {
        game = new Game();
        parser = new Parser(game);
    }

    public void play() {
        printWelcome();
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = (processCommand(command) || game.isGameFinished());
        }
        System.out.println();
        System.out.println("Thank you for playing ChopMaster3000!");
        //System.out.printf("\n"+"Your final highscore & sustainability rating is: "+"%.2f");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to (Sustainable)ChopMaster3000");
        System.out.println("Your objective is to get as much money by chopping wood in a sustainable way in 30 moves!");
        System.out.println("Money is earned by cutting woods, and spent on planting new saplings that will grow into new trees.");
        System.out.println("You final Sustainability rating is based on how much money you have times how many trees are left in the beginning.");
        System.out.println("Type '" + Commands.HELP + "' if you need help.");
        System.out.println();
        this.roomInfo();
    }   

    private void printHelp() {
        for(String str : game.getCommandDescriptions())
        {
            System.out.println(str + " ");
        }
    }

    private void roomInfo(){
        if(!game.isGameFinished()){
            System.out.println(game.getRoomDescription());
            System.out.println("There are " + game.getCurrentRoom().getForest().getTreePop() + " trees and " + game.getCurrentRoom().getForest().getSaplingPop() + " saplings.");
            System.out.println("Wood chopped: " + game.getInventory().getWoodChopped());
            if(game.getTick() == Game.maxTicks - 1){
                System.out.println("This is your last move");
            }

            location();
        }
    }

    private void location(){
        for (int i = 0; i < game.getRooms().length; i++) {
            System.out.println("+-----+-----+-----+-----+");
            for (int j = 0; j < game.getRooms().length; j++) {
                if(game.getRooms()[j][i].equals(game.getCurrentRoom())){
                    System.out.printf("|  x  ");
                }else{
                    System.out.printf("| %03d ", game.getRooms()[j][i].getForest().getTreePop());
                }
            }
            System.out.printf("|\n");
        }

        System.out.println("+-----+-----+-----+-----+");
        System.out.println();
    }

    //Controller
    public boolean processCommand(Command command) {
        boolean wantToQuit = false;

        Commands commandWord = command.getCommandName();

        if (commandWord == Commands.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        
        switch (commandWord) {
            case HELP:
                System.out.println("Your command words are:");
                printHelp();
                break;
        
            case GO:
                if (game.goRoom(command)) {
                    this.roomInfo();
                } else {
                    System.out.println("Can't walk in that direction.");
                }

                break;

            case QUIT:
                if (game.quit(command)) {
                    wantToQuit = true;
                } else {
                    System.out.println("Quit what?");
                }

                break;

            case PLANT:
                if(command.getCommandValue() == null){
                    System.out.println("Please insert a number after 'plant'");
                }else{
                    System.out.println(game.getCurrentRoom().getForest().plant(command.getCommandValue(), game.getInventory()));
                }
                break;
            
            case CHOP:
                if(command.getCommandValue() == null){
                    System.out.println("Please insert a number after 'chop'");
                }else{
                    System.out.println(game.getCurrentRoom().getForest().chop(command.getCommandValue(), game.getInventory()));
                }

                break;
            case SHOWSCORE:
                System.out.println();
                System.out.println("You currently have: " + game.getInventory().getMoneyScore() + " Euro-Dollars");
                System.out.println("Current eco-Score is: " + game.getInventory().calcEco(game.getRooms()));
                System.out.println();
                break;
            default:
                break;
        }
        return wantToQuit;
    }
}
