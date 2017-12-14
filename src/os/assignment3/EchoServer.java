/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.assignment3;
import java.io.*;
import java.net.*;

public class EchoServer {

    ServerSocket echoServer = null;
    Socket clientSocket = null;
    int numConnections = 0;
    int port;

    public static void main(String args[]) {
        int port = 6789;
        EchoServer server = new EchoServer(port);
        server.startServer();
    }

    public EchoServer(int port) {
        this.port = port;
    }

    public void stopServer() {
        System.exit(0);
    }

    public void startServer() {
        // Try to open a server socket on the given port
        // Note that we can't choose a port less than 1024 if we are not
        // privileged users (root)

        try {
            echoServer = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
}

class ServerConnection implements Runnable {

    BufferedReader is;
    PrintStream os;
    Socket clientSocket;
    int id;
    EchoServer server;

    public ServerConnection(Socket clientSocket, int id, EchoServer server) {
        this.clientSocket = clientSocket;
        this.id = id;
        this.server = server;
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }

    public void run() {
        String line;
        try {
            boolean serverStop = false;

            while (true) {
                line = is.readLine();
                if (line.equals(".")) {
                    break;
                } else {
                    os.println(line);
                }
            }

            System.out.println("Connection " + id + " closed.");
            is.close();
            os.close();
            clientSocket.close();

            if (serverStop) {
                server.stopServer();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

