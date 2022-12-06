package ChopMaster.GUI;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.util.Duration;
import ChopMaster.domain.game.Game;
import javafx.scene.control.*;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ChopMaster.domain.tiles.forests.OakForest;
import ChopMaster.domain.tiles.forests.PineForest;


public class MainController implements Initializable {
    @FXML
    private GridPane map;

    @FXML
    private SplitPane pane;

    @FXML
    private Text ecoScore, money, trees, saplings, turnsLeft, chopped, saplingGrowthTimer;

    @FXML
    private Button endGame, goNorth, goEast, goSouth, goWest;

    @FXML
    private Button infoBox;

    @FXML
    private TextField input;

    @FXML
    private ImageView oakSky, jungleSky, pineSky, oakLongCloud, pineLongCloud, jungleLongCloud;

    // handles tree view on background.
    @FXML
    private ImageView treeView0, treeView1, treeView2, treeView3, treeView4, treeView5, treeView6, treeView7, treeView8, treeView9;

    private ImageView[] treeViews;

    private Game game;
    private Text[] tileData;
    private Scene helpScene, gameOverScene;
    private GameOverController gameOverController;

    private Image oak, pine, jungle, oakSapling, pineSapling, jungleSapling, stump;

    private Update update;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tileData = new Text[map.getColumnCount() * map.getRowCount()];

        treeViews = new ImageView[]{treeView0, treeView1, treeView2, treeView3, treeView4, treeView5, treeView6, treeView7, treeView8, treeView9};
        oak = new Image("ChopMaster/GUI/resources/oaktree.png");
        pine = new Image("ChopMaster/GUI/resources/pinetree.png");
        jungle = new Image("ChopMaster/GUI/resources/jungletree.png");

        oakSapling = new Image("ChopMaster/GUI/resources/oaksapling.png");
        pineSapling = new Image("ChopMaster/GUI/resources/pinesapling.png");
        jungleSapling = new Image("ChopMaster/GUI/resources/junglesapling.png");
        
        stump = new Image("ChopMaster/GUI/resources/stump.png");
   


        infoBox.setVisible(false);


        this.setCloudAnimation(jungleLongCloud);
        this.setCloudAnimation(oakLongCloud);
        this.setCloudAnimation(pineLongCloud);
    }



    // button events
    @FXML
    private void onPlantButtonPressed(ActionEvent event) {
        game.getCurrentTile().getForest().plant(input.getText(), game.getInventory());
        updateAll();
    }

    @FXML
    private void onChopButtonPressed(ActionEvent event) {
        game.getCurrentTile().getForest().chop(input.getText(), game.getInventory());
        updateAll();
    }

    @FXML
    private void onGoNorthButtonPressed(ActionEvent event) {
        goTile("north");
    }

    @FXML
    private void onGoEastButtonPressed(ActionEvent event) {
        goTile("east");
    }

    @FXML
    private void onGoSouthButtonPressed(ActionEvent event) {
        goTile("south");
    }

    @FXML
    private void onGoWestButtonPressed(ActionEvent event) {
        goTile("west");
    }

    @FXML
    private void onHelpButtonPressed(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(helpScene);
    }

    @FXML
    private void onEndGameButtonPressed(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(gameOverScene);
        gameOverController.showInfo(game);
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent event) {
        switch (event.getText()) {
            case "w":
                goTile("north");
                break;
            case "d":
                goTile("east");
                break;
            case "s":
                goTile("south");
                break;
            case "a":
                goTile("west");
                break;
            case "c":
                game.getCurrentTile().getForest().chop(input.getText(), game.getInventory());
                updateAll();
                break;
            case "p":
                game.getCurrentTile().getForest().plant(input.getText(), game.getInventory());
                updateAll();
                break;

        }
    }

    // setters
    public void setHelpScene(Scene helpScene) {
        this.helpScene = helpScene;
    }

    public void setGameOverScene(Scene gameOversScene) {
        this.gameOverScene = gameOversScene;
    }

    public void setGameOverController(GameOverController gameOverController) {
        this.gameOverController = gameOverController;
    }

    private void goTile(String direction) {
        game.goTile(game.getCommand("go", direction));
        updateAll();
        if (game.isGameFinished()) {
            // end game by creating new action event and pass it to end game button
            onEndGameButtonPressed(new ActionEvent(pane, endGame));
            gameOverController.setCancelButtonVisable(false);
        }
    }

    private void updateAll() {
        this.update.map(tileData);
        this.update.info(ecoScore, money, trees, saplings, turnsLeft, chopped, saplingGrowthTimer);
        this.update.goButtons(goNorth, goEast, goSouth, goWest);
        this.update.background(pineSky, oakSky, jungleSky, oakLongCloud, pineLongCloud, jungleLongCloud);
        this.update.forest(treeViews, oak, pine, jungle, oakSapling, pineSapling, jungleSapling, stump);
        this.update.infobox(infoBox);
    }


    public void onInfoBoxClicked(ActionEvent actionEvent) {
        infoBox.setVisible(false); // When clicked on infobox, at any time it will disappear.
    }

    public void start(Game game) {
        this.game = game;
        this.update = new Update(game);
        map.getChildren().clear();
        setImage();
        createTextFields();
        updateAll();
    }

    private void setImage() {
        for (int i = 0; i < map.getColumnCount(); i++) {
            for (int j = 0; j < map.getRowCount(); j++) {
                ImageView view = new ImageView();
                view.setFitWidth(40);
                view.setFitHeight(40);
                view.setOpacity(0.5);

                if (game.getTiles()[j][i].getForest().getClass() == OakForest.class) {
                    view.setImage(this.oak);
                } else if (game.getTiles()[j][i].getForest().getClass() == PineForest.class) {
                    view.setImage(this.pine);
                } else {
                    view.setImage(this.jungle);
                }

                map.add(view, j, i);
            }
        }
    }

    private void createTextFields() {
        int labelIndex = 0;

        for (int i = 0; i < map.getColumnCount(); i++) {
            for (int j = 0; j < map.getRowCount(); j++) {
                tileData[labelIndex] = new Text("" + j + ":" + i);
                tileData[labelIndex].getStyleClass().add("text-normal");
                map.add(tileData[labelIndex], j, i);
                GridPane.setHalignment(tileData[labelIndex], HPos.CENTER);
                GridPane.setValignment(tileData[labelIndex], VPos.CENTER);
                labelIndex++;
            }
        }
    }

    private void setCloudAnimation(ImageView cloud){
        TranslateTransition cloudAnimation = new TranslateTransition();
        cloudAnimation.setNode(cloud);
        cloudAnimation.setDuration(Duration.millis(25069));
        cloudAnimation.setCycleCount(TranslateTransition.INDEFINITE);
        cloudAnimation.setByX(640);
        cloudAnimation.setInterpolator(Interpolator.LINEAR);
        cloudAnimation.play();
    }

}