/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.net.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author svpuzyrov
 */
public class SimpleTCPClient implements Runnable {

    private final String host;
    private final int port;
    private final Object data;

    public SimpleTCPClient(String host, int port, Object data) {
        this.host = host;
        this.port = port;
        this.data = data;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port);
                //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());) {
            Thread.sleep(1000);
            out.writeObject(data);
//            Object r = in.readObject();
//            System.out.printf("[%s:%d] Received: %s", 
//                    socket.getInetAddress().toString(), socket.getPort(),
//                    r.toString());

        } catch (IOException ex) {
            Logger.getLogger(SimpleTCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleTCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
