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
public class ServerClientSession implements Runnable{
    private final Socket socket;

    public ServerClientSession(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        String from = String.format("[%s:%d]", 
                                    socket.getInetAddress().toString(),
                                    socket.getPort());
        try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            /*ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())*/)
        {
            while (!socket.isClosed())
            {
                Object o = in.readObject();
                System.out.println(from + ">" + o);
                //out.writeObject("OK");
            }
        } catch (IOException ex) {
            if (socket.isInputShutdown())
            {
                System.out.println("Input stream [" + from + "] was shutdown");
            }
            else if (socket.isOutputShutdown())
            {
                System.out.println("Output stream [" + from + "] was shutdown");
            } else if (socket.isClosed())
            {
                System.out.println("Connection from [" + from + "] was closed");
            } else {
                System.out.println("Unknown error " + ex.getMessage());
                Logger.getLogger(ServerClientSession.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
