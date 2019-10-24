package cn.zkManager.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import cn.zkManager.dao.ZKDAO;


public class ZKService {
	private static ZKDAO zkdao = new ZKDAO();
	//连接zookeeper
	public static void connectZK(String connect){
		try {
			zkdao.connectionZooKeeper(connect);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//获得节点名数组
	public static List<String> getNodeList(String path){
		return zkdao.getListData(path);
		
	}
	//获得节点信息
	public static Map<String,String> getNodeInfo(String nodePath){
		try {
			return zkdao.getNodeInfo(nodePath);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//删除
	public static void deleteNode(String nodePath){
		zkdao.deleteNode(nodePath);
	}
	//更新
	public static void updateNode(String nodePath,String data){
		zkdao.setUpdateData(nodePath, data);
	}
	//新增
	public static void addNode(String nodePath,String data){
		zkdao.createNode(nodePath, data);
	}
	//获得有几个子节点信息
	public static int getNumChildren(String nodePath){
		int numOfChildren = zkdao.getNumOfChildren(nodePath);
		return numOfChildren;
	}
	
	
}
