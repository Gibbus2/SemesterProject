package worldOfZuul.GUI;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;

import worldOfZuul.domain.game.Game;


public class FXMLDocumentController implements Initializable{
    @FXML
    private GridPane map;

    @FXML
    private Text ecoScore, money, trees, saplings, turnsLeft, chopped;

    private Game game;
    private Text[] tileData;

    @FXML
    private Button quit;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.game = new Game();
    
        tileData = new Text[map.getColumnCount()*map.getRowCount()];
        int labelIndex = 0;

        for (int i = 0; i < map.getColumnCount(); i++) {
            for (int j = 0; j < map.getRowCount(); j++){
                tileData[labelIndex] = new Text("" + i + ":" + j);
                map.add(tileData[labelIndex], i, j); 
                GridPane.setHalignment(tileData[labelIndex], HPos.CENTER);
                GridPane.setValignment(tileData[labelIndex], VPos.CENTER);
                labelIndex++;
            }
        }
        
        this.updateMap();
        this.updateInfo();
    }

    public void handleButtonQuit(ActionEvent event){
        WorldOfZuulApplication.quit();
    }


    private void updateMap(){
       int labelIndex = 0;
        for (int i = 0; i < game.getRooms().length; i++) {
            for (int j = 0; j < game.getRooms().length; j++){
                if(game.getRooms()[j][i] == game.getCurrentRoom()){
                    tileData[labelIndex].setText("X");
                }else{
                    tileData[labelIndex].setText(String.format("%03d", game.getRooms()[j][i].getForest().getTreePop()));
                }
                labelIndex++;
            }
        }
    }

    private void updateInfo(){
        this.ecoScore.setText("" + this.game.getInventory().calcEco(game.getRooms()));
        this.money.setText("" + this.game.getInventory().getMoneyScore());

        this.trees.setText("" + game.getCurrentRoom().getForest().getTreePop());
        this.saplings.setText("" + game.getCurrentRoom().getForest().getSaplingPop());

        this.turnsLeft.setText("" + (Game.maxTicks - game.getTick()));
        this.chopped.setText("" + game.getInventory().getWoodChopped());
    }

}
