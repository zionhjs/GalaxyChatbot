package com.galaxy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //该注解可以自动注入@WebServlet，@WebFilter，@WebListener。否则在过滤器中无法获取到自动注入得bean
@SpringBootApplication
public class GalaxychatbotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalaxychatbotsApplication.class, args);
	}

}
