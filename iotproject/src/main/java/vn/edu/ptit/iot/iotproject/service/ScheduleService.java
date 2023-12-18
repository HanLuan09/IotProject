package vn.edu.ptit.iot.iotproject.service;

import java.sql.Date;
import java.util.List;

import vn.edu.ptit.iot.iotproject.entity.Schedule;

public interface ScheduleService {
	
	public List<Schedule> getStudentScheduleForToday(String code, Date date);
	
}
