// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package shipeditor;

import java.net.*;

public class SimpleSocket
{
	DatagramSocket sock;
	byte[] b;
	DatagramPacket packet;
	
	public SimpleSocket()
	{
		b = new byte[1024];
	}
	public void send(String address, int port, String message) throws Exception
	{
		sock = new DatagramSocket();
		b = message.getBytes();
		packet = new DatagramPacket(b , b.length , InetAddress.getByName(address) , port);
		sock.send(packet);
		packet = null;
		sock.close();
	}
	public String[] recv(int port) throws Exception
	{
		sock = new DatagramSocket(port);
		packet = new DatagramPacket(b , b.length);
		sock.receive(packet);
		sock.close();
		return new String[] {packet.getAddress().toString().replace("/", ""), new String(packet.getData(), 0, packet.getLength()), "" + packet.getPort()};
	}
	public static void echo(String msg)
    {
        System.out.println(msg);
    }
	
}
