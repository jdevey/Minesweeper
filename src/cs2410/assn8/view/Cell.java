package cs2410.assn8.view;

import cs2410.assn8.controller.HBoxController;
import cs2410.assn8.controller.MainController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import java.io.File;
import java.util.TimerTask;

/**
 * Class that handles operations related to the individual cell
 *
 * @author devey
 * @version 1.0
 */
public class Cell extends Button {

    /**
     * Location of the flag image
     */
    private String flagImageString = "File:images/flag.png";
    /**
     * Location of the question mark image
     */
    private String questionmarkImageString = "File:images/questionmark.png";

    /**
     * Coordinate of the cell
     */
    private int i;
    /**
     * Coordinate of the cell
     */
    private int j;

    /**
     * Whether it's a bomb
     */
    private boolean isBomb;
    /**
     * Whether it's been clicked
     */
    private boolean isClicked;
    /**
     * Right-click status (flag or not)
     */
    public int rightClickState = 0;
    /**
     * Used to access the main controller
     */
    private MainController mainController;
    /**
     * Used to access the scoreboard controller
     */
    private Scoreboard scoreboardController;
    /**
     * Used to access the hbox controller
     */
    private HBoxController hBoxController;

    /**
     * Constructor that initializes a few things for the cell
     *
     * @param _mainController main control
     * @param _scoreboardController scoreboard control
     * @param _hBoxController hbox control
     */
    public Cell (MainController _mainController, Scoreboard _scoreboardController, HBoxController _hBoxController) {
        mainController = _mainController;
        scoreboardController = _scoreboardController;
        hBoxController = _hBoxController;
        isBomb = false;
        isClicked = false;
        rightClickState = 0;
        //this.setStyle("-fx-background-color: lightblue; -fx-border-color: black");
        this.setPrefSize(25, 25);

        initCellProperties();
    }

    /**
     * Used to initialize a few things about the cell
     */
    private void initCellProperties() {

        //this.setOnMouseClicked()

        this.setOnMousePressed(aa -> {
            MouseButton btn = aa.getButton();
            if (btn == MouseButton.PRIMARY && rightClickState != 1 && rightClickState != 2) {
                if (mainController.gameType == 1) mainController.currTime = 9;
                mainController.getLogicController().cellClick(i, j);
            }
            else if (btn == MouseButton.SECONDARY){
                setClickState(rightClickState);
            }
            initTime();
            mainController.hasStarted = true;
        });
    }

    /**
     * Brings the cell to the next right-click state and deals with those repurcussions
     * @param state an integer
     */
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

    /**
     * Deals with the timer
     */
    private void initTime() {
        if (!mainController.hasStarted) {
            mainController.timer.scheduleAtFixedRate(
                    new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater( new Runnable() {
                                public void run() {
                                    hBoxController.timeTickLabel.setText(String.valueOf(mainController.currTime));
                                    if (mainController.gameType == 0) {
                                        ++mainController.currTime;
                                    }
                                    else {
                                        --mainController.currTime;
                                        if (mainController.currTime <= -1) {
                                            mainController.won = false;
                                            mainController.getLogicController().gameEnds();
                                        }
                                    }
                                }
                            });
                        }
                    }, 1000, 1000
            );
        }
    }

    /**
     * set clicked
     * @param choice either true or false
     */
    public void setIsClicked(boolean choice) {
        isClicked = choice;
    }

    /**
     * get whether it's been clicked
     * @return boolean
     */
    public boolean getIsClicked() {
        return isClicked;
    }

    /**
     * set whether it's a bomb
     * @param choice
     */
    public void setIsBomb(boolean choice) {
        isBomb = choice;
    }

    /**
     * get whether it's a bomb
     * @return
     */
    public boolean getIsBomb() {
        return isBomb;
    }

    /**
     * used while initializing the grid
     * @param _i i value
     * @param _j j value
     */
    public void setIJ(int _i, int _j) {
        i = _i;
        j = _j;
    }

    /**
     * Get i value
     * @return i
     */
    public int getI() {
        return i;
    }

    /**
     * get j value
     * @return j
     */
    public int getJ() {
        return j;
    }
}
