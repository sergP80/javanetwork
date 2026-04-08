package ua.edu.chmnu.ki.networks.tcp.mouse.server;

import ua.edu.chmnu.ki.networks.mouse.model.MouseCapture;
import ua.edu.chmnu.ki.networks.tcp.core.server.ClientSessionDelegate;
import ua.edu.chmnu.ki.networks.tcp.simple.ServerClientSession;

import java.awt.*;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultClientSessionDelegate implements ClientSessionDelegate {
    @Override
    public void handle(Socket socket) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Robot robot = new Robot();

            while (!socket.isClosed()) {
                MouseCapture mouseCapture = (MouseCapture) in.readObject();
                System.out.println("Position of target: " + mouseCapture);

                robot.mouseMove(mouseCapture.x(), mouseCapture.y());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
