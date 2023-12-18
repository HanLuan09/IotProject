package vn.edu.ptit.iot.iotproject.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.iot.iotproject.entity.Schedule;
import vn.edu.ptit.iot.iotproject.repository.ScheduleRepository;
import vn.edu.ptit.iot.iotproject.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Override
	public List<Schedule> getStudentScheduleForToday(String code, Date date) {
		// TODO Auto-generated method stub
		return scheduleRepository.findStudentScheduleForToday(code, date);
	}
}
