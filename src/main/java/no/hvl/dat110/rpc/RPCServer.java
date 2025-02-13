package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.MessagingServer;

import java.util.HashMap;

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;
	
	// hashmap to register RPC methods which are required to extend RPCRemoteImpl
	// the key in the hashmap is the RPC identifier of the method
	private HashMap<Byte,RPCRemoteImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte,RPCRemoteImpl>();
		
	}
	
	public void run() {

		// the stop RPC method is built into the server
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP,this);

		System.out.println("RPC SERVER RUN - Services: " + services.size());

		connection = msgserver.accept();

		System.out.println("RPC SERVER ACCEPTED");

		boolean stop = false;

		while (!stop) {
		/*
		   byte rpcid = 0;
		   Message requestmsg, replymsg;

			replymsg = connection.receive();

			byte[] params = replymsg.getData();
			rpcid = params[0];
			RPCRemoteImpl method = services.get(rpcid);
			params = RPCUtils.decapsulate(params);
			byte[] returnVal = method.invoke(params);
			byte[] replymsgData = RPCUtils.encapsulate(rpcid, returnVal);
			replymsg = new Message(replymsgData);
			connection.send(replymsg);


		 */
			byte rpcid = 0;
			Message requestmsg, replymsg;

			try {
				// Receive a message containing an RPC request
				replymsg = connection.receive();
				if (replymsg == null) {
					throw new RuntimeException("Failed to receive reply message");
				}

				// Extract the parameters from the received message
				byte[] params = replymsg.getData();
				if (params == null || params.length == 0) {
					throw new IllegalArgumentException("Invalid parameters in the RPC request");
				}

				// Extract the RPC identifier
				rpcid = params[0];

				// Lookup the method to be invoked
				RPCRemoteImpl method = services.get(rpcid);
				if (method == null) {
					throw new IllegalArgumentException("No method found for rpcid: " + rpcid);
				}

				// Extract the method's parameter using RPCUtils.decapsulate
				params = RPCUtils.decapsulate(params);

				// Invoke the method with the extracted parameters
				byte[] returnVal = method.invoke(params);

				// Encapsulate return value using RPCUtils.encapsulate
				byte[] replymsgData = RPCUtils.encapsulate(rpcid, returnVal);

				// Create a new message with the encapsulated return value and send it back
				replymsg = new Message(replymsgData);
				connection.send(replymsg);

			} catch (Exception e) {
				e.printStackTrace();
				// Handle exceptions and possibly send an error reply message
			}

			// - receive a Message containing an RPC request
			// - extract the identifier for the RPC method to be invoked from the RPC request
			// - extract the method's parameter by decapsulating using the RPCUtils
			// - lookup the method to be invoked
			// - invoke the method and pass the param
			// - encapsulate return value
			// - send back the message containing the RPC reply





			// stop the server if it was stop methods that was called
		   if (rpcid == RPCCommon.RPIDSTOP) {
			   stop = true;
		   }
		}
	
	}
	
	// used by server side method implementations to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {

		if (connection != null) {
			connection.close();
		} else {
			System.out.println("RPCServer.stop - connection was null");
		}
		
		if (msgserver != null) {
			msgserver.stop();
		} else {
			System.out.println("RPCServer.stop - msgserver was null");
		}
		
	}
}
