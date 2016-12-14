package simpleChat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simpleChat.Controllers.ServerController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/mainWindow.fxml"));
        Parent fxmlServerWindow = fxmlLoader.load();
        ServerController serverController = fxmlLoader.getController();
        serverController.setStage(primaryStage);

        primaryStage.setTitle("JavaFX Simple Chat Server");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        primaryStage.setScene(new Scene(fxmlServerWindow, 700, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
