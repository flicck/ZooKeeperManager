package cn.zkManager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.zkManager.service.impl.ZKService;


//这里提供前台页面为插入数据

@RestController //responsebody加 controller
public class Action {
	private static final Integer pageSize=10;
	@InitBinder
	  public void format(WebDataBinder wb){
		  wb.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	  }
	//获得某一页，需要传入两个参数，页数和path
	@RequestMapping("/findByPage")
	public  Map<String,Object> findByPage(Integer pageIndex,String path){
		
		 List<String> totalNodeList = ZKService.getNodeList(path);
		
		 //划分nodeList为不同的页数
		 	//获得总页数
		 int size = totalNodeList.size();
		 int totalPage = (size%10==0)?(size/10):((size/10)+1);
		 	//获得需要返回的页
		 if(pageIndex > totalPage){
			 pageIndex = totalPage;
		 }
		 	//计算下标
		 int startIndex = (pageIndex-1)*10;
		 int endIndex = startIndex + 9;
		 if(endIndex>totalNodeList.size()-1){
			 endIndex = totalNodeList.size()-1;
		 }
		 List<String> nodeList = new ArrayList<>();
		 for(int x= startIndex;x<=endIndex;x++){
			 nodeList.add(totalNodeList.get(x));
		 }
		 Map<String,Object> ma1 = new HashMap<String,Object>();
		
		 ma1.put("totalPage", totalPage);
		 ma1.put("pageIndex", pageIndex);
		 ma1.put("nodeList", nodeList);
		 ma1.put("path", path);
		 ma1.put("num", size);
		 return ma1;
	}
	@RequestMapping("/findNodeInfo")
	public Map<String,String> findNodeInfo(String absolutePath){
		Map<String, String> nodeInfo = ZKService.getNodeInfo(absolutePath);
		return nodeInfo;
	}
	@RequestMapping("/update")
	public void setUpdateDate(String absolutePath,String recData){
		ZKService.updateNode(absolutePath, recData);
	}
	@RequestMapping("/updateEntry")
	public ModelAndView updateEntry(String absolutePath,Integer page){
		ModelAndView mav=new ModelAndView();
		mav.addObject("nodeInfo", ZKService.getNodeInfo(absolutePath));
		mav.addObject("absolutePath",absolutePath);
		mav.addObject("page", page);
		mav.setViewName("forward:update.jsp");
		return mav;	
	}
	@RequestMapping("/addEntry")
	public ModelAndView addEntry(String path,Integer page){
		ModelAndView mav=new ModelAndView();
		mav.addObject("path",path);
		mav.addObject("page", page);
		mav.setViewName("forward:add.jsp");
		return mav;	
	}
	@RequestMapping("/addDownEntry")
	public ModelAndView addDownEntry(String path,Integer page){
		ModelAndView mav=new ModelAndView();
		mav.addObject("path",path);
		mav.addObject("page", page);
		mav.setViewName("forward:addDown.jsp");
		return mav;	
	}
	@RequestMapping("/updateC")
	public ModelAndView updateC(String absolutePath,Integer page,String nodeValue){
		ModelAndView mav = new ModelAndView();
		ZKService.updateNode(absolutePath, nodeValue);
		mav.setViewName("redirect:list.jsp?page="+page+"&"+"absolutePath="+absolutePath);
		return mav;
	}
	@RequestMapping("/addNode")
	public ModelAndView addNode(String path,Integer page,String nodeName,String nodeValue){
		ModelAndView mav = new ModelAndView();
		
		if(path.equals("/")){
			ZKService.addNode("/"+nodeName, nodeValue);
		}else{
			ZKService.addNode(path+"/"+nodeName, nodeValue);
		}
		 List<String> totalNodeList = ZKService.getNodeList(path);
		 int indexOf = totalNodeList.indexOf(nodeName);
		 int nowPage = (indexOf/10)+1;
		 URLEncoder urlEncoder = new  URLEncoder();
		mav.setViewName("redirect:list.jsp?page="+nowPage+"&"+"absolutePath="+urlEncoder.encode(path,"UTF-8")+urlEncoder.encode("/","UTF-8")+urlEncoder.encode(nodeName,"UTF-8"));
		 return mav;
	}
	@RequestMapping("/addNodeDown")
	public ModelAndView addNodeDown(String path,Integer page,String nodeName,String nodeValue){
		ModelAndView mav = new ModelAndView();
		ZKService.addNode(path+"/"+nodeName, nodeValue);
		 URLEncoder urlEncoder = new  URLEncoder();
		mav.setViewName("redirect:list.jsp?page="+1+"&"+"absolutePath="+urlEncoder.encode(path,"UTF-8")+"/xx");
		return mav;
	}
	@RequestMapping("/delete")
	public String delete(String absolutePath){
		ZKService.deleteNode(absolutePath);
		//获得上一级节点，看一看现在这个上级节点还有没有数据
		String tmpStr = "";
		String tmpStr1 = "";
		// /xxx/xx
		String[] split = absolutePath.split("/");
		if(split.length>=4){
			for(int i=1;i<split.length-1;i++){
				// "" xxx xx
				tmpStr = tmpStr +"/"+split[i];
			}
			for(int i=1;i<split.length-2;i++){
				// "" xxx xx
				tmpStr1 = tmpStr1 +"/"+split[i];
			}	
		}
		if(split.length==3){
			for(int i=1;i<split.length-1;i++){
				// "" xxx xx
				tmpStr = tmpStr +"/"+split[i];
			}
			tmpStr1 = "/";
		}
		
		int	numChildren = -1;
		// /xx
		if(split.length==2){
			tmpStr1 = "/";
		}else{
			numChildren = ZKService.getNumChildren(tmpStr);
		}
	
	
		if(numChildren<=0){
			return tmpStr1;
		}else{
			return tmpStr;
		}
	}

}
