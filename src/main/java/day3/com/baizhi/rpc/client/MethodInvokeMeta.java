package day3.com.baizhi.rpc.client;

import java.io.Serializable;
import java.util.Arrays;

/*MethodInvokeMeta用于封装client端期望的类名和方法，以及
调用该方法所需的参数类型和值
 */
public class MethodInvokeMeta implements Serializable {
    private Class<?> targetInterface;//类名
    private String method;//方法名
    private Class<?>[] parameterTypes;//参数类型
    private Object[] args;//参数

    public Class<?> getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class<?> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "MethodInvokeMeta{" +
                "targetInterface=" + targetInterface +
                ", method='" + method + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
