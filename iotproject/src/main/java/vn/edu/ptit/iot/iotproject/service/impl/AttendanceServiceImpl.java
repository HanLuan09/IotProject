package vn.edu.ptit.iot.iotproject.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.edu.ptit.iot.iotproject.entity.Attendance;
import vn.edu.ptit.iot.iotproject.entity.Schedule;
import vn.edu.ptit.iot.iotproject.entity.Student;
import vn.edu.ptit.iot.iotproject.payload.HistoryAttendanceResponse;
import vn.edu.ptit.iot.iotproject.payload.HistoryAttendanceRequest;
import vn.edu.ptit.iot.iotproject.repository.AttendanceRepository;
import vn.edu.ptit.iot.iotproject.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Override
    @Transactional
    public void saveAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }
	
	@Override
	public List<HistoryAttendanceResponse> getAttendance(HistoryAttendanceRequest attendanceRequest) throws ParseException {
		
		List<Attendance> attendances = new ArrayList<>();
		if(attendanceRequest.getStartDate() != null && attendanceRequest.getEndDate()!= null) {
			attendances = attendanceRepository.findByAttendanceCodeAndDateRange(
							attendanceRequest.getCode(), 
							attendanceRequest.getSqlStartDate(), 
							attendanceRequest.getSqlEndDate()
						  );
		}else {
			attendances = attendanceRepository.findByAttendanceCode(attendanceRequest.getCode());
		}
		List<HistoryAttendanceResponse> attendanceResponses = new ArrayList<>();
		for(Attendance a : attendances) {
			String[] s = a.getAttendanceDesc().split(";");
			String tmp1 ="", tmp2 ="";
			if(s.length == 2) {
				tmp1 = s[0]; 
				tmp2 = s[1];
			}
			attendanceResponses.add(new HistoryAttendanceResponse(tmp1, tmp2, a.getAttendanceImg(), a.getAttendanceDate(), a.getAttendanceTime(), a.getAttendanceRoom()));
		}
		Collections.sort(attendanceResponses);
		return attendanceResponses;
	}
	@Override
	public Attendance checkAttendanceOfScheduleAndStudent(Schedule schedule, Student student) {
		
		return attendanceRepository.findByScheduleAndStudent(schedule, student);
	}

}
