package worldOfZuul.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
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
            // hello
            // map scene
            FXMLLoader mainLoader = new FXMLLoader(WorldOfZuulApplication.class.getResource("mainView.fxml"));
            Parent mainPane = mainLoader.load();
            Scene mainScene = new Scene(mainPane);

            // help scene
            FXMLLoader helpLoader = new FXMLLoader(WorldOfZuulApplication.class.getResource("helpView.fxml"));
            Parent helpPane = helpLoader.load();
            Scene helpScene = new Scene(helpPane);

            //gameover scene
            FXMLLoader gameOverLoader = new FXMLLoader(WorldOfZuulApplication.class.getResource("gameOverView.fxml"));
            Parent gameOverPane = gameOverLoader.load();
            Scene gameOverScene = new Scene(gameOverPane);

            // get controllers
            MainController mainController = (MainController) mainLoader.getController();
            HelpController helpController = (HelpController) helpLoader.getController();
            GameOverController gameOverController = (GameOverController) gameOverLoader.getController();

            //settes for helpController
            helpController.setMainScene(mainScene);
            
            //setters for mainController
            mainController.setHelpScene(helpScene);
            mainController.setGameOverScene(gameOverScene);
            mainController.setGameOverController(gameOverController);
            
            //setters for gameOverController
            gameOverController.setMainScene(mainScene);
            gameOverController.setMainController(mainController);

            //set scene in stage
            primaryStage.setScene(helpScene);
            primaryStage.show();
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("worldOfZuul/GUI/resources/oaktree.png"));

            //start the game
            Game game = new Game();
            mainController.start(game);

        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
}
