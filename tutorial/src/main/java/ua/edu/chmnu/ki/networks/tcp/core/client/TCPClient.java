/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.core.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient implements Runnable {

    private final Object data;
    private final Socket socket;
    private final ServerResponseDelegate delegate;

    public TCPClient(String host, int port, Object data, ServerResponseDelegate delegate) throws IOException {
        this.data = data;
        this.delegate = delegate;
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
            delegate.handle(response);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
