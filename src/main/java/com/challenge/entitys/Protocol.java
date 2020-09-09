package com.challenge.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.LoggerFactory;

import com.challenge.TCPServer;
import com.challenge.enums.Frame;

@Entity
@Table()
public class Protocol {

	public static final char REQUEST_END = '\r';
	public static final char REQUEST_INT = '\n';

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private byte bytes;

	private Frame frame;

	private byte[] data;

	private byte crc;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the bytes
	 */
	public byte getBytes() {
		return bytes;
	}

	@Column(name = "bytes")
	public int getBytesInt() {
		return bytes;
	}

	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte bytes) {
		this.bytes = bytes;
	}

	/**
	 * @return the frame
	 */
	@Column(name = "frame")
	public byte getFrame() {
		return frame.getValue();
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(byte frame) {
		this.frame = Frame.valueOf(frame);
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	@Column(name = "data")
	public String getDataString() {
		try {
			return new String(data, "ASCII");
		} catch (Exception e) {
			LoggerFactory.getLogger(TCPServer.class).error(e.getMessage(), e);
			return "";
		}
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the crc
	 */
	public byte getCrc() {
		return crc;
	}

	/**
	 * @param crc the crc to set
	 */
	public void setCrc(byte crc) {
		this.crc = crc;
	}

	@Override
	public String toString() {
		return id + ": " + (int) bytes + " - " + String.format("%x", frame.getValue()) + " - " + getDataString() + " - " + String.format("%x", crc);
	}

}
