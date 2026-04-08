/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.core.client;

import lombok.Getter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OneWayTcpClient<T> implements Runnable {

    @Getter
    private final String host;

    @Getter
    private final int port;

    private final Socket socket;

    private final Supplier<T> supplier;

    @Getter
    private boolean isActive = true;

    public OneWayTcpClient(String host, int port, Supplier<T> supplier) throws IOException {
        this.host = host;
        this.port = port;
        this.supplier = supplier;
        this.socket = new Socket(host, port);
        this.socket.setSoTimeout(1000);
    }

    public void setActive(boolean active) throws IOException {
        isActive = active;

        if (!isActive) {
            socket.close();
        }
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            while (!socket.isClosed()) {
                T data = supplier.get();

                out.writeObject(data);

                Thread.sleep(100);
            }

        } catch (SocketTimeoutException ex) {
            System.out.println("No ops on the socket: " + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(OneWayTcpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
