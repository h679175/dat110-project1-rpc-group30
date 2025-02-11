package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		byte[] data;

//		data = MessageUtils.encapsulate(message);
		try {
			data = MessageUtils.encapsulate(message);
			outStream.write(data);
			outStream.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public Message receive() {

		Message message = null;
		byte[] data;

        try {
            //data = inStream.readAllBytes();
			byte[] segment = new byte[MessageUtils.SEGMENTSIZE];
			int bytesRead = inStream.read(segment, 0, MessageUtils.SEGMENTSIZE);


			if (bytesRead == MessageUtils.SEGMENTSIZE) {
				message = MessageUtils.decapsulate(segment);
			} else {
				System.err.println("Incorrect segment size received. Expected: " + MessageUtils.SEGMENTSIZE + ", but read: " + bytesRead);
			}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		return message;
		
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}