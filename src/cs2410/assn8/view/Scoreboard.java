package cs2410.assn8.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Deals with numbers and operations related to the scoreboard
 *
 * @author devey
 * @version 1.0
 */
public class Scoreboard {

    /**
     * Number of bombs yet to be discovered
     */
    private int bombsRemaining;

    /**
     * constructor
     * @param bombs number of bombs
     */
    public Scoreboard(int bombs) {
        bombsRemaining = bombs;
    }

    /**
     * just a getter
     * @return number of bombs
     */
    public int getBombsRemaining() {
        return bombsRemaining;
    }

    /**
     * just a setter
     * @param bombs number of bombs
     */
    public void setBombsRemaining(int bombs) {
        bombsRemaining = bombs;
    }
}
