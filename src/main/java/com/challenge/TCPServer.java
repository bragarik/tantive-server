
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

	/**
	 * Usage args: <code>port</code>
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.printf(
"	.    .     .            +         .         .                 .  . \n"
+ "  .                 .                   .               .          \n"
+ "          .    ,,o         .                  __.o+.               \n"
+ ".            od8^                  .      oo888888P^b           .  \n"
+ "   .       ,'.o'      .     .             `b^'''`b -`b   .         \n"
+ "         ,'.'o'             .   .          t. = -`b -`t.    .      \n"
+ "        ; d o' .        ___          _.--.. 8  -  `b  =`b          \n"
+ "    .  dooo8<       .o:':__;o.     ,;;o88$$8bb - = `b  =`b.    .   \n"
+ ".     |^88^88=. .,x88/::/ | \\\\`;;;;;;d$$$$$88$88888/$x88888      \n"
+ "      :-88=88$$L8`$`|::|_>-<_||$;;$;8$$=;:::=$8;;\\$$$$\\8888      \n"
+ "  .   |=88 88$$|HHHH|::| >-< |||;$;;8$$=;:::=$8;;;$$$$+|]88        \n"
+ "      | 88-88$$LL.$.$b::Y_|_Y/$|;;;;`$8$$oo88$:o$.;;;;+|]88  .     \n"
+ "      Yx88o88^^''`^^$8boooood..-\\H_Hd$P$$88$P^$$^'\\;;;/$$88      \n"
+ "     . `'\\^\\          ~''''''      d$P '''^' ;   = `+' - P       \n"
+ "        `.`.b   .                :<$$>  .   :  -   d' - P      . . \n"
+ "          .`.b     .        .    `788      ,'-  = d' =.'           \n"
+ "   .       ``.b.                           :..-  :'  P             \n"
+ "        .   `q.>b         .               `^^^:::::,'       .      \n"
+ "              ''^^               .                     .           \n"
+ "                                          .               .       .\n"
+ ".         .          .                 .        +         .        \n"
+ "                                Tantive (TCP Server)               \n"
+ "	                               \u001B[30m\u001B[47m- Ricardo Braga - \u001B[0m\n\n\n" 
				);
		
		IoAcceptor acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new CustomProtocolCodecFactory(StandardCharsets.US_ASCII)));

		int port;

		// Usage args: <code>port</code>
		if (args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				LoggerFactory.getLogger(TCPServer.class).warn("Ignored arg: " + e.getMessage(), e);
				port = PORT;
			}
		} else {
			port = PORT;
		}


		acceptor.setHandler(new AppService());

		SocketSessionConfig socketSessionConfig = (SocketSessionConfig) acceptor.getSessionConfig();
		socketSessionConfig.setWriteTimeout(60);

		try {
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			LoggerFactory.getLogger(TCPServer.class).error(e.getMessage(), e);
			System.exit(1);
		}

		System.out.println("Waiting for messages ...");
	}
}
