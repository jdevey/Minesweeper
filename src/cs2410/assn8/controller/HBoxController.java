package cs2410.assn8.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HBoxController {
    /**
     * a button
     */
    @FXML
    public Button startBtn;

    /**
     * Bombs label
     */
    @FXML
    private Text bombsLabel;

    /**
     * Bomb counter
     */
    @FXML
    public Text bombsTickLabel;

    /**
     * Label that says time
     */
    @FXML
    private Text timeLabel;

    /**
     * Time ticker
     */
    @FXML
    public Text timeTickLabel;

    /**
     * Contains some stuff
     */
    @FXML
    private VBox leftVBox;

    /**
     * Contains some stuff
     */
    @FXML
    private VBox rightVBox;
}
