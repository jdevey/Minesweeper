package cs2410.assn8.view;

import cs2410.assn8.controller.MainController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import java.io.File;

public class Cell extends Button {

    private String flagImageString = "File:images/flag.png";
    private String questionmarkImageString = "File:images/questionmark.png";

    private int i;
    private int j;

    private boolean isBomb;
    private boolean isClicked;
    public int rightClickState = 0;
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
            if (btn == MouseButton.PRIMARY && rightClickState != 1 && rightClickState != 2) {
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
            if (mainController.setupDone) {
                scoreboardController.setBombsRemaining(scoreboardController.getBombsRemaining() - 1);
                mainController.getHBoxController().bombsTickLabel.setText(((Integer)(scoreboardController.getBombsRemaining())).toString());
            }
            ImageView image = new ImageView(flagImageString);
            image.setFitHeight(12); image.setFitWidth(7);
            this.setGraphic(image);
        }
        else if (state == 1) {
            rightClickState = 2;
            ImageView image = new ImageView(questionmarkImageString);
            image.setFitHeight(12); image.setFitWidth(7);
            this.setGraphic(image);
        }
        else if (state == 2) {
            if (mainController.setupDone) {
                scoreboardController.setBombsRemaining(scoreboardController.getBombsRemaining() + 1);
                mainController.getHBoxController().bombsTickLabel.setText(((Integer)(scoreboardController.getBombsRemaining())).toString());
            }
            rightClickState = 0;
            this.setGraphic(null);
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
