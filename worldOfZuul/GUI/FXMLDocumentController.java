package worldOfZuul.GUI;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import worldOfZuul.domain.game.Game;


public class FXMLDocumentController implements Initializable{
    @FXML
    private GridPane map;

    private Game game;
    private Label[] labels;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.game = new Game();
    
        labels = new Label[map.getColumnCount()*map.getRowCount()];
        int labelIndex = 0;

        for (int i = 0; i < map.getColumnCount(); i++) {
            for (int j = 0; j < map.getRowCount(); j++){
                labels[labelIndex] = new Label("" + i + ":" + j);
                map.add(labels[labelIndex], i, j); 
                labelIndex++;

            }
        }

        
        this.updateMap();
    }


    private void updateMap(){

       int labelIndex = 0;
        for (int i = 0; i < game.getRooms().length; i++) {
            for (int j = 0; j < game.getRooms().length; j++){
                if(game.getRooms()[j][i] == game.getCurrentRoom()){
                    labels[labelIndex].setText("X");
                }else{
                    labels[labelIndex].setText(String.format("%03d", game.getRooms()[j][i].getForest().getTreePop()));
                }
                labelIndex++;
            }
        }
    }

}
