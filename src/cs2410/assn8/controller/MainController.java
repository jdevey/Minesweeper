package cs2410.assn8.controller;

import cs2410.assn8.model.LogicCenter;
import cs2410.assn8.view.Cell;
import cs2410.assn8.view.Scoreboard;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Random;

public class MainController {

    public GridPane gPane = new GridPane();

    public ArrayList<ArrayList<Cell>> fullGrid = new ArrayList<>();

    @FXML
    BorderPane main;

    @FXML
    private HBox hBox;

    @FXML
    public HBoxController hBoxController;

    @FXML
    private MenuBar menu;

    @FXML
    public MenuController menuController;

    private MainController mainController;

    private LogicCenter logicController = new LogicCenter();

    private Scoreboard scoreboardController;

    public int size = 20;
    public int numBomb = 15;
    public int correctCount;

    int gameType;
    public boolean won;

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
        fullGrid.clear();
        gPane.getChildren().clear();
        gPane.setHgap(2);
        gPane.setVgap(2);

        for (int i = 0; i < size; ++i) {
            fullGrid.add(new ArrayList<>());
            for (int j = 0; j < size; ++j) {
                fullGrid.get(i).add(new Cell(mainController, scoreboardController));
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
        main.getChildren().remove(menu);
        main.getChildren().remove(hBox);
        main.setTop(menu);
        main.setCenter(hBox);
        hBox.setPrefHeight(30);
    }

    public void play(MainController _mainController) {
        this.mainController = _mainController;
        initGrid();
        setPaneBottom(gPane);
        initPosition();
        logicController.beginPlaying(mainController, scoreboardController, size, numBomb);
    }
}
