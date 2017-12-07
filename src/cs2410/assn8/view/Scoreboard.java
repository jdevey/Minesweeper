package cs2410.assn8.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Scoreboard {

    private int bombsRemaining;

    public Scoreboard(int bombs) {
        bombsRemaining = bombs;
    }

    public int getBombsRemaining() {
        return bombsRemaining;
    }
    public void setBombsRemaining(int bombs) {
        bombsRemaining = bombs;
    }
}
