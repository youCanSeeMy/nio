package day3.com.baizhi.rpc.server;

import java.io.Serializable;

//封装调用方法返回的结果或异常
public class Result implements Serializable {
    private Object returnValue;//结果
    private RuntimeException exception;//异常

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "Result{" +
                "returnValue=" + returnValue +
                ", exception=" + exception +
                '}';
    }
}




