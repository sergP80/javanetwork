/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.square_root.server;

import ua.edu.chmnu.ki.networks.tcp.ServerSessionHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author svpuzyrov
 */
public class ClientSession implements Runnable{
    private final Socket socket;
    private final ServerSessionHandler handler;

    public ClientSession(Socket socket, ServerSessionHandler handler) throws IOException {
        this.socket = socket;
        this.handler = handler;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        String from = String.format("[%s:%d]", 
                                    socket.getInetAddress().toString(),
                                    socket.getPort());
        try {
           handler.handle(socket);
        } catch (Exception ex) {
            String what;
            String action = "shutdown";
            if (socket.isInputShutdown())
            {
                what = "Input stream";
                
            }
            else if (socket.isOutputShutdown())
            {
                what = "Output stream";
            } else if (socket.isClosed())
            {
                what = "Connection";
            } else {
                what = "Unknown error";
                action = ex.getMessage();
                Logger.getLogger(ClientSession.class.getName()).log(Level.SEVERE, null, ex);
            }            
            System.out.printf("%s from %s was %s\n", what, from, action);
        }
    }
    
}
