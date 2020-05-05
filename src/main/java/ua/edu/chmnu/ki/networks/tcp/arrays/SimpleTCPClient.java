/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.arrays;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleTCPClient<T> implements Runnable {

    private final String host;
    private final int port;
    private final T[] data;
    private final Socket socket;
    
    public SimpleTCPClient(String host, int port, T[] data) throws IOException {
        this.host = host;
        this.port = port;
        this.data = data;
        this.socket = new Socket(host, port);
        this.socket.setSoTimeout(1000);
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {
            //Thread.sleep(1000);
            out.writeObject(new Request<T>(data));
            Object r = in.readObject();
            System.out.printf("[%s:%d] Received: %s\n", 
                    socket.getInetAddress().toString(), socket.getPort(),
                    r.toString());

        }catch (SocketTimeoutException ex)
        {
            System.out.println("No ops on the socket: " + ex.getMessage());  
        } catch (IOException ex) {
            Logger.getLogger(SimpleTCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SimpleTCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

}
