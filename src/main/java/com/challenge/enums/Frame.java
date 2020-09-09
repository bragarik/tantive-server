package com.challenge.enums;

import java.util.HashMap;
import java.util.Map;

public enum Frame {

	ACK(0xA0), TEXT_MESSAGE(0xA1), USER_INFO(0xA2), DATE_TIME(0xA3);

	private byte value;
	private static Map<Integer, Frame> map = new HashMap<Integer, Frame>();

	static {
		for (Frame v : Frame.values()) {
			map.put((int) v.value, v);
		}
	}

	Frame(int value) {
		this.value = (byte) value;
	}

	public byte getValue() {
		return value;
	}

	public static Frame valueOf(int value) {
		return (Frame) map.get(value);
	}
}
