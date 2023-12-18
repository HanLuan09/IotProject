package vn.edu.ptit.iot.iotproject.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.ptit.iot.iotproject.entity.Schedule;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
	@Query("""
		    SELECT s
		    FROM Schedule s
		    JOIN s.subjectSemester ss
		    JOIN ss.studentClasses stc
		    JOIN stc.account a
		    JOIN s.studyCrew sc
		    WHERE a.code = :code AND s.scheduleDate = :todayDate
		""")
	List<Schedule> findStudentScheduleForToday(@Param("code") String code, @Param("todayDate") Date todayDate);
}
