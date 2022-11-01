/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldOfZuul.textUI;

import java.rmi.StubNotFoundException;

import worldOfZuul.Command;
import worldOfZuul.Commands;
import worldOfZuul.Game;

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
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
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
        System.out.println(game.getRoomDescription());
        System.out.println("There are " + game.getCurrentRoom().getForest().getTreePop() + " trees and " + game.getCurrentRoom().getForest().getSaplingPop() + " saplings.");
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
                System.out.println(game.getCurrentRoom().getForest().plant(Integer.parseInt(command.getCommandValue())));
                break;
            
            case CHOP:
                // String msg = command.getCommandValue();
                // System.out.println();
                System.out.println(game.getCurrentRoom().getForest().chop(Integer.parseInt(command.getCommandValue())));
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


        // if (commandWord == Commands.HELP) {
        //     System.out.println("You are lost. You are alone. You wander");
        //     System.out.println("around at the university.");
        //     System.out.println();
        //     System.out.println("Your command words are:");
        //     printHelp();
        // } else if (commandWord == Commands.GO) {
        //     if (game.goRoom(command)) {
        //         System.out.println(game.getRoomDescription());
        //     } else {
        //         System.out.println("Can't walk in that direction.");
        //     }
        // } else if (commandWord == Commands.QUIT) {
        //     if (game.quit(command)) {
        //         wantToQuit = true;
        //     } else {
        //         System.out.println("Quit what?");
        //     }

        // }
        return wantToQuit;
    }
}
