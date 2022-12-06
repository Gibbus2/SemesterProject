package ChopMaster.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ChopMaster.domain.game.Game;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ChopMasterGUIApplication extends Application {

    // public static Font font;

    public static void main(String[] args) {
        // WorldOfZuulApplication.font = Font.loadFont("worldOfZuul/GUI/resources/fonts/m5x7.ttf", 45);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("ChopMaster");

        try {


            // map scene
            FXMLLoader mainLoader = new FXMLLoader(ChopMasterGUIApplication.class.getResource("mainView.fxml"));
            Parent mainPane = mainLoader.load();
            Scene mainScene = new Scene(mainPane);
            mainScene.getStylesheets().add("/ChopMaster/GUI/resources/style.css");


            // help scene
            FXMLLoader helpLoader = new FXMLLoader(ChopMasterGUIApplication.class.getResource("helpView.fxml"));
            Parent helpPane = helpLoader.load();
            Scene helpScene = new Scene(helpPane);
            helpScene.getStylesheets().add("/ChopMaster/GUI/resources/style.css");


            //gameover scene
            FXMLLoader gameOverLoader = new FXMLLoader(ChopMasterGUIApplication.class.getResource("gameOverView.fxml"));
            Parent gameOverPane = gameOverLoader.load();
            Scene gameOverScene = new Scene(gameOverPane);
            gameOverScene.getStylesheets().add("/ChopMaster/GUI/resources/style.css");

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
            primaryStage.getIcons().add(new Image("ChopMaster/GUI/resources/oaktree.png"));

            //start the game
            Game game = new Game();
            mainController.start(game);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
