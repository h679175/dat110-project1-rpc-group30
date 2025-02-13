package no.hvl.dat110.messaging;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		byte[] segment = null;
		byte[] data;
		byte payloadLength;

		data = message.getData();

		segment = new byte[SEGMENTSIZE];
		payloadLength = (byte) data.length;

		segment[0] = payloadLength;
		for (int i=1; i<=data.length; i++) {
			segment[i] = data[i-1];
		}

		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {

		Message message = null;
		byte[] payload;

		payload = new byte[segment[0]];

		for (int i=0; i<payload.length;i++) {
			payload[i] = segment[i+1];
		}

		message = new Message(payload);
		
		return message;
		
	}
	
}
