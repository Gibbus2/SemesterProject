package worldOfZuul.GUI;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class FXMLDocumentController implements Initializable{
    @FXML
    private GridPane map;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // map.add(new Label("test"), 0, 0);
        System.out.println(map.getRowCount());
        
    }


}
