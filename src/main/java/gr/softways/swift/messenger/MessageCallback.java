package gr.softways.swift.messenger;

import java.util.List;

/**
 *
 * @author Panos
 */
public class MessageCallback {

  public String object;
  public List<Entry> entry;

  public static class Entry {

    public long id;
    public long time;
    public List<Messaging> messaging;
  }

  public static class Messaging {

    public Sender sender;
    public Recipient recipient;
    public long timestamp;
    public Message message;
    public Delivery delivery;
  }

  public static class Sender {

	public long id;
  }

  public static class Recipient {

	public long id;
  }

  public static class Message {

    public String mid;
    public long seq;
    public String text;
    public long sticker_id;
    
    public List<Attachment> attachments;
  }

  public static class Delivery {

    public List<String> mids;
    public long watermark;
    public long seq;
  }
  
  public static class Attachment {
	
    public final String type = "template";
	
    public Payload payload;
  }
  
  public static class Payload {
	
    public final String template_type = "generic";
    
    public String url;
  }
}