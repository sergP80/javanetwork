package ua.edu.chmnu.ki.networks.tcp.square_root.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

@Setter
@Getter
public class Response implements Serializable {
    private Result result;

    private double[] roots;

    @Override
    public String toString() {
        return "Response{" +
                "result=" + result +
                ", roots=" + Arrays.toString(roots) +
                '}';
    }
}
