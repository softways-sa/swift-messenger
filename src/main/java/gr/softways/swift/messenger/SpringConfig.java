package gr.softways.swift.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Panos
 */
@Configuration
public class SpringConfig {
  
  @Bean
  public OkHttpClient client() {
    return new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(5);
  }
}