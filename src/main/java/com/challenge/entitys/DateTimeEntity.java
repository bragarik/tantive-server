package com.challenge.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <table>
 * <thead>
 * <tr>
 * <th>DIA</th>
 * <th>MÊS</th>
 * <th>ANO</th>
 * <th>HORA</th>
 * <th>MINUTO</th>
 * <th>SEGUNDO</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>1 byte</td>
 * <td>1 byte</td>
 * <td>1 byte</td>
 * <td>1 byte</td>
 * <td>1 byte</td>
 * <td>1 byte</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @author Ricardo Braga
 *
 */
@Entity
@Table()
public class DateTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "day")
	private byte day;

	@Column(name = "month")
	private byte month;

	@Column(name = "year")
	private byte year;

	@Column(name = "hour")
	private byte hour;

	@Column(name = "minute")
	private byte minute;

	@Column(name = "second")
	private byte second;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getDay() {
		return day;
	}

	public void setDay(byte day) {
		this.day = day;
	}

	public byte getMonth() {
		return month;
	}

	public void setMonth(byte month) {
		this.month = month;
	}

	public byte getYear() {
		return year;
	}

	public void setYear(byte year) {
		this.year = year;
	}

	public byte getHour() {
		return hour;
	}

	public void setHour(byte hour) {
		this.hour = hour;
	}

	public byte getMinute() {
		return minute;
	}

	public void setMinute(byte minute) {
		this.minute = minute;
	}

	public byte getSecond() {
		return second;
	}

	public void setSecond(byte second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return id + ": " + getId();
	}

}
