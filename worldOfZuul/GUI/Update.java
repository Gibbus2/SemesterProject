package worldOfZuul.GUI;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import worldOfZuul.domain.game.Game;
import worldOfZuul.domain.tiles.forests.OakForest;
import worldOfZuul.domain.tiles.forests.PineForest;
import worldOfZuul.domain.tiles.forests.JungleForest;


public class Update {
    

    public Update(){

    }

    public void updateMap(Game game, Text[] tileData) {
        int labelIndex = 0;
        for (int i = 0; i < game.getRooms().length; i++) {
            for (int j = 0; j < game.getRooms().length; j++) {
                if (game.getRooms()[j][i] == game.getCurrentRoom()) {
                    tileData[labelIndex].setText("X");
                } else {
                    tileData[labelIndex].setText(String.format("%03d", game.getRooms()[j][i].getForest().getTreePop()));
                }
                labelIndex++;
            }
        }
    }


    public void updateInfo(Game game, Text ecoScore, Text money, Text trees, Text saplings, Text turnsLeft, Text chopped, Text saplingGrowthTimer) {
        ecoScore.setText("" + game.getInventory().calcEco(game.getRooms()));
        money.setText("" + game.getInventory().getMoneyScore());

        trees.setText("" + game.getCurrentRoom().getForest().getTreePop());
        saplings.setText("" + game.getCurrentRoom().getForest().getSaplingPop());

        turnsLeft.setText("" + (Game.maxTicks - game.getTick()));
        chopped.setText("" + game.getInventory().getWoodChopped());

        saplingGrowthTimer.setText("" + game.getCurrentRoom().getForest().getSaplingTurnsLeft());
    }


    public void updateGoButtons(Game game, Button goNorth, Button goEast, Button goSouth, Button goWest) {
        goNorth.setDisable(game.getCurrentRoom().getExit("north") == null);
        goEast.setDisable(game.getCurrentRoom().getExit("east") == null);
        goSouth.setDisable(game.getCurrentRoom().getExit("south") == null);
        goWest.setDisable(game.getCurrentRoom().getExit("west") == null);
    }


    public void updateBackground(Game game, ImageView pineSky, ImageView oakSky, ImageView jungleSky, ImageView oakLongCloud, ImageView pineLongCloud, ImageView jungleLongCloud) {
        pineSky.setVisible(false);
        oakSky.setVisible(false);
        jungleSky.setVisible(false);
        oakLongCloud.setVisible(false);
        pineLongCloud.setVisible(false);
        jungleLongCloud.setVisible(false);

        if (game.getCurrentRoom().getForest().getClass() == OakForest.class) {
            oakSky.setVisible(true);
            oakLongCloud.setVisible(true);
        }else if (game.getCurrentRoom().getForest().getClass() == PineForest.class) {
            pineSky.setVisible(true);
            pineLongCloud.setVisible(true);

        }else if (game.getCurrentRoom().getForest().getClass() == JungleForest.class) {
            jungleSky.setVisible(true);
            jungleLongCloud.setVisible(true);

        }


    }

}
