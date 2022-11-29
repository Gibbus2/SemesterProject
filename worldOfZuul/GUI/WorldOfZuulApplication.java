package worldOfZuul.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import worldOfZuul.domain.game.Game;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class WorldOfZuulApplication extends Application {
   
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("ChopMaster");

        try {
            // map scene
            FXMLLoader mainLoader = new FXMLLoader(WorldOfZuulApplication.class.getResource("mainView.fxml"));
            Parent mainPane = mainLoader.load();
            Scene mainScene = new Scene(mainPane);

            // help scene
            FXMLLoader helpLoader = new FXMLLoader(WorldOfZuulApplication.class.getResource("helpView.fxml"));
            Parent helpPane = helpLoader.load();
            Scene helpScene = new Scene(helpPane);

            // get controllers
            MainController mainController = (MainController) mainLoader.getController();
            HelpController helpController = (HelpController) helpLoader.getController();
            
            helpController.setMainScene(mainScene);
            mainController.setHelpScene(helpScene);

            //set scene in stage
            primaryStage.setScene(helpScene);
            primaryStage.show();
            primaryStage.setResizable(false);

            //start the game
            Game game = new Game();
            mainController.start(game);

        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
}
