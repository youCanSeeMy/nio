package day3.com.baizhi.rpc.proxy;

import day3.com.baizhi.rpc.client.RpcClient;
import day3.com.baizhi.rpc.client.RpcClientImpl;
import day3.com.baizhi.rpc.register.ServiceRegistry;
import day3.com.baizhi.rpc.server.HostAndPort;
import day3.com.baizhi.rpc.server.Result;
import day3.com.baizhi.rpc.client.MethodInvokeMeta;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/*通过动态代理封装Request对象的构建和与服务器的连接，
   然后发送请求，等待反馈结果
 */
public class RPCProxy<T> implements InvocationHandler {
    RpcClient rpcClient=new RpcClientImpl();//定义客户端
    private List<HostAndPort> hostAndPorts;//支持动态更新
    Class targetInterface;//目标接口
    public T createPorxy(Class targetInterface){
        this.targetInterface=targetInterface;
        return (T) Proxy.newProxyInstance(RPCProxy.class.getClassLoader(),new Class[]{targetInterface},this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        MethodInvokeMeta mim=new MethodInvokeMeta();
        mim.setTargetInterface(targetInterface);
        mim.setArgs(args);
        mim.setMethod(method.getName());
        mim.setParameterTypes(method.getParameterTypes());

        ServiceRegistry serviceRegistry = new ServiceRegistry();
        HostAndPort hostAndPort = serviceRegistry.getRedomServer();
        System.out.println(mim+"--------------------------------------------------");
        System.out.println("客户端连接的服务器号是----------"+hostAndPort);


        Result result = rpcClient.call(mim, hostAndPort);

        if(result.getException()!=null){
            throw result.getException();
        }
        return result.getReturnValue();
    }
}
