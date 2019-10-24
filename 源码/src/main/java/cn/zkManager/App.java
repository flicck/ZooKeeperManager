package cn.zkManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.zkManager.service.impl.ZKService;

			 //代表这个是一个控制层,相当于responsebody加controller的注解
//@EnableAutoConfiguration	//包装了一堆组件，这个标签代表会把一些常用的
							//组件加载进来,包括spring mvc等
							//光有这个注释还不够，还要能扫描
@SpringBootApplication(exclude = {
DataSourceAutoConfiguration.class,
DataSourceTransactionManagerAutoConfiguration.class,
HibernateJpaAutoConfiguration.class})							
	//所以用这个更上层注释，这个注释可以让spring
							//扫描这个类下面的包
							//springbootapplication是一个复合注解
							//包括@ComponentScan，和@SpringBootConfiguration，@EnableAutoConfiguration。
@Configuration
public class App extends SpringBootServletInitializer implements WebMvcConfigurer{
	//保存这个连接参数，供连接失效后controller再次连接使用
	public  static String connectString = "";
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowCredentials(true)
		.allowedHeaders("*")
		.allowedOrigins("*")
		.allowedMethods("*");
	}
	public static void main(String[] args){
		//初始化ZKServer类，让它赶紧连接上
		connectString = args[0];
		ZKService.connectZK(args[0]);
		//记住：
		//第一个传启动类，第二个传定制化参数
		SpringApplication.run(App.class, args);
	}
	//为了打包
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		 return builder.sources(this.getClass());
	}
}
