package ua.edu.chmnu.ki.networks.tcp.arrays;

import java.io.Serializable;

public class Response<T, U> implements Serializable {
    private T max;
    private T min;
    private U avg;

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public U getAvg() {
        return avg;
    }

    public void setAvg(U avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "max=" + max +
                ", min=" + min +
                ", avg=" + avg +
                '}';
    }
}
