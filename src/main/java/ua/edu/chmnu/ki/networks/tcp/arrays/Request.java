package ua.edu.chmnu.ki.networks.tcp.arrays;

import java.io.Serializable;
import java.util.Arrays;

public class Request<T> implements Serializable {
    private T[] array;

    public Request(T[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "Request{" +
                "array=" + Arrays.toString(array) +
                '}';
    }

    public T[] getArray() {
        return array;
    }
}
