package vn.edu.ptit.iot.iotproject.payload.attendance;

import java.sql.Date;
import java.sql.Time;

public class AttendanceSchedule implements Comparable<AttendanceSchedule> {
	private Long scheduleId;
	private Date scheduleDate;
	private Time scheduleTime;
	private String scheduleImg;
	private int status;
	
	public AttendanceSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttendanceSchedule(Long scheduleId, Date scheduleDate, Time scheduleTime, String scheduleImg, int status) {
		super();
		this.scheduleId = scheduleId;
		this.scheduleDate = scheduleDate;
		this.scheduleTime = scheduleTime;
		this.scheduleImg = scheduleImg;
		this.status = status;
	}

	
	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public Time getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(Time scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	
	public String getScheduleImg() {
		return scheduleImg;
	}

	public void setScheduleImg(String scheduleImg) {
		this.scheduleImg = scheduleImg;
	}

	@Override
	public int compareTo(AttendanceSchedule o) {
		// TODO Auto-generated method stub
		return this.getScheduleDate().compareTo(o.getScheduleDate());
	}
}
