package com.lutong.fragment.linechart;


import java.io.Serializable;

public class Bean implements Serializable {

    public String data;
    public float value;

    public Bean(String data, float value) {
        this.data = data;
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "data='" + data + '\'' +
                ", value=" + value +
                '}';
    }
}
