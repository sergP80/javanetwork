package ua.edu.chmnu.ki.networks.tcp.square_root.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

@Setter
@Getter
public class Request implements Serializable {
    private double[] coeffs;

    public Request() {
    }

    public Request(double[] coeffs) {
        this.coeffs = coeffs;
    }

    @Override
    public String toString() {
        return "Request{" +
                "coeffs=" + Arrays.toString(coeffs) +
                '}';
    }
}
