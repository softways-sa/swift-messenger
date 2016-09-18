package gr.softways.swift.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SwiftMessengerApplication extends SpringBootServletInitializer {
  
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(SwiftMessengerApplication.class);
  }

  public static void main(String[] args) {
	  SpringApplication.run(SwiftMessengerApplication.class, args);
  }
}
