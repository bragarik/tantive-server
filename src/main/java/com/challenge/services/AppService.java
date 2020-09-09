package com.challenge.services;

import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.challenge.dao.ProtocolDao;
import com.challenge.entitys.Protocol;

public class AppService extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		session.write(processMessage((Protocol) message));
		session.closeOnFlush();
	}

	public Protocol processMessage(Protocol message) {

//		switch (message.getFrame()) {
//		case Frame.ACK.getValue(): {
//			break;
//		}
//		case Frame.ACK.getValue(): {
//			break;
//		}
//		case Frame.ACK.getValue(): {
//			break;
//		}
//		case Frame.ACK.getValue(): {
//			break;
//		}
//		default:
//		}

		ProtocolDao dao = new ProtocolDao();

//		handleMessage(message);

		dao.saveProtocol(message);

		Protocol write = new Protocol();

		write.setBytes((byte) 0x05);
		write.setFrame((byte) 0xa0);
		write.setData(new byte[0]);
		write.setCrc((byte) 0x28);

		List<Protocol> protocol = dao.getAllProtocols();
		protocol.forEach(s -> System.out.println(s.toString()));

		return write;
	}
}