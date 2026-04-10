package ua.edu.chmnu.ki.networks.tcp.arrays.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Response<T, U> implements Serializable {
    private T max;
    private T min;
    private U avg;

    @Override
    public String toString() {
        return "Response{" +
                "max=" + max +
                ", min=" + min +
                ", avg=" + avg +
                '}';
    }
}
