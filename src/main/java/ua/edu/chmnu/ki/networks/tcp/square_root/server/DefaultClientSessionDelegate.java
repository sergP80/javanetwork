package ua.edu.chmnu.ki.networks.tcp.square_root.server;

import ua.edu.chmnu.ki.networks.tcp.core.server.ClientSessionDelegate;
import ua.edu.chmnu.ki.networks.tcp.simple.ServerClientSession;
import ua.edu.chmnu.ki.networks.tcp.square_root.model.Request;
import ua.edu.chmnu.ki.networks.tcp.square_root.model.Response;
import ua.edu.chmnu.ki.networks.tcp.square_root.model.Result;

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
                Request request = (Request) in.readObject();
                System.out.println(">" + request);
                Response response = processRequest(request);
                out.writeObject(response);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Response processRequest(Request request) {
        Response response = new Response();
        double[] coeffs = request.getCoeffs();
        double D = coeffs[1] * coeffs[1] - 4 * coeffs[0] * coeffs[2];
        if (D < 0) {
            response.setResult(Result.NO_ROOTS);
        } else {
            double[] results = new double[2];
            results[0] = (-coeffs[1] - Math.sqrt(D)) / 2 / coeffs[0];
            results[1] = (-coeffs[1] + Math.sqrt(D)) / 2 / coeffs[0];
            response.setRoots(results);
            if (D > 0) {
                response.setResult(Result.TWO_ROOTS);
            } else {
                response.setResult(Result.ONE_ROOT);
            }
        }
        return response;
    }
}
