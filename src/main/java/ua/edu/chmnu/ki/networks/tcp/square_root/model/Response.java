package ua.edu.chmnu.ki.networks.tcp.square_root.model;

import java.io.Serializable;
import java.util.Arrays;

public class Response implements Serializable {
    private Result result;

    private double[] roots;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public double[] getRoots() {
        return roots;
    }

    public void setRoots(double[] roots) {
        this.roots = roots;
    }

    @Override
    public String toString() {
        return "Response{" +
                "result=" + result +
                ", roots=" + Arrays.toString(roots) +
                '}';
    }
}
