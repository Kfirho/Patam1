package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {

    private int port;
    private volatile boolean stop;
    private ServerSocket server;
    private ClientHandler handler;

    public MyServer(int port, ClientHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    public void start() {
        stop = false; 
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);

            while (!stop) {
                try {
                    Socket client = server.accept(); 
                    try {
                        handler.handleClient(client.getInputStream(), client.getOutputStream());
                    } finally {
                        try {
                            client.close(); 
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketTimeoutException ste) {
                } catch (IOException io) {
                    if (!stop) {
                        io.printStackTrace(); 
                    }
                }
            }
        } catch (IOException e) {
            if (!stop) {
                e.printStackTrace();
            }
        } finally {
            close(); 
        }
    }

    public synchronized void close() {
        stop = true;
        try {
            if (server != null) {
                server.close(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
