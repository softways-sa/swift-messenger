package gr.softways.swift.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
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
  public ResponseEntity receive(@RequestBody MessageCallback message) {
	logJSON(message);
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
