package com.lutong.App;

public class MessageEvent<T>{

    private int code;
    private T data;

    public MessageEvent(int code, T data){
       setCode(code);
       setData(data);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
