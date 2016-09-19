package gr.softways.swift.messenger;

import java.util.List;

/**
 *
 * @author Panos
 */
public class StructuredMessage {
  
  public Recipient recipient;
  
  public Message message;
  
  public static class Recipient {
    public long id;
  }
  
  public static class Message {
	
    public Attachment attachment;
  }
  
  public static class Attachment {
	
    public final String type = "template";
	
    public Payload payload;
  }
  
  public static class Payload {
	
    public final String template_type = "generic";
	
    public List<Element> elements;
  }
  
  public static class Element {
	
    public String title;
    public String subtitle;
    public String item_url;
    public String image_url;
  }
}
