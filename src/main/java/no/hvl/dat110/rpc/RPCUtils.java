package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class RPCUtils {
	
	public static byte[] encapsulate(byte rpcid, byte[] payload) {
		
		byte[] rpcmsg = null;

		rpcmsg = new byte[128];

		rpcmsg[0] = rpcid;
		byte rpcmsgLength = (byte) payload.length;

		for(int i = 1; i <= rpcmsgLength; i++) {
			rpcmsg[i] = payload[i-1];
		}


		
		return rpcmsg;
	}
	
	public static byte[] decapsulate(byte[] rpcmsg) {
		
		byte[] payload = null;

		int payloadLength = rpcmsg.length - 1;
		payload = new byte[payloadLength];

		for(int i = 0; i<payloadLength; i++){
			payload[i] = rpcmsg[i+1];
		}

		
		return payload;
		
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {
		
		byte[] encoded = null;

        try {
            encoded = str.getBytes("UTF-16");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

		return encoded;
	}

	// convert byte array to a String
	public static String unmarshallString(byte[] data) {
		
		String decoded = null;

        try {
            decoded = new String(data, "UTF-16");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

		return decoded;
	}
	
	public static byte[] marshallVoid() {
		
		byte[] encoded = null;
		
		// TODO - START 
		
		if (true)
			throw new UnsupportedOperationException(TODO.method());
				
		// TODO - END
		
		return encoded;
		
	}
	
	public static void unmarshallVoid(byte[] data) {
		
		// TODO
		
		if (true)
			throw new UnsupportedOperationException(TODO.method());
		
	}

	// convert boolean to a byte array representation
	public static byte[] marshallBoolean(boolean b) {
		
		byte[] encoded = new byte[1];
				
		if (b) {
			encoded[0] = 1;
		} else
		{
			encoded[0] = 0;
		}
		
		return encoded;
	}

	// convert byte array to a boolean representation
	public static boolean unmarshallBoolean(byte[] data) {
		
		return (data[0] > 0);
		
	}

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {
		
		byte[] encoded = null;

		encoded = ByteBuffer.allocate(4).putInt(x).array();
		
		return encoded;
	}
	
	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {
		
		int decoded = 0;

		decoded = ByteBuffer.wrap(data).getInt();
		
		return decoded;
		
	}
}
