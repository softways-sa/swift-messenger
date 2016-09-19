package gr.softways.swift.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
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
  
  private final String VERIFY_TOKEN = "Rfgh34fdvvcvRHGcewqaTYgbhFTREccaa";
  
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
		  
		  if ("προσφορές".equalsIgnoreCase(messaging.message.text)) {
			StructuredMessage structuredMessage = new StructuredMessage();
		
			structuredMessage.recipient = new StructuredMessage.Recipient();
			structuredMessage.recipient.id = messaging.sender.id;
			structuredMessage.message = new StructuredMessage.Message();
			structuredMessage.message.attachment = new StructuredMessage.Attachment();
			structuredMessage.message.attachment.payload = new StructuredMessage.Payload();

			structuredMessage.message.attachment.payload.elements = new ArrayList();

			StructuredMessage.Elements element = new StructuredMessage.Elements();
			element.image_url = "http://shopdemo.softways.gr/prd_images/61091-1.jpg";
			element.item_url = "http://shopdemo.softways.gr/site/product/RED-SAWTOOTH-65-10-BACKPACK?prdId=61091&extLang=";
			structuredMessage.message.attachment.payload.elements.add(element);
			
			// send response
			//this.outbox.send(response);
		  }
		  
		}
	  }
	}
	
	return new ResponseEntity(HttpStatus.OK);
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
