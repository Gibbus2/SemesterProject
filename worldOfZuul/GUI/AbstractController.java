package worldOfZuul.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractController {
    // attributes
    private Scene mapScene;
    private Scene helpScene;

    // methods
    // open help and map scenes.
    public void openMapScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.mapScene);
    }

    public void openHelpScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.mapScene);
    }

    // getters and setters
    public Scene getMapScene() {
        return mapScene;
    }

    public void setMapScene(Scene mapScene) {
        this.mapScene = mapScene;
    }

    public Scene getHelpScene() {
        return helpScene;
    }

    public void setHelpScene(Scene helpScene) {
        this.helpScene = helpScene;
    }
}
