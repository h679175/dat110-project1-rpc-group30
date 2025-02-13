package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.MessagingClient;

public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
	
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() {
		connection = msgclient.connect();

	}
	
	public void disconnect() {
		
		connection.close();
	}

	/*
	 Make a remote call om the method on the RPC server by sending an RPC request message and receive an RPC reply message

	 rpcid is the identifier on the server side of the method to be called
	 param is the marshalled parameter of the method to be called
	 */

	public byte[] call(byte rpcid, byte[] param) {

		byte[] returnval = null;

		try {
			byte[] rpcRequestMessage = RPCUtils.encapsulate(rpcid, param);


			System.out.println("rpcReqMsg " + rpcRequestMessage);

			Message message = new Message(rpcRequestMessage);
			System.out.println(message.getData() +" data");
			connection.send(message);
			Message replyMessage;
			replyMessage = connection.receive();
			returnval = replyMessage.getData();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnval;
	}


}
