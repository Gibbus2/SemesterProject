package worldOfZuul.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController extends AbstractController {

    @FXML
    private void onMapButtonPressed (ActionEvent event) {
        openMapScene(event);
    }
}
