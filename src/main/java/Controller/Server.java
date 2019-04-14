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
package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer {

	private Controller controller;

	public Server(Controller controller, int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
		this.setReuseAddr(true);
		this.controller = controller;
	}

	public Server(InetSocketAddress address) {
		super(address);
	}

	public void gossipMessage(WebSocket senderConn, String message){
		for (WebSocket conn: this.getConnections()){
			if (senderConn != conn){
				conn.send(message);
			}
		}
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		conn.send("SERVER: Welcome to the server!"); // This method sends a message to the new client
		broadcast("SERVER: new connection: " + handshake.getResourceDescriptor()); // This method sends a message to all clients
		String address = conn.getRemoteSocketAddress().getAddress().getHostAddress();
		String peerUri = "ws://"+ address + ":"+handshake.getFieldValue("server_port");
		try {
			if (!this.controller.isContainPeer(peerUri)){
				if (this.controller != null){
					this.controller.connectToPeer(peerUri);
					System.out.println(address + " connected!");
				} else {
					System.out.println("Make sure to bind controller!");				
				}
			} 
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		broadcast(conn + "SERVER:  disconnected!");
		System.out.println(conn + "SERVER:  disconnected!");
		this.controller.deletePeer(this.getAddress().toString());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		broadcast(message);
		System.out.println(conn + "-SERVER: " + message);
		gossipMessage(conn, message);
	}

	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
		broadcast(message.array());
		System.out.println(conn + "-SERVER: " + message);
	}

	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	@Override
	public void onStart() {
		System.out.println("SERVER: Peer started!");
		setConnectionLostTimeout(0);
		setConnectionLostTimeout(100);
	}

}