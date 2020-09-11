package com.challenge.services;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;

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

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		MessageEntity entity = processMessage((ProtocolEntity) message);
		if (entity != null) {
			session.write(entity);
		}
		session.closeOnFlush();
	}

	public MessageEntity processMessage(ProtocolEntity protocol) {

		MessageEntity messageEntity = protocol.getMessageEntity();
		if (!checkCRC8(messageEntity)) {
			return null;
		}
		
		System.out.println(messageEntity);

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
//		new Thread(daoSaveRunnable(messageEntity)).start();
		GenericDao.save(messageEntity);

		// response
		return ackReponse();
	}

	private MessageEntity handleUserInfo(ProtocolEntity protocol) {

		MessageEntity messageEntity = protocol.getMessageEntity();
		if (!checkCRC8(messageEntity)) {
			return null;
		}

		// Persistence
//		new Thread(daoSaveRunnable(messageEntity, protocol.getUserInfoEntity())).start();
		GenericDao.save(messageEntity);
		GenericDao.save(protocol.getUserInfoEntity());

		// response
		return ackReponse();
	}

	private MessageEntity handleDateTime(ProtocolEntity protocol) {

		MessageEntity messageEntity = protocol.getMessageEntity();

		if (!checkCRC8(messageEntity)) {
			return null;
		}

		// Persistence
		GenericDao.save(messageEntity);

		MessageEntity response = new MessageEntity();
		response.setFrame(Frame.DATE_TIME.getValue());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd;MM;yy;HH;mm;ss");

		// set to your timezone
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(messageEntity.getDataString()));

		//
		String[] dataArray = simpleDateFormat.format(calendar.getTime()).split(";");

		// SimpleDateFormat - 2 bytes masks
		byte[] byteArray = new byte[dataArray.length * 2];
		for (int i = 0, j = 0; i < dataArray.length * 2; j++) {
			byteArray[i++] = (byte) dataArray[j].getBytes(StandardCharsets.US_ASCII)[0];
			byteArray[i++] = (byte) dataArray[j].getBytes(StandardCharsets.US_ASCII)[1];
		}

		response.setData(byteArray);

		response.setBytes(MessageEntity.getSizeMessage(response));
		response.setCrc(CRC8.getValue(response.getCrcData()));

		// response
		return response;
	}

	private MessageEntity ackReponse() {
		MessageEntity write = new MessageEntity();

		write.setBytes((byte) 0x05);
		write.setFrame(Frame.ACK.getValue());
		write.setData(new byte[0]);
		write.setCrc(CRC8.getValue(write.getCrcData()));
		return write;
	}

	/**
	 * Check CRC8
	 * 
	 * @param entity
	 * @return CRC8 OK or NOK
	 */
	private boolean checkCRC8(MessageEntity entity) {
		return entity.getCrc() == CRC8.getValue(entity.getCrcData());
	}

	/**
	 * 
	 * @param <E>
	 * @param entity
	 * @return
	 */
	private static Runnable daoSaveRunnable(Object... entity) {
		return new Runnable() {
			public void run() {
				try {
					for (Object e : entity) {
						GenericDao.save(e);
					}
				} catch (Exception e) {
					LoggerFactory.getLogger(GenericDao.class).error(e.getMessage(), e);
				}
			}
		};
	}

}