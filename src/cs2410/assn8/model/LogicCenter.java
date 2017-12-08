package cs2410.assn8.model;

import cs2410.assn8.controller.MainController;
import cs2410.assn8.view.Scoreboard;

public class LogicCenter {
    private int size;
    private int numBomb;
    private MainController mainController;
    private Scoreboard scoreboardController;

    public void beginPlaying(MainController _mainController, Scoreboard _scoreboardController, int _size, int _numBomb) {

        size = _size;
        numBomb = _numBomb;
        mainController = _mainController;
        mainController.setupDone = false;
        scoreboardController = _scoreboardController;
        mainController.won = true;

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

    public void cellClick(int i, int j) {
        mainController.fullGrid.get(i).get(j).setIsClicked(true);
        mainController.gPane.getChildren().get(i * size + j).setDisable(true);
        if (mainController.fullGrid.get(i).get(j).getIsBomb()) {
            mainController.won = false;
            mainController.gPane.getChildren().get(i * size + j).setStyle("-fx-background-color: gold");
            gameEnds();
        }
        else {
            recurseFill(i, j);
        }
    }

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

        if (surroundCount == 0) mainController.gPane.getChildren().get(i * size + j).setStyle("-fx-background-color: blue");
        else mainController.fullGrid.get(i).get(j).setText(((Integer)surroundCount).toString());
    }

    private void gameEnds() {
        if (mainController.won) System.out.println("You winned");
        else System.out.println("Noob, rekt");
    }
}
