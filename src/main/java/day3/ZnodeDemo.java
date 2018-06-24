package day3;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ZnodeDemo {
    private ZkClient client;
    @Before
    public void before(){
        client=new ZkClient("192.168.204.132:2181");
    }
    @Test
    public void testCreateNode(){
//       String path = client.create("/zpark", new Date(), CreateMode.EPHEMERAL);
//         client.create("/",new Date())
//        client.createPersistent("/zpark");
    }
    @Test
    public void testModifyNode(){
        client.writeData("/zpark",new Date());
    }
    @Test
    public void testDeleteNode(){
        client.deleteRecursive("/zpark");
    }
    @Test
    public void testReadNodeData(){
        Object o = client.readData("/zpark");
        System.out.println(o.getClass()+" "+o);
    }
    @Test
    public void testIsNodeExists(){
        boolean exists = client.exists("/zpark");
        System.out.println(exists);
    }
    @Test
    public void testChildNode(){
        boolean exists = client.exists("/zpark/192.168.204.128:20880");
        if(!exists){
            client.createPersistent("/zpark/192.168.204.128:20880",true);
        }
//        List<String> children = client.getChildren("/zpark");
//        for (String child : children) {
//            System.out.println(child);
//        }
    }
    @Test
    public void testWatchNodeData() throws IOException {
        client.subscribeDataChanges("/zpark", new IZkDataListener() {
            public void handleDataChange(String path, Object o) throws Exception {
                System.out.println(path+":"+o);
            }

            public void handleDataDeleted(String path) throws Exception {
                System.out.println("delete:"+path);

            }
        });
        System.in.read();
    }
    @Test
    public void testWatchNodeChange() throws IOException {
        client.subscribeChildChanges("/zpark", new IZkChildListener() {
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println(list);
            }
        });
        System.in.read();
    }
    @After
    public void after(){
        client.close();
    }
}
