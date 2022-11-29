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
            // map scene
            FXMLLoader mapLoader = new FXMLLoader(WorldOfZuulApplication.class.getResource("mapView.fxml"));
            Parent mapPane = mapLoader.load();
            Scene mapScene = new Scene(mapPane);

            // help scene
            FXMLLoader helpLoader = new FXMLLoader(WorldOfZuulApplication.class.getResource("helpView.fxml"));
            Parent helpPane = helpLoader.load();
            Scene helpScene = new Scene(helpPane);

            // get controllers
            MapController mapController = (MapController) mapLoader.getController();
            HelpController helpController = (HelpController) helpLoader.getController();

            helpController.setMapScene(mapScene);
            helpController.setHelpScene(helpScene);

            mapController.setMapScene(mapScene);
            mapController.setHelpScene(helpScene);

            primaryStage.setScene(helpScene);
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
