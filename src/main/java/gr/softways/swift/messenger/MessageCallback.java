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
  }

  public static class Delivery {

	public List<String> mids;
	public long watermark;
	public long seq;
  }
}
