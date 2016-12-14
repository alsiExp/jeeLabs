package simpleChat.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simpleChat.Server.ChatServer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ServerController {

    @FXML
    private TextArea txtArea;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnRun;

    private Stage mainStage;
    private ChatServer server;

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize() {
        server = new ChatServer();
        server.setGUI(this);
    }

    @FXML
    private void runServer(ActionEvent actionEvent) {
        Thread serverThread = new Thread(new ServerThread());
        serverThread.start();
    }

    @FXML
    private void stopServer(ActionEvent actionEvent) {
        print("Server stops...");
        server.stop();
    }

    @FXML
    private void createClient(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../fxml/clientWindow.fxml"));
            Parent p = fxmlLoader.load();
            Stage clientWindowStage = new Stage();
            clientWindowStage.setTitle("Java FX Chat Client");
            clientWindowStage.setMinHeight(200);
            clientWindowStage.setMinWidth(350);
            clientWindowStage.setScene(new Scene(p));
            clientWindowStage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void print(String s) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                printConsole(s);
            }
        });
    }

    private void printConsole(String s) {
        StringBuilder sb = new StringBuilder(txtArea.getText());
        sb.append(s);
        sb.append(System.lineSeparator());
        txtArea.setText(sb.toString());
    }

    public class ServerThread implements Runnable {
        @Override
        public void run() {
            print("Server starts...");
            server.go();
        }
    }

    /*
    public class ClientThread implements Runnable {
        @Override
        public void run() {
            ClientController
        }
    }*/
}
