package org.example.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 类说明：
 */
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
	
	 @Override
	   public void addViewControllers(ViewControllerRegistry registry) {
	       registry.addViewController("/ws").setViewName("/ws");
	   }

}
