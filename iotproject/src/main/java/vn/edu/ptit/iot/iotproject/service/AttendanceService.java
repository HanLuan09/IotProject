package vn.edu.ptit.iot.iotproject.service;

import java.text.ParseException;
import java.util.List;


import vn.edu.ptit.iot.iotproject.entity.Attendance;
import vn.edu.ptit.iot.iotproject.entity.Schedule;
import vn.edu.ptit.iot.iotproject.entity.Student;
import vn.edu.ptit.iot.iotproject.payload.HistoryAttendanceResponse;
import vn.edu.ptit.iot.iotproject.payload.HistoryAttendanceRequest;

public interface AttendanceService {
	
	public void saveAttendance(Attendance attendance);
	
	public List<HistoryAttendanceResponse> getAttendance(HistoryAttendanceRequest attendanceRequest) throws ParseException;
	
	public Attendance checkAttendanceOfScheduleAndStudent(Schedule schedule, Student student);
}
