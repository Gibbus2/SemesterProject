package worldOfZuul.GUI;

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
import worldOfZuul.domain.game.Game;
import javafx.scene.control.*;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import worldOfZuul.domain.tiles.forests.OakForest;
import worldOfZuul.domain.tiles.forests.PineForest;
import worldOfZuul.domain.tiles.forests.JungleForest;


public class MainController implements Initializable {
    @FXML
    private GridPane map;

    @FXML
    private SplitPane pane;

    @FXML
    private Text ecoScore, money, trees, saplings, turnsLeft, chopped, saplingGrowthTimer;

    @FXML
    private Text ecoScoreText, moneyText, treesText, saplingsText, turnsLeftText, choppedText, SaplingGrowthTimerText;

    @FXML
    private Button endGame, goNorth, goEast, goSouth, goWest, plant, chop;

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
        oak = new Image("worldOfZuul/GUI/resources/oaktree.png");
        pine = new Image("worldOfZuul/GUI/resources/pinetree.png");
        jungle = new Image("worldOfZuul/GUI/resources/jungletree.png");

        oakSapling = new Image("worldOfZuul/GUI/resources/oaksapling.png");
        pineSapling = new Image("worldOfZuul/GUI/resources/pinesapling.png");
        jungleSapling = new Image("worldOfZuul/GUI/resources/junglesapling.png");

        stump = new Image("worldOfZuul/GUI/resources/stump.png");


        infoBox.setVisible(false);


        this.setCloudAnimation(jungleLongCloud);
        this.setCloudAnimation(oakLongCloud);
        this.setCloudAnimation(pineLongCloud);

        this.update = new Update();
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
        // updateMap();
        this.update.updateMap(game, tileData);
        // updateInfo();
        this.update.updateInfo(game, ecoScore, money, trees, saplings, turnsLeft, chopped, saplingGrowthTimer);
        // updateGoButtons();
        this.update.updateGoButtons(game, goNorth, goEast, goSouth, goWest);
        // updateBackground();
        this.update.updateBackground(game, pineSky, oakSky, jungleSky, oakLongCloud, pineLongCloud, jungleLongCloud);
        updateForest();
        updateInfobox();
    }

    private void updateInfobox() {

        if (game.getTick() == 10 || game.getTick() == 20 || game.getTick() == 30 ||
                (game.getTick() <= 10 && game.getInventory().getWoodChopped() >= 150) ||
                (game.getTick() <= 20 && game.getInventory().getWoodChopped() >= 400) ||
                (game.getTick() <= 30 && game.getInventory().getWoodChopped() >= 1000)) {
            infoBox.setVisible(false); //when the sub-goal is succeeded it will remove infobox
        }       // so it is ready for next infobox. If reach goal before sub-goal round it also disappear.


        if (infoBox.isVisible()) {
            return; //if infobox is visible it will go to the top of method again and check the 1st if-statement.
        }

        if ((game.getTick() == 7) && game.getInventory().getWoodChopped() < 150) { //1st sub-goal.
            infoBox.setVisible(true);
            infoBox.setText("IKEA demands 150 trees by round 10. GET TO WORK!"+"\n"+"        (Click to minimize)");
        } else if (game.getTick() == 15 && game.getInventory().getWoodChopped() < 400) { //2nd sub-goal.
            infoBox.setVisible(true);
            infoBox.setText("IKEA demands 450 trees by round 20. YOU CAN DO IT!"+"\n"+"        (Click to minimize)");
        } else if (game.getTick() == 25 && game.getInventory().getWoodChopped() < 1000) { //3rd sub-goal.
            infoBox.setVisible(true);
            infoBox.setText("IKEA demands 1000 trees by round 30. HURRY UP!"+"\n"+"        (Click to minimize)");
        } else
            infoBox.setText(null);
    }


    public void onInfoBoxClicked(ActionEvent actionEvent) {
        infoBox.setVisible(false); // When clicked on infobox, at any time it will disappear.
    }

    private void updateBackground() {
        pineSky.setVisible(false);
        oakSky.setVisible(false);
        jungleSky.setVisible(false);
        oakLongCloud.setVisible(false);
        pineLongCloud.setVisible(false);
        jungleLongCloud.setVisible(false);

        if (game.getCurrentTile().getForest().getClass() == OakForest.class) {
            oakSky.setVisible(true);
            oakLongCloud.setVisible(true);
        }else if (game.getCurrentTile().getForest().getClass() == PineForest.class) {
            pineSky.setVisible(true);
            pineLongCloud.setVisible(true);

        }else if (game.getCurrentTile().getForest().getClass() == JungleForest.class) {
            jungleSky.setVisible(true);
            jungleLongCloud.setVisible(true);

        }


    }


    @FXML
    private void updateForest() {
        int treePop = this.game.getCurrentTile().getForest().getTreePop();
        int saplingPop = this.game.getCurrentTile().getForest().getSaplingPop();

        for (ImageView treeView : this.treeViews) {
            Image image;
            if (treePop >= 10) {
                if (game.getCurrentTile().getForest().getClass() == OakForest.class) {
                    image = this.oak;
                } else if (game.getCurrentTile().getForest().getClass() == PineForest.class) {
                    image = this.pine;
                } else {
                    image = this.jungle;
                }
                treePop = treePop - 10;
            } else if (saplingPop >= 10) {
                if (game.getCurrentTile().getForest().getClass() == OakForest.class) {
                    image = this.oakSapling;
                } else if (game.getCurrentTile().getForest().getClass() == PineForest.class) {
                    image = this.pineSapling;
                } else {
                    image = this.jungleSapling;
                }
                saplingPop = saplingPop - 10;
            } else {
                image = this.stump;
            }

            treeView.setImage(image);
        }
    }

    private void updateMap() {
        int labelIndex = 0;
        for (int i = 0; i < game.getTiles().length; i++) {
            for (int j = 0; j < game.getTiles().length; j++) {
                if (game.getTiles()[j][i] == game.getCurrentTile()) {
                    tileData[labelIndex].setText("X");
                } else {
                    tileData[labelIndex].setText(String.format("%03d", game.getTiles()[j][i].getForest().getTreePop()));
                }
                labelIndex++;
            }
        }
    }

    private void updateInfo() {
        this.ecoScore.setText("" + this.game.getInventory().calcEco(game.getTiles()));
        this.money.setText("" + this.game.getInventory().getMoneyScore());

        this.trees.setText("" + game.getCurrentTile().getForest().getTreePop());
        this.saplings.setText("" + game.getCurrentTile().getForest().getSaplingPop());

        this.turnsLeft.setText("" + (Game.maxTicks - game.getTick()));
        this.chopped.setText("" + game.getInventory().getWoodChopped());

        this.SaplingGrowthTimer.setText("" + game.getCurrentTile().getForest().getSaplingTurnsLeft());
    }

    private void updateGoButtons() {
        goNorth.setDisable(game.getCurrentTile().getExit("north") == null);
        goEast.setDisable(game.getCurrentTile().getExit("east") == null);
        goSouth.setDisable(game.getCurrentTile().getExit("south") == null);
        goWest.setDisable(game.getCurrentTile().getExit("west") == null);
    }

    public void start(Game game) {
        this.game = game;
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