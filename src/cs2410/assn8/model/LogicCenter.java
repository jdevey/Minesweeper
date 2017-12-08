package cs2410.assn8.model;

import cs2410.assn8.controller.MainController;
import cs2410.assn8.view.Scoreboard;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import java.util.Timer;

import java.lang.Math.*;

/**
 * Deals with many of the more complex logical operations found in the program
 *
 * @author devey
 * @version 1.0
 */
public class LogicCenter {
    /**
     * size of grid
     */
    private int size;
    /**
     * number of bombs
     */
    private int numBomb;
    /**
     * main controller
     */
    private MainController mainController;
    /**
     * scoreboard controller
     */
    private Scoreboard scoreboardController;

    /**
     * begins a new game of minesweeperish
     * @param _mainController controller
     * @param _scoreboardController controller
     * @param _size size
     * @param _numBomb number of bombs
     */
    public void beginPlaying(MainController _mainController, Scoreboard _scoreboardController, int _size, int _numBomb) {

        size = _size;
        numBomb = _numBomb;
        mainController = _mainController;
        mainController.setupDone = false;
        mainController.hasStarted = false;
        scoreboardController = _scoreboardController;
        mainController.won = true;

        if (mainController.timer != null)mainController.timer.cancel();
        mainController.timer = new Timer(true);

        if (mainController.gameType == 0) {
            mainController.stage.setTitle("Minesweeperish - Normal Mode");
            mainController.currTime = 0;
            mainController.getHBoxController().timeTickLabel.setText("0");
        }
        else if (mainController.gameType == 1) {
            mainController.stage.setTitle("Minesweeperish - Speed Demon");
            mainController.currTime = 10;
            mainController.getHBoxController().timeTickLabel.setText("10");
        }
        else {
            mainController.stage.setTitle("Minesweeperish - Time's Up");
            mainController.currTime = 60;
            mainController.getHBoxController().timeTickLabel.setText("60");
        }

        mainController.getHBoxController().startBtn.setOnAction(e -> mainController.play());
        mainController.correctCount = size * size - numBomb;
        scoreboardController.setBombsRemaining(numBomb);
        mainController.getHBoxController().bombsTickLabel.setText(((Integer)(scoreboardController.getBombsRemaining())).toString());

        //Making sure all the bombs are placed and shuffled, color is returned to normal, and flag states are returned to default
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                mainController.fullGrid.get(i).get(j).setClickState(2);
                mainController.fullGrid.get(i).get(j).setIsClicked(false);
                mainController.gPane.getChildren().get(size * i + j).setStyle("" +
                        "-fx-background-color: lightblue;" +
                        " -fx-border-color: black" +
                        "");
            }
        }
        mainController.setupDone = true;
    }

    /**
     * Handles cell clicks
     * @param i int
     * @param j int
     */
    public void cellClick(int i, int j) {
        mainController.fullGrid.get(i).get(j).setIsClicked(true);
        mainController.gPane.getChildren().get(i * size + j).setDisable(true);
        if (mainController.fullGrid.get(i).get(j).getIsBomb()) {
            mainController.won = false;
            //mainController.gPane.getChildren().get(i * size + j).setStyle("-fx-background-color: gold");
            gameEnds();
        }
        else {
            recurseFill(i, j);
        }
    }

    /**
     * Recursively goes out from a point
     * @param i int
     * @param j int
     */
    private void recurseFill(int i, int j) {
        if (!mainController.fullGrid.get(i).get(j).getIsBomb()) {
            //scoreboardController.setBombsRemaining(scoreboardController.getBombsRemaining() - 1);
            --mainController.correctCount;
            //mainController.getHBoxController().bombsTickLabel.setText(((Integer)(scoreboardController.getBombsRemaining())).toString());
            if (mainController.correctCount == 0) gameEnds();
        }
        mainController.fullGrid.get(i).get(j).setIsClicked(true);
        mainController.gPane.getChildren().get(i * size + j).setDisable(true);
        int surroundCount = 0;

        if (i > 0) surroundCount +=                         mainController.fullGrid.get(i - 1).get(j).getIsBomb() ? 1 : 0;
        if (i > 0 && j < size - 1) surroundCount +=         mainController.fullGrid.get(i - 1).get(j + 1).getIsBomb() ? 1 : 0;
        if (j < size - 1) surroundCount +=                  mainController.fullGrid.get(i).get(j + 1).getIsBomb() ? 1 : 0;
        if (i < size - 1 && j < size - 1) surroundCount +=  mainController.fullGrid.get(i + 1).get(j + 1).getIsBomb() ? 1 : 0;
        if (i < size - 1) surroundCount +=                  mainController.fullGrid.get(i + 1).get(j).getIsBomb() ? 1 : 0;
        if (i < size - 1 && j > 0) surroundCount +=         mainController.fullGrid.get(i + 1).get(j - 1).getIsBomb() ? 1 : 0;
        if (j > 0) surroundCount +=                         mainController.fullGrid.get(i).get(j - 1).getIsBomb() ? 1 : 0;
        if (i > 0 && j > 0) surroundCount +=                mainController.fullGrid.get(i - 1).get(j - 1).getIsBomb() ? 1 : 0;

        if (surroundCount == 0) {
            if (i > 0 && !mainController.fullGrid.get(i - 1).get(j).getIsClicked() && mainController.fullGrid.get(i - 1).get(j).rightClickState == 0) recurseFill(i - 1, j);
            if (i > 0 && j < size - 1 && !mainController.fullGrid.get(i - 1).get(j + 1).getIsClicked() && mainController.fullGrid.get(i - 1).get(j + 1).rightClickState == 0) recurseFill(i - 1, j + 1);
            if (j < size - 1 && !mainController.fullGrid.get(i).get(j + 1).getIsClicked() && mainController.fullGrid.get(i).get(j + 1).rightClickState == 0) recurseFill(i, j + 1);
            if (i < size - 1 && j < size - 1 && !mainController.fullGrid.get(i + 1).get(j + 1).getIsClicked() && mainController.fullGrid.get(i + 1).get(j + 1).rightClickState == 0) recurseFill(i + 1, j + 1);
            if (i < size - 1 && !mainController.fullGrid.get(i + 1).get(j).getIsClicked() && mainController.fullGrid.get(i + 1).get(j).rightClickState == 0) recurseFill(i + 1, j);
            if (i < size - 1 && j > 0 && !mainController.fullGrid.get(i + 1).get(j - 1).getIsClicked() && mainController.fullGrid.get(i + 1).get(j - 1).rightClickState == 0) recurseFill(i + 1, j - 1);
            if (j > 0 && !mainController.fullGrid.get(i).get(j - 1).getIsClicked() && mainController.fullGrid.get(i).get(j - 1).rightClickState == 0) recurseFill(i, j - 1);
            if (i > 0 && j > 0 && !mainController.fullGrid.get(i - 1).get(j - 1).getIsClicked() && mainController.fullGrid.get(i - 1).get(j - 1).rightClickState == 0) recurseFill(i - 1, j - 1);
        }

        if (surroundCount == 0) mainController.gPane.getChildren().get(i * size + j).setStyle("-fx-background-color: blue; -fx-border-color: black");
        else mainController.fullGrid.get(i).get(j).setText(((Integer)surroundCount).toString());
    }

    /**
     * Deals with the ending of the game
     */
    public void gameEnds() {
        if (mainController.timer != null)mainController.timer.cancel();
        String mineImageString = "File:images/mine2.png";
        if (mainController.won) {
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    if (mainController.fullGrid.get(i).get(j).getIsBomb()) {
                        mainController.gPane.getChildren().get(i * size + j).setStyle("-fx-background-color: darkgreen; -fx-border-color: black");
                        ImageView mineImg = new ImageView(mineImageString);
                        mineImg.setFitHeight(10); mineImg.setFitWidth(7);
                        mainController.fullGrid.get(i).get(j).setGraphic(mineImg);
                    }
                }
            }

            if (mainController.gameType == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations! You won in " + ((Integer)(Math.max(mainController.currTime - 1, 0))).toString() + " seconds!");
                alert.showAndWait();
            }
            else if (mainController.gameType == 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations! You won with " + ((Integer)(Math.min(mainController.currTime + 1, 60))).toString() + " seconds remaining!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations! You won!");
                alert.showAndWait();
            }
        }
        else {
            if (mainController.timer != null)mainController.timer.cancel();
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    if (mainController.fullGrid.get(i).get(j).rightClickState == 1 || mainController.fullGrid.get(i).get(j).rightClickState == 2) {
                        if (mainController.fullGrid.get(i).get(j).getIsBomb()) {
                            mainController.gPane.getChildren().get(i * size + j).setStyle("-fx-background-color: darkgreen; -fx-border-color: black");
                            ImageView mineImg = new ImageView(mineImageString);
                            mineImg.setFitHeight(10); mineImg.setFitWidth(7);
                            mainController.fullGrid.get(i).get(j).setGraphic(mineImg);
                        }
                        else {
                            mainController.gPane.getChildren().get(i * size + j).setStyle("-fx-background-color: gold; -fx-border-color: black");
                            ImageView mineImg = new ImageView(mineImageString);
                            mineImg.setFitHeight(10); mineImg.setFitWidth(7);
                            mainController.fullGrid.get(i).get(j).setGraphic(mineImg);
                        }
                    }
                    else if (mainController.fullGrid.get(i).get(j).getIsBomb()) {
                        mainController.gPane.getChildren().get(i * size + j).setStyle("-fx-background-color: darkred; -fx-border-color: black");
                        ImageView mineImg = new ImageView(mineImageString);
                        mineImg.setFitHeight(10); mineImg.setFitWidth(7);
                        mainController.fullGrid.get(i).get(j).setGraphic(mineImg);
                    }
                    mainController.fullGrid.get(i).get(j).setDisable(true);
                }
            }
            if (mainController.gameType == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Whoops! You lost in " + ((Integer)(Math.max(mainController.currTime - 1, 0))).toString() + " seconds!");
                alert.showAndWait();
            }
            else if (mainController.gameType == 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Whoops! You lost with " + ((Integer)(Math.min(mainController.currTime + 1, 60))).toString() + " seconds remaining!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Whoops! You lost!");
                alert.showAndWait();
            }
        }
    }
}
