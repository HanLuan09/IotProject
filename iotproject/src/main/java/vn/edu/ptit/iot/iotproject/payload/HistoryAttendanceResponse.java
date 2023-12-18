package vn.edu.ptit.iot.iotproject.payload;

import java.sql.Date;
import java.sql.Time;

public class HistoryAttendanceResponse implements Comparable<HistoryAttendanceResponse> {
	private String attendanceName;
	private String attendanceSubject;
	private String attendanceImg;
	private Date attendanceDate;
	private Time attendanceTime;
	private String attendanceRoom;
	public HistoryAttendanceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HistoryAttendanceResponse(String attendanceName, String attendanceSubject, String attendanceImg, 
			Date attendanceDate, Time attendanceTime, String attendanceRoom) {
		super();
		this.attendanceName = attendanceName;
		this.attendanceSubject = attendanceSubject;
		this.attendanceImg = attendanceImg;
		this.attendanceDate = attendanceDate;
		this.attendanceTime = attendanceTime;
		this.attendanceRoom = attendanceRoom;
	}
	@Override
	public int compareTo(HistoryAttendanceResponse o) {
		if(this.attendanceDate.equals(o.attendanceDate)) {
			return o.attendanceTime.compareTo(this.attendanceTime);
		}
		return o.attendanceDate.compareTo(this.attendanceDate);
	}
	public String getAttendanceName() {
		return attendanceName;
	}
	public String getAttendanceSubject() {
		return attendanceSubject;
	}
	public Date getAttendanceDate() {
		return attendanceDate;
	}
	public Time getAttendanceTime() {
		return attendanceTime;
	}
	public String getAttendanceImg() {
		return attendanceImg;
	}
	public String getAttendanceRoom() {
		return attendanceRoom;
	}
	
}
