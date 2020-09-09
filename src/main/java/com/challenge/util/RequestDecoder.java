package com.challenge.util;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.challenge.entitys.Protocol;



public class RequestDecoder extends CumulativeProtocolDecoder {
	
  @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
      in.setAutoExpand(true);
      in.setAutoShrink(true);
      if (in.hasRemaining()) {
    	  int i= 0, j = 0;
          char testIntChar = (char)in.get(i++);
          char testEndChar = (char)in.get(in.limit()-1);
          if (testIntChar != Protocol.REQUEST_INT && testEndChar != Protocol.REQUEST_END) {
              return false;
          } else {
        	  Protocol protocol = new Protocol();
        	  
        	  protocol.setBytes(in.get(i++));
        	  protocol.setFrame(in.get(i++));
        	  
        	  //- 5 (except init, bytes, frame, crc and end)
        	  int size = (int)protocol.getBytes() - 5;
        	  byte data[] = new byte[size]; 
        	  for (;j < size;) {
        		  data[j++] = in.get(i++);
        	  }
        	  
        	  protocol.setData(data);
        	  
        	  protocol.setCrc(in.get(i));
        	  
        	  in.position(in.limit());
        	  out.write(protocol);
        	  return true;
        	  
          }
      }
      return false;
    }
}