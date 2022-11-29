package worldOfZuul.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import javafx.scene.Node;
import javafx.stage.Stage;

public class HelpController {

    private Scene mainScene;

    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }

    @FXML
    private void onMapButtonPressed (ActionEvent event) {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(this.mainScene);
    }
}
