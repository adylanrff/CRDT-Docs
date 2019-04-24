/*
 * Copyright (c) 2010-2019 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */
package com.sisterhore.messenger;

import java.io.IOException;
import java.net.BindException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Base64;

import com.sisterhore.controller.Controller;
import com.sisterhore.controller.Operation;
import com.sisterhore.socket.server.AbstractSocketServer;
import com.sisterhore.util.Handshake;
import com.sisterhore.util.Serializer;
import com.sisterhore.util.SocketComm;

public class MessengerServer extends AbstractSocketServer {

	private Controller controller;

	public MessengerServer(Controller controller, int port) throws UnknownHostException {
		super(port);
		this.controller = controller;
	}

	@Override
	public void onOpen(Socket conn) {
		String handshakeData;
		Handshake handshake = null;
		try {
			handshakeData = SocketComm.read(conn);
			byte[] bytes = Base64.getDecoder().decode(handshakeData);
			handshake = (Handshake) Serializer.deserialize(bytes);
			System.out.println(handshake);
			SocketComm.write(conn, "SERVER: Welcome to the server!"); // This method sends a message to the new client
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String peerUri = handshake.host+":"+handshake.port;
		try {
			if (!this.controller.isContainPeer(peerUri)){
				if (this.controller != null){
					this.controller.connectToPeer(peerUri);
					System.out.println(peerUri + " connected!");
				} else {
					System.out.println("Make sure to bind controller!");				
				}
			} 
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClose(Socket conn) {
		System.out.println(conn + "SERVER:  disconnected!");
		this.controller.deletePeer(conn.getInetAddress().getHostAddress());
	}

	@Override
	public void onMessage(Socket conn, String message) {
		Operation operation = Operation.getOperation(message);
		System.out.println(operation);
	}

	@Override
	public void onError( Socket conn, Exception ex ) {
		if (ex.getClass() != null && ex.getClass() == BindException.class){
			System.err.println("Address already in use");
			System.exit(1);
		}

		if( conn != null ) {
			ex.printStackTrace();
		}
	}

}