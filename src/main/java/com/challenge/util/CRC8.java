package com.challenge.util;

import java.nio.ByteBuffer;

public class CRC8 {

	private final short init;

	private short value;

	private static final short CRC_POLYNOM = 0x07;

	private static final short CRC_INITIAL = 0x00;

	static {

	}

	public CRC8() {
		this.value = this.init = CRC_INITIAL;
	}

	private void update(byte[] buffer, int offset, int len) {
		short crc = CRC_INITIAL;
		int i, j;
		for (i = 0; i < len; i++) {
			crc ^= buffer[i];
			for (j = 0; j < 8; j++) {
				if ((crc & 0x80) != 0)
					crc = (short) ((crc << 1) ^ CRC_POLYNOM);
				else
					crc <<= 1;
			}
		}
		value = crc;
	}

	public void update(byte[] buffer) {
		update(buffer, 0, buffer.length);
	}

	public void update(long buffer) {
		update(longToBytes(buffer));
	}

	public byte[] longToBytes(long l) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(l);
		return buffer.array();
	}

	public long getValue() {
		return value & 0xff;
	}

	public void reset() {
		value = init;
	}

}