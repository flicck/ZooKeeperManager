package cn.zkManager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.zkManager.App;
import cn.zkManager.service.impl.ZKService;



//前两个是做页面跳转的时候需要用的注释
@Controller
@ControllerAdvice //指responsebody加controller

//后一个是后端向前端发送数据用的RestController
@CrossOrigin
//@RestController
public class UserController {

	//由于没有了web.xml,这样跳转页面首页
//	@Autowired
//	private VideoInfoServiceImpl bi;

	@RequestMapping("/")
	public String sayHello(){
		//再次尝试加载，以防止连接断开
		ZKService.connectZK(App.connectString);
		return "forward:/index.jsp";
	}


	@ExceptionHandler(Exception.class)
	public ModelAndView showErrorMsg(Exception e){
		ModelAndView mv=new ModelAndView();
		mv.addObject("msg",e);
		mv.setViewName("forward:/error.jsp");
		return mv;
	}
}
