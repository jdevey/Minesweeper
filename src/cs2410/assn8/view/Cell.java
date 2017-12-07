package cs2410.assn8.view;

import cs2410.assn8.controller.MainController;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

public class Cell extends Button {

    private int clickState = 0;

    private int i;
    private int j;

    private boolean isBomb;
    private boolean isClicked;
    private int rightClickState;
    private MainController mainController;
    private Scoreboard scoreboardController;

    public Cell (MainController _mainController, Scoreboard _scoreboardController) {
        mainController = _mainController;
        scoreboardController = _scoreboardController;
        isBomb = false;
        isClicked = false;
        rightClickState = 0;
        //this.setStyle("-fx-background-color: lightblue; -fx-border-color: black");
        this.setPrefSize(25, 25);

        initCellProperties();
    }

    void initCellProperties() {

        //this.setOnMouseClicked()

        this.setOnMousePressed(aa -> {
            MouseButton btn = aa.getButton();
            if (btn == MouseButton.PRIMARY) {
                mainController.getLogicController().cellClick(i, j);
            }
            else if (btn == MouseButton.SECONDARY){
                setClickState(rightClickState);
            }
        });

    }

    public void setClickState(int state) {
        if (state == 0) {
            rightClickState = 1;
            scoreboardController.setBombsRemaining(scoreboardController.getBombsRemaining() - 1);
        }
        else if (state == 1) {
            rightClickState = 2;
        }
        else {
            rightClickState = 0;
            scoreboardController.setBombsRemaining(scoreboardController.getBombsRemaining() - 1);
        }
    }

    public void setIsClicked(boolean choice) {
        isClicked = choice;
    }

    public boolean getIsClicked() {
        return isClicked;
    }

    public void setIsBomb(boolean choice) {
        isBomb = choice;
    }

    public boolean getIsBomb() {
        return isBomb;
    }

    public void setIJ(int _i, int _j) {
        i = _i;
        j = _j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
