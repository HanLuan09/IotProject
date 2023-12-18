package vn.edu.ptit.iot.iotproject.payload;

import java.util.List;

import vn.edu.ptit.iot.iotproject.payload.attendance.AttendanceStudent;

public class AttendanceResponse {
	private String subject;
	private List<AttendanceStudent> attendanceStudents;
	public AttendanceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AttendanceResponse(String subject, List<AttendanceStudent> attendanceStudents) {
		super();
		this.subject = subject;
		this.attendanceStudents = attendanceStudents;
	}
	public String getSubject() {
		return subject;
	}
	public List<AttendanceStudent> getAttendanceStudents() {
		return attendanceStudents;
	}
}
