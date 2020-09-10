package com.challenge.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.challenge.entitys.MessageEntity;

/**
 * 
 * @author Ricardo Braga
 *
 */
public class RequestEncoder extends ProtocolEncoderAdapter implements ProtocolEncoder {
	
	@Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		
		MessageEntity protocol = (MessageEntity) message;
		
		List<Byte> list = new ArrayList<>();
		list.add((byte) MessageEntity.REQUEST_INT);
		list.add(protocol.getBytes());
		list.add(protocol.getFrame());
		for (Byte bytes : protocol.getData()) {
			list.add(bytes);
		}
		list.add(protocol.getCrc());
		list.add((byte) MessageEntity.REQUEST_END);
		
		IoBuffer ioBuffer = IoBuffer.allocate(list.size(), false);
		for (Byte bytes : list) {
			ioBuffer.put(bytes);
		}
		
//	case DATE_TIME:
//		DateTimeEntity dateTimeEntity = new DateTimeEntity();
//		
//		dateTimeEntity.setDay(messageEntity.getData()[k++]);
//		dateTimeEntity.setMonth(messageEntity.getData()[k++]);
//		dateTimeEntity.setYear(messageEntity.getData()[k++]);
//		dateTimeEntity.setHour(messageEntity.getData()[k++]);
//		dateTimeEntity.setMinute(messageEntity.getData()[k++]);
//		dateTimeEntity.setSecond(messageEntity.getData()[k++]);
//		
//		protocolEntity.setDateTimeEntity(dateTimeEntity);
//		break;
//	}
		
		//flip or there will be nothing to send
        ioBuffer.flip();   
        out.write(ioBuffer);
        
        //flushes all buffers you wrote
        out.flush();
    }
}