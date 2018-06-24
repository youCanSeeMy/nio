package day3.com.baizhi.rpc.client;

import day3.com.baizhi.rpc.server.HostAndPort;
import day3.com.baizhi.rpc.server.Result;

public interface RpcClient {
    public Result call(MethodInvokeMeta mim, HostAndPort hap);
}
