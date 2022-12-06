package ChopMaster.GUI;

import ChopMaster.domain.game.Game;
import ChopMaster.domain.tiles.forests.JungleForest;
import ChopMaster.domain.tiles.forests.OakForest;
import ChopMaster.domain.tiles.forests.PineForest;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;



public class Update {
    
    private Game game;

    public Update(Game game){
        this.game = game;
    }

    //Updates the minimap with player location and tree population
    //takes a array of text objs starting in the top left corner for the grid(0,0), going left to right top to bottom
    public void map(Text[] tileData) {
        int labelIndex = 0;
        for (int i = 0; i < this.game.getTiles().length; i++) {
            for (int j = 0; j < this.game.getTiles().length; j++) {
                if (this.game.getTiles()[j][i] == this.game.getCurrentTile()) {
                    tileData[labelIndex].setText("X");
                } else {
                    tileData[labelIndex].setText(String.format("%03d", this.game.getTiles()[j][i].getForest().getTreePop()));
                }
                labelIndex++;
            }
        }
    }


    //Updates the values for score, money and tree related information 
    public void info(Text ecoScore, Text money, Text trees, Text saplings, Text turnsLeft, Text chopped, Text saplingGrowthTimer) {
        ecoScore.setText("" + this.game.getInventory().calcEco(this.game.getTiles()));
        money.setText("" + this.game.getInventory().getMoneyScore());

        trees.setText("" + this.game.getCurrentTile().getForest().getTreePop());
        saplings.setText("" + this.game.getCurrentTile().getForest().getSaplingPop());

        turnsLeft.setText("" + (Game.maxTicks - this.game.getTick()));
        chopped.setText("" + this.game.getInventory().getWoodChopped());

        saplingGrowthTimer.setText("" + this.game.getCurrentTile().getForest().getSaplingTurnsLeft());
    }


    //Disabels or enables buttons depending on what exits are availabe
    public void goButtons(Button goNorth, Button goEast, Button goSouth, Button goWest) {
        goNorth.setDisable(this.game.getCurrentTile().getExit("north") == null);
        goEast.setDisable(this.game.getCurrentTile().getExit("east") == null);
        goSouth.setDisable(this.game.getCurrentTile().getExit("south") == null);
        goWest.setDisable(this.game.getCurrentTile().getExit("west") == null);
    }


    //sets background and sky animation visable depending on forest type for the current tile
    public void background(ImageView pineSky, ImageView oakSky, ImageView jungleSky, ImageView oakLongCloud, ImageView pineLongCloud, ImageView jungleLongCloud) {
        pineSky.setVisible(false);
        oakSky.setVisible(false);
        jungleSky.setVisible(false);
        oakLongCloud.setVisible(false);
        pineLongCloud.setVisible(false);
        jungleLongCloud.setVisible(false);

        if (this.game.getCurrentTile().getForest().getClass() == OakForest.class) {
            oakSky.setVisible(true);
            oakLongCloud.setVisible(true);
        }else if (this.game.getCurrentTile().getForest().getClass() == PineForest.class) {
            pineSky.setVisible(true);
            pineLongCloud.setVisible(true);

        }else if (this.game.getCurrentTile().getForest().getClass() == JungleForest.class) {
            jungleSky.setVisible(true);
            jungleLongCloud.setVisible(true);

        }
    }

    //Updates tree/sapling sprites, so one tree/sapling is showed for each 10.
    public void forest(ImageView[] treeViews, Image oak, Image pine, Image jungle, Image oakSapling, Image pineSapling, Image jungleSapling, Image stump) {
        int treePop = this.game.getCurrentTile().getForest().getTreePop();
        int saplingPop = this.game.getCurrentTile().getForest().getSaplingPop();

        for (ImageView treeView : treeViews) {
            Image image;
            if (treePop >= 10) {
                if (this.game.getCurrentTile().getForest().getClass() == OakForest.class) {
                    image = oak;
                } else if (this.game.getCurrentTile().getForest().getClass() == PineForest.class) {
                    image = pine;
                } else {
                    image = jungle;
                }
                treePop = treePop - 10;
            } else if (saplingPop >= 10) {
                if (this.game.getCurrentTile().getForest().getClass() == OakForest.class) {
                    image = oakSapling;
                } else if (this.game.getCurrentTile().getForest().getClass() == PineForest.class) {
                    image = pineSapling;
                } else {
                    image = jungleSapling;
                }
                saplingPop = saplingPop - 10;
            } else {
                image = stump;
            }

            treeView.setImage(image);
        }
    }

    //Set infobox visable at tick 7, 15 and 25, with info on upcomming milestones.
    public void infobox(Button infoBox) {
        if (this.game.getTick() == 10 || this.game.getTick() == 20 || this.game.getTick() == 30 ||
                (this.game.getTick() <= 10 && this.game.getInventory().getWoodChopped() >= 150) ||
                (this.game.getTick() <= 20 && this.game.getInventory().getWoodChopped() >= 400) ||
                (this.game.getTick() <= 30 && this.game.getInventory().getWoodChopped() >= 1000)) {
            infoBox.setVisible(false); //when the sub-goal is succeeded it will remove infobox
        }       // so it is ready for next infobox. If reach goal before sub-goal round it also disappear.


        if (infoBox.isVisible()) {
            return; //if infobox is visible it will go to the top of method again and check the 1st if-statement.
        }

        if ((this.game.getTick() == 7) && this.game.getInventory().getWoodChopped() < 150) { //1st sub-goal.
            infoBox.setVisible(true);
            infoBox.setText("IKEA demands 150 trees by round 10. GET TO WORK!"+"\n"+"        (Click to minimize)");
        } else if (this.game.getTick() == 15 && this.game.getInventory().getWoodChopped() < 400) { //2nd sub-goal.
            infoBox.setVisible(true);
            infoBox.setText("IKEA demands 450 trees by round 20. YOU CAN DO IT!"+"\n"+"        (Click to minimize)");
        } else if (this.game.getTick() == 25 && this.game.getInventory().getWoodChopped() < 1000) { //3rd sub-goal.
            infoBox.setVisible(true);
            infoBox.setText("IKEA demands 1000 trees by round 30. HURRY UP!"+"\n"+"        (Click to minimize)");
        } else
            infoBox.setText(null);
    }


}
