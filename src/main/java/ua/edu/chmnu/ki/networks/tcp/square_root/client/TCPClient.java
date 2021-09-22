/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.square_root.client;

import ua.edu.chmnu.ki.networks.tcp.ServerResponseHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient implements Runnable {

    private final String host;
    private final int port;
    private final Object data;
    private final Socket socket;
    private final ServerResponseHandler handler;

    public TCPClient(String host, int port, Object data, ServerResponseHandler handler) throws IOException {
        this.host = host;
        this.port = port;
        this.data = data;
        this.handler = handler;
        this.socket = new Socket(host, port);
        this.socket.setSoTimeout(1000);
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(data);
            Object response = in.readObject();
            System.out.printf("[%s:%d] Received: %s\n",
                    socket.getInetAddress().toString(), socket.getPort(),
                    response.toString());
            handler.handle(response);

        } catch (SocketTimeoutException ex) {
            System.out.println("No ops on the socket: " + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
