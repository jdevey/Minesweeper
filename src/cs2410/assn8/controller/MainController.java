package cs2410.assn8.controller;

import cs2410.assn8.model.LogicCenter;
import cs2410.assn8.view.Cell;
import cs2410.assn8.view.Scoreboard;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MainController {

    public Stage stage;

    public GridPane gPane = new GridPane();

    public ArrayList<ArrayList<Cell>> fullGrid = new ArrayList<>();

    @FXML
    BorderPane main;

    @FXML
    private HBox hBox;

    @FXML
    public HBoxController hBoxController;

    @FXML
    public MenuBar menuBar;

    @FXML
    public MenuController menuController;

    private MenuBar menuBar1 = new MenuBar();
    ToggleGroup toggleGroup = new ToggleGroup();
    private Menu menu1 = new Menu("Gamemode");
    private RadioMenuItem normalMode1 = new RadioMenuItem("Normal Mode");
    private RadioMenuItem speedDemon1 = new RadioMenuItem("Speed Demon");
    private RadioMenuItem timesUp1 = new RadioMenuItem("Time's Up");

    private MainController mainController;

    private LogicCenter logicController = new LogicCenter();

    private Scoreboard scoreboardController;

    public boolean setupDone;
    public int size = 20;
    public int numBomb = 100;
    public int correctCount;

    public int gameType = 0;
    public boolean won;
    public boolean hasStarted;

    public Timer timer;
    public int currTime = 0;

    public HBoxController getHBoxController() {
        return hBoxController;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public LogicCenter getLogicController() {
        return logicController;
    }

    private void initGrid() {
        scoreboardController = new Scoreboard(numBomb);
        fullGrid.clear();
        gPane.getChildren().clear();
        //gPane.setHgap(2);
        //gPane.setVgap(2);

        for (int i = 0; i < size; ++i) {
            fullGrid.add(new ArrayList<>());
            for (int j = 0; j < size; ++j) {
                fullGrid.get(i).add(new Cell(mainController, scoreboardController, hBoxController));
            }
        }

        //Shuffling
        int bombTemp = numBomb;
        while (bombTemp > 0) {
            Random rand = new Random();

            int randX = rand.nextInt(size);
            int randY = rand.nextInt(size);

            if (!(fullGrid.get(randX).get(randY).getIsBomb())) {
                fullGrid.get(randX).get(randY).setIsBomb(true); //Means that these are bombs
                --bombTemp;
            }
        }

        //Now that everything is shuffled, we can assign an "i" and "j" value to each cell.
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                fullGrid.get(i).get(j).setIJ(i, j);
            }
        }

        //Adding stuff to the gridpane
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                gPane.add(fullGrid.get(i).get(j), fullGrid.get(i).get(j).getJ(), fullGrid.get(i).get(j).getI());
            }
        }
    }

    private void setPaneBottom(Node node) {
        main.setBottom(node);
        main.setAlignment(node, Pos.CENTER);
        main.setMargin(node, new Insets(0, 0, 10, 10));
    }

    private void initPosition() {
        main.getChildren().remove(hBox);
        main.setCenter(hBox);
        hBox.setPrefHeight(30);
    }

    public void setup(MainController _mainController, Stage primaryStage) {
        this.mainController = _mainController;
        this.stage = primaryStage;
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Hello! Welcome to Minesweeperish! \n\n" +
                        "(30 points, 2 new game types)\n\nIn this " +
                        "version of Minesweeperish, you " +
                        "can choose from one of three game types: \n\n" +
                        "1. Normal Mode \n" +
                        "2. Speed Demon \n" +
                        "3. Time's Up \n\n" +
                        "Normal mode is simply classic minesweeper. " +
                        "Speed Demon Mode requires the player to choose " +
                        "at least one new cell every ten seconds, or they " +
                        "lose! Finally, in Time's Up mode, the user has only " +
                        "sixty seconds to complete the game, or they lose! \n\n" +
                        "See the README document for other useful information."

        );
        alert.showAndWait();
        initMenu();
        play();
    }

    public void play() {
        initGrid();
        setPaneBottom(gPane);
        initPosition();
        logicController.beginPlaying(mainController, scoreboardController, size, numBomb);
    }

    private void initMenu() {
        menu1.getItems().addAll(normalMode1, speedDemon1, timesUp1);
        menuBar1.getMenus().add(menu1);
        main.setTop(menuBar1);
        normalMode1.setOnAction(e -> gameType = 0);
        speedDemon1.setOnAction(e -> gameType = 1);
        timesUp1.setOnAction(e -> gameType = 2);
        normalMode1.setToggleGroup(toggleGroup);
        speedDemon1.setToggleGroup(toggleGroup);
        timesUp1.setToggleGroup(toggleGroup);

        normalMode1.setSelected(true);
    }
}
