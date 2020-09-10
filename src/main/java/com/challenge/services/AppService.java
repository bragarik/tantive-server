package com.challenge.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.challenge.dao.GenericDao;
import com.challenge.entitys.MessageEntity;
import com.challenge.entitys.ProtocolEntity;
import com.challenge.enums.Frame;
import com.challenge.util.CRC8;

/**
 * 
 * @author Ricardo Braga
 *
 */
public class AppService extends IoHandlerAdapter {

	private GenericDao dao = new GenericDao();

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		MessageEntity entity = processMessage((ProtocolEntity) message);
		if (entity != null) {
			session.write(entity);
			session.closeOnFlush();
		}
	}

	public MessageEntity processMessage(ProtocolEntity protocol) {

		MessageEntity messageEntity = protocol.getMessageEntity();
		if (!checkCRC8(messageEntity)) {
			return null;
		}

		switch (Frame.valueOf(messageEntity.getFrame())) {
		case ACK:
			return null;
		case TEXT_MESSAGE:
			return handleTextMessage(protocol);
		case USER_INFO:
			return handleUserInfo(protocol);
		case DATE_TIME:
			return handleDateTime(protocol);
		}

		return null;
	}

	private MessageEntity handleTextMessage(ProtocolEntity protocol) {

		MessageEntity messageEntity = protocol.getMessageEntity();
		if (!checkCRC8(messageEntity)) {
			return null;
		}

		// Persistence
		dao.saveProtocol(messageEntity);

		// response
		return ackReponse();
	}

	private MessageEntity handleUserInfo(ProtocolEntity protocol) {

		MessageEntity messageEntity = protocol.getMessageEntity();
		if (!checkCRC8(messageEntity)) {
			return null;
		}

		// Persistence
		dao.saveProtocol(messageEntity);
		dao.saveProtocol(protocol.getUserInfoEntity());

		// response
		return ackReponse();
	}

	private MessageEntity handleDateTime(ProtocolEntity protocol) {

		MessageEntity messageEntity = protocol.getMessageEntity();

		if (!checkCRC8(messageEntity)) {
			return null;
		}

		MessageEntity response = new MessageEntity();
		response.setFrame(Frame.DATE_TIME.getValue());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyHHmmss"); //TODO: Corrigir

		// set to your timezone
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(messageEntity.getDataString()));
		response.setData(simpleDateFormat.format(calendar.getTime()).getBytes());

		response.setBytes(MessageEntity.getSizeMessage(response));
		response.setCrc(CRC8.getValue(response.getCrcData()));

		// response
		return response;
	}

	private MessageEntity ackReponse() {
		MessageEntity write = new MessageEntity();

		write.setBytes((byte) 0x05);
		write.setFrame((byte) 0xa0);
		write.setData(new byte[0]);
		write.setCrc((byte) 0x28);
		return write;
	}

	/**
	 * Check CRC8
	 * 
	 * @param messageEntity
	 * @return CRC8 OK or NOK
	 */
	private boolean checkCRC8(MessageEntity messageEntity) {
		return messageEntity.getCrc() == CRC8.getValue(messageEntity.getCrcData());
	}

}