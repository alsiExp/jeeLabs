package simpleChat.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import simpleChat.Server.Hash;


import java.io.*;
import java.net.Socket;

public class ClientController {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String clientHash;

    @FXML
    private TextArea txtArea;
    @FXML
    private Button btnSend;
    @FXML
    private TextField txtField;

    @FXML
    private void initialize() {
        setUpNetworking();
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
        try {
            String clientText = txtField.getText();
            clientHash = Hash.md5Custom(clientText);
            writer.println(clientText);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtField.setText("");
        txtField.requestFocus();
    }

    private void setUpNetworking() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream(), "Cp866");
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "Cp866"));
            clientConsolePrint("Network connection created, address: " + this.socket.getInetAddress() + " , port: " + socket.getLocalPort());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public class IncomingReader implements Runnable {
        @Override
        public void run() {
            String message;
            String serverHash = "";
            String ans = "";
            try {
                while ((message = reader.readLine()) != null) {
                    String[] arr = message.split("::");
                    ans = arr[0];
                    serverHash = arr[1];
                    clientConsolePrint(ans);
                    if(clientHash != null) {
                        clientConsolePrint("Client hash = " + clientHash);
                        clientConsolePrint("Server hash = " + clientHash);

                    }

                  /*  sb.append(" Client md5 hash = ");
                    clientHash = Hash.md5Custom(s);
                    sb.append(clientHash);*/
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }
    }

    private void clientConsolePrint(String s) {
        StringBuilder sb = new StringBuilder(txtArea.getText());
        sb.append(s);
        sb.append(System.lineSeparator());
        txtArea.setText(sb.toString());
    }
}
