package day3.com.baizhi.rpc.register;
import day3.com.baizhi.rpc.server.HostAndPort;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.RandomUtils;

import java.net.InetAddress;
import java.util.List;

public class ServiceRegistry {
    ZkClient zkClient = new ZkClient("192.168.20.130:2181");
    //用于服务器注册
    public void register(Integer port){
        try{
            InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            String hostAddress = address.getHostAddress();//192.168.0.121
            boolean exists = zkClient.exists("/hostAndPort/"+hostAddress+":"+port);
            if(!exists){
                zkClient.createPersistent("/hostAndPort/"+hostAddress+":"+port,true);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //用于客户端随机获取服务器的地址
    public HostAndPort getRedomServer(){
        List<String> nodes = zkClient.getChildren("/hostAndPort");
        int i = RandomUtils.nextInt(0, nodes.size() - 1);
        String s = nodes.get(i);
        String[] strings = s.split(":");
        HostAndPort hostAndPort = new HostAndPort(strings[0],Integer.parseInt(strings[1]));
        return hostAndPort;
    }
}