package ua.edu.chmnu.ki.networks.tcp.arrays.model;

import java.io.Serializable;
import java.util.Arrays;

public record Request<T>(T[] array) implements Serializable {

    @Override
    public String toString() {
        return "Request{" +
                "array=" + Arrays.toString(array) +
                '}';
    }

}
