package worldOfZuul.GUI;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import worldOfZuul.domain.game.Game;
import javafx.scene.control.*;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainController implements Initializable{
    @FXML
    private GridPane map;

    @FXML
    private Text ecoScore, money, trees, saplings, turnsLeft, chopped;

    @FXML
    private Button quit, goNorth, goEast, goSouth, goWest, plant, chop;

    @FXML
    private TextField input;

    private Game game;
    private Text[] tileData;
    private Scene helpScene;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tileData = new Text[map.getColumnCount()*map.getRowCount()];
        int labelIndex = 0;

        for (int i = 0; i < map.getColumnCount(); i++) {
            for (int j = 0; j < map.getRowCount(); j++){
                tileData[labelIndex] = new Text("" + j + ":" + i);
                map.add(tileData[labelIndex], j, i); 
                GridPane.setHalignment(tileData[labelIndex], HPos.CENTER);
                GridPane.setValignment(tileData[labelIndex], VPos.CENTER);
                labelIndex++;
            }
        }
    }

    //button events
    @FXML
    private void onQuitButtonPressed(ActionEvent event){
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    private void onPlantButtonPressed(ActionEvent event){
        game.getCurrentRoom().getForest().plant(input.getText(), game.getInventory());
        updateAll();
    }

    @FXML
    private void onChopButtonPressed(ActionEvent event){
        game.getCurrentRoom().getForest().chop(input.getText(), game.getInventory());
        updateAll();
    }

    @FXML
    private void onGoNorthButtonPressed(ActionEvent event){
        goRoom("north");
    }
    
    @FXML
    private void onGoEastButtonPressed(ActionEvent event){
        goRoom("east");
    }

    @FXML
    private void onGoSouthButtonPressed(ActionEvent event){
        goRoom("south");
    }

    @FXML
    private void onGoWestButtonPressed(ActionEvent event){
        goRoom("west");
    }

    @FXML
    private void onHelpButtonPressed(ActionEvent event) {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(helpScene);
    }


    //settes
    public void setHelpScene(Scene helpScene){
        this.helpScene = helpScene;
    }

    private void goRoom(String direction){
        game.goRoom(game.getCommand("go", direction));
        updateAll();
        if(game.isGameFinished()){
            //END
            System.out.println("game end");
            //TODO: quit game from here
        }
    }


    private void updateAll(){
        updateMap();
        updateInfo();
        updateGoButtons();
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

    private void updateGoButtons(){
        goNorth.setDisable(game.getCurrentRoom().getExit("north") == null);
        goEast.setDisable(game.getCurrentRoom().getExit("east") == null);
        goSouth.setDisable(game.getCurrentRoom().getExit("south") == null);
        goWest.setDisable(game.getCurrentRoom().getExit("west") == null);
    }

    public void start(Game game) {
        this.game = game;
        updateAll();
    }


}
