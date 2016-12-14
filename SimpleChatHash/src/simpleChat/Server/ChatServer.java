package simpleChat.Server;


import simpleChat.Controllers.ServerController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServer {
    ArrayList<PrintWriter> clientOutptutStreams;
    ArrayList<Socket> clientSocketsList;
    ServerSocket serverSocket;
    ServerController controller;

    public void setGUI(ServerController sc) {
        controller = sc;
    }

    public void go() {
        clientOutptutStreams = new ArrayList<>();
        clientSocketsList = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(5000);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "Cp866"));
                clientOutptutStreams.add(writer);
                clientSocketsList.add(clientSocket);
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                printServerConsole("Server: Connection is created. " + clientSocket.toString());

            }
        } catch (SocketException ignore) {} // become exception by close socket
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tellEveryone(String message) {
        for(PrintWriter writer : clientOutptutStreams) {
            writer.println(message);
            writer.flush();
        }
    }

    private void printServerConsole(String s) {
        controller.print(s);
    }

    public void stop() {
        try {
            serverSocket.close();
            for (Socket s : clientSocketsList) {
                printServerConsole(s.toString() + " is stoped");
                s.close();
            }
            clientSocketsList.clear();
            clientOutptutStreams.clear();

        } catch (IOException e) {
        }
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        public ClientHandler(Socket clientSocket) {
            try {
                socket = clientSocket;
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(), "Cp866");
                reader = new BufferedReader(inputStreamReader);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    String hash = Hash.md5Custom(message);
                    printServerConsole("Server has received: " + message);
                    printServerConsole("Server md5 hash = " + hash);

                    tellEveryone(message + "::" + hash );
                }
            } catch (IOException ignore) {
            }  //become exception by close socket
        }
    }


}
