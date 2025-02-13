package no.hvl.dat110.system.display;

import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;


public class DisplayDevice {
	
	public static void main(String[] args) {
		
		System.out.println("Display server starting ...");

		//RPCClient displayClient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
		RPCServer displayServer = new RPCServer(Common.DISPLAYPORT);

		DisplayImpl display = new DisplayImpl((byte)Common.WRITE_RPCID, displayServer);

		displayServer.run();

		displayServer.stop();

		// implement the operation of the display RPC server
		// see how this is done for the sensor RPC server in SensorDevice

		
		System.out.println("Display server stopping ...");
		
	}
}
