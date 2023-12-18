package vn.edu.ptit.iot.iotproject.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.ptit.iot.iotproject.entity.Attendance;
import vn.edu.ptit.iot.iotproject.entity.Schedule;
import vn.edu.ptit.iot.iotproject.entity.Student;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	
	@Query("SELECT att FROM Attendance att JOIN att.studentAttendance sa JOIN sa.account a WHERE a.code = :code")
	List<Attendance> findByAttendanceCode(@Param("code") String code);
	
	@Query("SELECT att FROM Attendance att JOIN att.studentAttendance sa JOIN sa.account a WHERE a.code = :code AND att.attendanceDate BETWEEN :startDate AND :endDate")
	List<Attendance> findByAttendanceCodeAndDateRange(@Param("code") String code, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("SELECT att FROM Attendance att WHERE att.schedule = :schedule AND att.studentAttendance = :student")
	Attendance findByScheduleAndStudent(@Param("schedule") Schedule schedule, @Param("student") Student student);

}
