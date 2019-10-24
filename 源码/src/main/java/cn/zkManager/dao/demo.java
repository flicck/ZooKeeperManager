package cn.zkManager.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.ZooKeeper;



public class demo {
	public static void main(String[] args) throws UnsupportedEncodingException {
		 ZKDAO zkdao = new ZKDAO();
		Map<String, String> nodeInfo = zkdao.getNodeInfo("/hbase/meta-region-server");
		String value = nodeInfo.get("nodeValue");
//	
//		 new String(value,"utf-8");
		System.out.println();
		 
	}
}
