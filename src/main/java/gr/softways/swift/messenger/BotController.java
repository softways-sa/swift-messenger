package gr.softways.swift.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
  
  private static final String VERIFY_TOKEN = "Rfgh34fdvvcvRHGcewqaTYgbhFTREccaa";
  private static final String PAGE_ACCESS_TOKEN = "EAAC3nmXGgkEBADgag5blAkUv1wq3h2MiqSHVsLvEQ25RxEl6DCr4JZB4QZApeoFlaChR4Xr2zU03GcqK50xMESepZB3vM4e7utpNom661LlWGU9htIFyRhoZChoapkJ52ANQC1ZAaak6ubjht4Kgm72XpFF7CZBdZCVs8m38lf2IAZDZD";
  
  private static final String MESSAGE_POST_ENDPOINT_FORMAT = "https://graph.facebook.com/v2.7/me/messages?access_token=";
  
  private static final okhttp3.MediaType JSON_MEDIA_TYPE = okhttp3.MediaType.parse("application/json; charset=utf-8");
  
  @Autowired
  private OkHttpClient client;
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @Autowired
  private ExecutorService executorService;
  
  @RequestMapping(value = "/v1/webhook", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
  public String verify(@RequestParam("hub.verify_token") String verify_token,
					   @RequestParam("hub.challenge") String challenge) {
	
	if (VERIFY_TOKEN.equals(verify_token)) {
	  return challenge;
	}
	
	return "Error, wrong validation token";
  }
  
  @RequestMapping(value = "/v1/webhook", method = RequestMethod.POST)
  public ResponseEntity receive(@RequestBody MessageCallback messageCallback) {
    logJSON(messageCallback);

    for (final MessageCallback.Entry message : messageCallback.entry) {
      for (final MessageCallback.Messaging messaging : message.messaging) {
        // Only deal with messages, drop deliveries on the floor.
        if (messaging.message != null && messaging.message.text != null) {
          if ("προσφορες".equalsIgnoreCase(messaging.message.text)) {
            StructuredMessage structuredMessage = new StructuredMessage();

            structuredMessage.recipient = new StructuredMessage.Recipient();
            structuredMessage.recipient.id = messaging.sender.id;
            structuredMessage.message = new StructuredMessage.Message();
            structuredMessage.message.attachment = new StructuredMessage.Attachment();
            structuredMessage.message.attachment.payload = new StructuredMessage.Payload();

            structuredMessage.message.attachment.payload.elements = new ArrayList();

            StructuredMessage.Element element = new StructuredMessage.Element();
            element.title = "Σακίδιο πλάτης";
            element.subtitle = "Ότι χρειάζεται ο αναβάτης";
            element.image_url = "http://shopdemo.softways.gr/prd_images/61091-1.jpg";
            element.item_url = "http://shopdemo.softways.gr/site/product/RED-SAWTOOTH-65-10-BACKPACK?prdId=61091&extLang=";
            structuredMessage.message.attachment.payload.elements.add(element);
            
            element = new StructuredMessage.Element();
            element.title = "Men's Waterproof Jacket";
            element.subtitle = "by Timberland";
            element.image_url = "http://shopdemo.sw.softways.gr/prd_images/107.11.004-1.jpg";
            element.item_url = "http://shopdemo.sw.softways.gr/site/product/Men-s-Waterproof-Jacket?prdId=107.11.004&extLang=";
            structuredMessage.message.attachment.payload.elements.add(element);

            callSendAPI(structuredMessage);
          }
        }
      }
    }

    return new ResponseEntity(HttpStatus.OK);
  }
  
  private void callSendAPI(StructuredMessage structuredMessage) {
    executorService.submit(newPostRunnable(structuredMessage));
  }
  
  private Runnable newPostRunnable(final StructuredMessage structuredMessage) {
    return () -> {
      try {
        final String responseString = objectMapper.writeValueAsString(structuredMessage);
        System.out.println("our request: " + responseString);
        
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(JSON_MEDIA_TYPE, responseString);
        final Request request = new Request.Builder().url(MESSAGE_POST_ENDPOINT_FORMAT + PAGE_ACCESS_TOKEN).post(requestBody).build();
        final Response response = client.newCall(request).execute();
        System.out.println(response.toString());
      } catch (final IOException e) {
        e.printStackTrace();
      }
    };
  }
  
  private void logJSON(MessageCallback object) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      //Object to JSON in String
      String jsonInString = mapper.writeValueAsString(object);
      System.out.println(jsonInString);
    }
    catch (Exception e) {
    }
  }
}
