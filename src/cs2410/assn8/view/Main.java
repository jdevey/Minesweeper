package cs2410.assn8.view;

import cs2410.assn8.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //public static MainController mainController;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs2410/assn8/resources/main.fxml"));
        Parent mainPane = loader.load();
        MainController mainController = loader.getController();

        mainController.play(mainController);

        primaryStage.setTitle("Minesweeperish");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }
/*
    private void initFunctionality() {
        mainController.initPosition();
        initGrid();
        mainController.initStyle();

        //mainController.setBorderPane(bPane);
        //mainController.setPaneBottom(gPane);
    }

    private void initGrid() {

        for (int i = 0; i < 20; ++i) {
            fullGrid.add(new ArrayList<>());
            for (int j = 0; j < 20; ++j) {
                fullGrid.get(i).add(new Cell());
            }
        }

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 20; ++j) {
                fullGrid.get(i).get(j).isBomb = true; //Means that these are bombs
            }
        }

        Collections.shuffle(fullGrid);

        //gPane.setGridLinesVisible(true);

        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                gPane.add(fullGrid.get(i).get(j), i, j);
            }
        }
    }
*/
    public static void main(String[] args) {
        launch(args);
    }
}