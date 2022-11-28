package worldOfZuul.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class WorldOfZuulApplication extends Application {
    private static Stage primaryStage;
   
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WorldOfZuulApplication.primaryStage = primaryStage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    static public void quit(){
        WorldOfZuulApplication.primaryStage.close();
    }
}
