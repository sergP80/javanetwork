package ua.edu.chmnu.ki.networks.tcp.arrays.server;

import ua.edu.chmnu.ki.networks.tcp.arrays.model.Request;
import ua.edu.chmnu.ki.networks.tcp.arrays.model.Response;
import ua.edu.chmnu.ki.networks.tcp.core.server.ClientSessionDelegate;
import ua.edu.chmnu.ki.networks.tcp.simple.ServerClientSession;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultClientSessionDelegate implements ClientSessionDelegate {
    @Override
    public void handle(Socket socket) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            while (!socket.isClosed()) {
                Request<Integer> request = (Request<Integer>) in.readObject();
                System.out.println(">" + request);
                Response<Integer, Double> response = processRequest(request);
                System.out.println("<" + response);

                out.writeObject(response);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected Response<Integer, Double> processRequest(Request<Integer> request) {
        Response<Integer, Double> response = new Response<>();
        Integer[] array = request.array();
        response.setAvg(array[0].doubleValue());
        response.setMin(array[0]);
        response.setMax(array[0]);
        for (int i = 1;  i < array.length; ++i) {
            if (array[i] > response.getMax()) {
                response.setMax(array[i]);
            }
            if (array[i] < response.getMin()) {
                response.setMin(array[i]);
            }
            response.setAvg(response.getAvg() + array[i]);
        }
        response.setAvg(response.getAvg()/array.length);
        return response;
    }
}
