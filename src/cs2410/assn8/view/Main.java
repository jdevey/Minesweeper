package cs2410.assn8.view;

import cs2410.assn8.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Deals with starting the program and getting it rolling
 *
 * @author devey
 * @version 1.0
 */
public class Main extends Application {
    //public static MainController mainController;

    /**
     * Starts the program with a scene
     * @param primaryStage a stage
     * @throws Exception in case of exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs2410/assn8/resources/main.fxml"));
        Parent mainPane = loader.load();
        MainController mainController = loader.getController();

        mainController.setup(mainController, primaryStage);

        primaryStage.setTitle("Minesweeperish - Normal Mode");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }

    /**
     * Launches the program
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}