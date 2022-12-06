package worldOfZuul.GUI;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private Text ecoScore, money, trees, saplings, turnsLeft, chopped, SaplingGrowthTimer;

    @FXML
    private Button endGame, goNorth, goEast, goSouth, goWest, plant, chop;

    @FXML
    private Button infoBox;

    @FXML
    private TextField input;

    @FXML
    private ImageView oakBackground, pineBackground, jungleBackground;

    // handles tree view on background.
    @FXML
    private ImageView treeView0, treeView1, treeView2, treeView3, treeView4, treeView5, treeView6, treeView7, treeView8, treeView9;

    private ImageView[] treeViews;

    private Game game;
    private Text[] tileData;
    private Scene helpScene, gameOverScene;
    private GameOverController gameOverController;

    private Image oak, pine, jungle, oakSapling, pineSapling, jungleSapling, stump;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tileData = new Text[map.getColumnCount() * map.getRowCount()];

        int labelIndex = 0;

        treeViews = new ImageView[]{treeView0, treeView1, treeView2, treeView3, treeView4, treeView5, treeView6, treeView7, treeView8, treeView9};
        oak = new Image("worldOfZuul/GUI/resources/oaktree.png");
        pine = new Image("worldOfZuul/GUI/resources/pinetree.png");
        jungle = new Image("worldOfZuul/GUI/resources/jungletree.png");

        oakSapling = new Image("worldOfZuul/GUI/resources/oaksapling.png");
        pineSapling = new Image("worldOfZuul/GUI/resources/pinesapling.png");
        jungleSapling = new Image("worldOfZuul/GUI/resources/junglesapling.png");

        stump = new Image("worldOfZuul/GUI/resources/stump.png");


        for (int i = 0; i < map.getColumnCount(); i++) {
            for (int j = 0; j < map.getRowCount(); j++) {
                tileData[labelIndex] = new Text("" + j + ":" + i);
                map.add(tileData[labelIndex], j, i);
                GridPane.setHalignment(tileData[labelIndex], HPos.CENTER);
                GridPane.setValignment(tileData[labelIndex], VPos.CENTER);
                labelIndex++;
            }
        }

        infoBox.setVisible(false);
    }

    // button events
    @FXML
    private void onPlantButtonPressed(ActionEvent event) {
        game.getCurrentRoom().getForest().plant(input.getText(), game.getInventory());
        updateAll();
    }

    @FXML
    private void onChopButtonPressed(ActionEvent event) {
        game.getCurrentRoom().getForest().chop(input.getText(), game.getInventory());
        updateAll();
    }

    @FXML
    private void onGoNorthButtonPressed(ActionEvent event) {
        goRoom("north");
    }

    @FXML
    private void onGoEastButtonPressed(ActionEvent event) {
        goRoom("east");
    }

    @FXML
    private void onGoSouthButtonPressed(ActionEvent event) {
        goRoom("south");
    }

    @FXML
    private void onGoWestButtonPressed(ActionEvent event) {
        goRoom("west");
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
                goRoom("north");
                break;
            case "d":
                goRoom("east");
                break;
            case "s":
                goRoom("south");
                break;
            case "a":
                goRoom("west");
                break;
            case "c":
                game.getCurrentRoom().getForest().chop(input.getText(), game.getInventory());
                updateAll();
                break;
            case "p":
                game.getCurrentRoom().getForest().plant(input.getText(), game.getInventory());
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

    private void goRoom(String direction) {
        game.goRoom(game.getCommand("go", direction));
        updateAll();
        updateInfobox();
        if (game.isGameFinished()) {
            // end game by creating new action event and pass it to end game button
            onEndGameButtonPressed(new ActionEvent(pane, endGame));
            gameOverController.setCancelButtonVisable(false);
        }
    }

    private void updateAll() {
        updateMap();
        updateInfo();
        updateGoButtons();
        updateBackground();
        updateForest();

    }

    private void updateInfobox() {

        if      (game.getTick() == 10 || game.getTick() == 20 || game.getTick() == 30 ||
                (game.getTick() <= 10 && game.getInventory().getWoodChopped() >= 150) ||
                (game.getTick() <= 20 && game.getInventory().getWoodChopped() >= 400) ||
                (game.getTick() <= 30 && game.getInventory().getWoodChopped() >= 1000)){
                infoBox.setVisible(false);
        }

        if (infoBox.isVisible()) {
            return;
        }

        if ((game.getTick() == 7) && game.getInventory().getWoodChopped() < 150) {
            infoBox.setVisible(true);
            infoBox.setText("IKEA demand 150 trees by round 10. GET TO WORK!");
        } else if (game.getTick() == 15 && game.getInventory().getWoodChopped() < 400) {
            infoBox.setVisible(true);
            infoBox.setText("IKEA demand 450 trees by round 20");
        } else if (game.getTick() == 25 && game.getInventory().getWoodChopped() < 1000) {
            infoBox.setVisible(true);
            infoBox.setText("IKEA demand 1000 trees by round 30. HURRY UP!");
        } else
            infoBox.setText(null);
    }


        public void onInfoBoxClicked (ActionEvent actionEvent){
            infoBox.setVisible(false);
        }


        private void updateBackground () {
            oakBackground.setVisible(false);
            pineBackground.setVisible(false);
            jungleBackground.setVisible(false);

            if (game.getCurrentRoom().getForest().getClass() == OakForest.class) {
                oakBackground.setVisible(true);
            }
            if (game.getCurrentRoom().getForest().getClass() == PineForest.class) {
                pineBackground.setVisible(true);
            }
            if (game.getCurrentRoom().getForest().getClass() == JungleForest.class) {
                jungleBackground.setVisible(true);
            }


        }

        @FXML
        private void updateForest () {
            int treePop = this.game.getCurrentRoom().getForest().getTreePop();
            int saplingPop = this.game.getCurrentRoom().getForest().getSaplingPop();

            for (ImageView treeView : this.treeViews) {
                Image image;
                if (treePop >= 10) {
                    if (game.getCurrentRoom().getForest().getClass() == OakForest.class) {
                        image = this.oak;
                    } else if (game.getCurrentRoom().getForest().getClass() == PineForest.class) {
                        image = this.pine;
                    } else {
                        image = this.jungle;
                    }
                    treePop = treePop - 10;
                } else if (saplingPop >= 10) {
                    if (game.getCurrentRoom().getForest().getClass() == OakForest.class) {
                        image = this.oakSapling;
                    } else if (game.getCurrentRoom().getForest().getClass() == PineForest.class) {
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

        private void updateMap () {
            int labelIndex = 0;
            for (int i = 0; i < game.getRooms().length; i++) {
                for (int j = 0; j < game.getRooms().length; j++) {
                    if (game.getRooms()[j][i] == game.getCurrentRoom()) {
                        tileData[labelIndex].setText("X");
                    } else {
                        tileData[labelIndex].setText(String.format("%03d", game.getRooms()[j][i].getForest().getTreePop()));
                    }
                    labelIndex++;
                }
            }
        }

        private void updateInfo () {
            this.ecoScore.setText("" + this.game.getInventory().calcEco(game.getRooms()));
            this.money.setText("" + this.game.getInventory().getMoneyScore());

            this.trees.setText("" + game.getCurrentRoom().getForest().getTreePop());
            this.saplings.setText("" + game.getCurrentRoom().getForest().getSaplingPop());

            this.turnsLeft.setText("" + (Game.maxTicks - game.getTick()));
            this.chopped.setText("" + game.getInventory().getWoodChopped());

            this.SaplingGrowthTimer.setText("" + game.getCurrentRoom().getForest().getSaplingTurnsLeft());
        }

        private void updateGoButtons () {
            goNorth.setDisable(game.getCurrentRoom().getExit("north") == null);
            goEast.setDisable(game.getCurrentRoom().getExit("east") == null);
            goSouth.setDisable(game.getCurrentRoom().getExit("south") == null);
            goWest.setDisable(game.getCurrentRoom().getExit("west") == null);
        }

        public void start (Game game){
            this.game = game;
            map.getChildren().clear();
            setImage();
            createTextFields();
            updateAll();
        }

        private void setImage () {
            for (int i = 0; i < map.getColumnCount(); i++) {
                for (int j = 0; j < map.getRowCount(); j++) {
                    ImageView view = new ImageView();
                    view.setFitWidth(40);
                    view.setFitHeight(40);
                    view.setOpacity(0.5);

                    if (game.getRooms()[j][i].getForest().getClass() == OakForest.class) {
                        view.setImage(this.oak);
                    } else if (game.getRooms()[j][i].getForest().getClass() == PineForest.class) {
                        view.setImage(this.pine);
                    } else {
                        view.setImage(this.jungle);
                    }

                    map.add(view, j, i);
                }
            }
        }

        private void createTextFields () {
            int labelIndex = 0;

            for (int i = 0; i < map.getColumnCount(); i++) {
                for (int j = 0; j < map.getRowCount(); j++) {
                    tileData[labelIndex] = new Text("" + j + ":" + i);
                    map.add(tileData[labelIndex], j, i);
                    GridPane.setHalignment(tileData[labelIndex], HPos.CENTER);
                    GridPane.setValignment(tileData[labelIndex], VPos.CENTER);
                    labelIndex++;
                }
            }
        }

    }