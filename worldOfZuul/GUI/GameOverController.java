package worldOfZuul.GUI;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import worldOfZuul.domain.game.Game;

import javafx.event.ActionEvent;

import javafx.scene.Node;
import javafx.stage.Stage;

public class GameOverController {
    
    @FXML
    private TextArea textArea;

    @FXML
    private Button quit, restart, cancel;

    private Scene mainScene;
    private MainController mainController;


    public void showInfo(Game game){
        textArea.setText(
            "Thank you for playing ChopMaster!\nYour Final score was " + game.getInventory().calcEco(game.getRooms()) + "\nYou have chopped " + game.getInventory().getWoodChopped() +" trees\nand planted "+ game.getInventory().getTreesPlanted()+ " trees"
        );
    }

    public void setCancelButtonVisable(boolean value){
        cancel.setVisible(value);
    }

    //setters
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onQuitButtonPressed(ActionEvent event){
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    private void onRestartButtonPressed(ActionEvent event){
        mainController.start(new Game());
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(mainScene);

        this.mainController.onInfoBoxClicked(null);
        setCancelButtonVisable(true);
    }

    @FXML
    private void onCancelButtonPressed(ActionEvent event){
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(mainScene);
    }
}
