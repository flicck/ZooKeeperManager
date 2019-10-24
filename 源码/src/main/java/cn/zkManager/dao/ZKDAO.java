package cn.zkManager.dao;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZKUtil;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import cn.zkManager.deserialize.Deserialize;

import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 
 * @author sun_flower
 * 
 */
public class ZKDAO {
    
  //  private static String connectString = "192.168.134.20:2181";
    //连接时长30分钟
    private static int sessionTimeout = 1800000;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    
    private static ZooKeeper zk = null;
    static{
//    	try {
//    		zk = connectionZooKeeper();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    public static void connectionZooKeeper(String connect) throws IOException {
        
    	zk = new ZooKeeper(connect, sessionTimeout, new Watcher() {
            
            public void process(WatchedEvent event) {
       
            }
        });
       
    }
    
    public String createNode(String path, String data) {
        byte[] bytesData = data.getBytes();
        //访问控制列表
        ArrayList<ACL> openAclUnsafe = Ids.OPEN_ACL_UNSAFE;
        //创建模式
        CreateMode mode = CreateMode.PERSISTENT;
        String result = "";
		try {
			result = zk.create(path, bytesData, openAclUnsafe, mode);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        return result;
    }
    
    //这个方法要返回这个节点的信息为一个map
    public Map<String,String> getNodeInfo(String path) throws UnsupportedEncodingException{
    	Map<String,String> m1 = new LinkedHashMap<>();
    			
    	Stat stat = new Stat();
    	byte[] data = null;
		try {
			data = zk.getData(path, false, stat);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//反序列化
		String outString = "";
		if(data!=null){
			outString = new String(data,"utf-8");
		}
		
		try {
			if(data!=null){
				Deserialize serializableSerializer = new Deserialize();
				Object deserialize = serializableSerializer.deserialize(data);
				outString = deserialize.toString();
				
			}
		} catch (Exception e) {
			throw e;
		//即使反序列化出异常还是要执行下面的语句
		}finally{
			m1.put("nodeValue", data==null?"null":outString);
			m1.put("cZxid", String.valueOf(stat.getCzxid()));
			m1.put("ctime", sdf.format(new Date(stat.getCtime())));
			m1.put("mZxid", String.valueOf(stat.getMzxid()));
			m1.put("mtime", sdf.format(new Date(stat.getMtime())));
			m1.put("pZxid", String.valueOf(stat.getPzxid()));
			m1.put("cversion", String.valueOf(stat.getCversion()));
			m1.put("dataVersion", String.valueOf(stat.getVersion()));
			m1.put("aclVersion", String.valueOf(stat.getAversion()));
			m1.put("ephemeralOwner", String.valueOf(stat.getEphemeralOwner()));
			m1.put("dataLength", String.valueOf(stat.getDataLength()));
			m1.put("numOfChildren", String.valueOf(stat.getNumChildren()));   	
	    	//返回一个有所有数据的map
	        return m1;
		}
		
	

    }
    //这个方法要返回一个节点下一层所有节点的数据
    public List<String> getListData(String path){
    	List<String> children=null;
		try {
			children = zk.getChildren(path, false);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(children);
    	
    	return children;
    }
    
    public Stat setUpdateData( String path, String data) {
    	try {
    		
			return zk.setData(path, data.getBytes(), -1);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    //删除节点
    public void deleteNode(String path){
    	try {
    		ZKUtil.deleteRecursive(zk, path);
		} catch (InterruptedException | KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public int getNumOfChildren(String path){
       	Stat stat = new Stat();
    	byte[] data = null;
		try {
			data = zk.getData(path, false, stat);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int numChildren = stat.getNumChildren();
		return numChildren;
    }

    
    
}