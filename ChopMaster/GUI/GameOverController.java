package ChopMaster.GUI;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import ChopMaster.domain.game.Game;

import javafx.event.ActionEvent;

import javafx.scene.Node;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class GameOverController implements Initializable {

    @FXML
    private TextArea textArea;

    @FXML
    private Button quit, restart, cancel;

    private Scene mainScene;
    private MainController mainController;

    //Sets message at game over, based on your eco-score
    private String sustainReview(Game game) {
        if (game.getInventory().calcEco(game.getTiles()) >= 30000) {
            return "Well done! Your highscore is above 30000,\nwhich proves you're a real sustainable Chop Master!";
        }

        if ((game.getInventory().calcEco(game.getTiles()) > 20000) && (game.getInventory().calcEco(game.getTiles()) < 30000)) {
            return "You could've performed a bit better,\ntry agian - plant a few more eco friendly trees next time.";
        }
        return "Your eco score is too low.\nYou need to plant more trees next time \nto be a real sustainable Chop Master.";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.getStyleClass().add("text");
    }

    //compiles data from the game object and shows it in the testArea
    public void showInfo(Game game) {
        textArea.setText(
                "Thank you for playing ChopMaster!\nYour Final score was " + game.getInventory().calcEco(game.getTiles()) +
                        "\nYou have chopped " + game.getInventory().getWoodChopped() + " trees\nand planted " + game.getInventory().getTreesPlanted() + " trees" +
                        "\n\nThe final score reflects how sustainably you have chopped.\nThe more ecologically friendly trees planted,\nthe higher the eco-score.\n"
                        + "\nSustainable review: " + sustainReview(game));
    }

    //sets cancel button visability
    public void setCancelButtonVisable(boolean value) {
        cancel.setVisible(value);
    }

    //setters
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    //buttons
    @FXML
    private void onQuitButtonPressed(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    private void onRestartButtonPressed(ActionEvent event) {
        mainController.start(new Game());
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(mainScene);

        this.mainController.onInfoBoxClicked(null);
        setCancelButtonVisable(true);
    }

    @FXML
    private void onCancelButtonPressed(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(mainScene);
    }
}
