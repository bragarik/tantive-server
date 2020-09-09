package com.challenge.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCRC8 {

	@Test
	public void testUpdateByteArray() {
		CRC8 crc = new CRC8();

		// https://crccalc.com/?crc=090131323334&method=crc8&datatype=hex&outtype=hex
		crc.update(new byte[] { 0x09, 0x01, 0x31, 0x32, 0x33, 0x34 });
		assertEquals(0xc6, crc.getValue());

		// https://crccalc.com/?crc=185a2f564&method=crc8&datatype=hex&outtype=hex
		// //random number
		crc.update(new byte[] { 0x18, 0x5a, 0x2f, 0x56, 0x04 });
		assertEquals(0x21, crc.getValue());

	}

	@Test
	public void testUpdateInt() {
		CRC8 crc = new CRC8();

		// https://crccalc.com/?crc=090131323334&method=crc8&datatype=hex&outtype=hex
		crc.update(0x090131323334L);
		assertEquals(0xc6, crc.getValue());

		// https://crccalc.com/?crc=465f2a581&method=crc8&datatype=hex&outtype=hex
		// //random number
		crc.update(0x465f2a5801L);
		assertEquals(0xca, crc.getValue());
	}

}
