package ua.edu.chmnu.ki.networks.tcp.square_root.model;

import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable {
    private double[] coeffs;

    public Request() {
    }

    public Request(double[] coeffs) {
        this.coeffs = coeffs;
    }

    public double[] getCoeffs() {
        return coeffs;
    }

    public void setCoeffs(double[] coeffs) {
        this.coeffs = coeffs;
    }

    @Override
    public String toString() {
        return "Request{" +
                "coeffs=" + Arrays.toString(coeffs) +
                '}';
    }
}
