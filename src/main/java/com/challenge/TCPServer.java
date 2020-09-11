
package com.challenge;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.LoggerFactory;

import com.challenge.services.AppService;
import com.challenge.util.CustomProtocolCodecFactory;


public class TCPServer {

	private static final int PORT = 5151;

	public static void main(String[] args) {

		System.out.printf(
"		          ___/O   O\\____ \n" +
"		         / O        O   \\ \n" +
" 		         \\______________/ \n" +
"		    -===|____\\///\\\\\\/_____ \n" +
"		        \\----------------/ \n" +
"		         \\______________/  \\/ \n" +
"		          /\\__________/    // \n" +
"		   >=o\\  // //\\\\   || \\\\  // \n" +
"		      \\\\o/ //  \\o  ||  \\o// \n" +
"		          //    || || \n" +
"		      /o==o     |o \\o==o    \n" +
"		     //         //     \\\\ \n" +
"		     /\\        //       /\\   Tantive (TCP Server) \n" +
" 		              /\\              \u001B[30m\u001B[47m- Ricardo Braga - \u001B[0m\n\n\n" 
				);
		
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CustomProtocolCodecFactory(StandardCharsets.US_ASCII)));
		acceptor.setHandler(new AppService());
		
		SocketSessionConfig socketSessionConfig  = (SocketSessionConfig) acceptor.getSessionConfig();
		socketSessionConfig.setWriteTimeout(60);
		
		try {
			acceptor.bind(new InetSocketAddress(PORT));
		} catch (IOException e) {
			LoggerFactory.getLogger(TCPServer.class).error(e.getMessage(), e);
			System.exit(1);
		}
		
		System.out.println("Waiting for messages ...");
	}
}
