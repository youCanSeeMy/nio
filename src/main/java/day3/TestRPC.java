package day3;

import day3.com.baizhi.rpc.proxy.RPCProxy;
import day3.com.baizhi.rpc.server.HostAndPort;
import day3.com.baizhi.rpc.server.RpcServer;
import day3.com.zpark.service.DemoService;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.net.InetAddress;
import java.util.List;


public class TestRPC {
    ZkClient zkClient = new ZkClient("192.168.20.130:2181");
    @Test
    public void m5() {
        RPCProxy<DemoService> proxy=new RPCProxy<DemoService>();
        DemoService demoService =  proxy.createPorxy(DemoService.class);

        Integer sum = demoService.sum(1, 2);
        System.out.println(sum);
        // 1.demoService代理 可以随机调用服务提供者（多个）
        // 2.业务实现细节对.demoService代理透明
        //3.服务自动注册与发现
    }
    @Test
    public void m0(){
        zkClient.deleteRecursive("/hostAndPort");
    }
    @Test
    public void m2(){
            RpcServer rpcServer = new RpcServer();
            rpcServer.bind(8888);

    }

    public void createNode(Integer port){
        try{
            InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            String hostAddress = address.getHostAddress();//192.168.0.121
            boolean exists = zkClient.exists("/hostAndPort/"+hostAddress+":"+port);
            if(!exists){
                zkClient.createPersistent("/hostAndPort/"+hostAddress+":"+port,true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public HostAndPort getRedomNode(){
        List<String> nodes = zkClient.getChildren("/hostAndPort");
        int i = RandomUtils.nextInt(0, nodes.size() - 1);
        String s = nodes.get(i);
        String[] strings = s.split(":");
        HostAndPort hostAndPort = new HostAndPort(strings[0],Integer.parseInt(strings[1]));
        return hostAndPort;
    }
    @Test
    public void m1(){
//        createNode(8888);
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        System.out.println(getRedomNode());
        zkClient.delete("/hostAndPort/192.168.8.22:8888");
    }

}
