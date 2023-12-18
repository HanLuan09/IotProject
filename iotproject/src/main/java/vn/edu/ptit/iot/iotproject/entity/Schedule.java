package vn.edu.ptit.iot.iotproject.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {
	
	private static final long serialVersionUID = -297553281792804396L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduleid")
	private Long scheduleId;
	
	@Column(name = "scheduledate")
	private Date scheduleDate;
	
	@ManyToOne
    @JoinColumn(name = "studycrewid")
    private StudyCrew studyCrew;
	
	@ManyToOne
    @JoinColumn(name = "subjectsemesterid")
    private SubjectSemester subjectSemester;
	
	@OneToMany(mappedBy = "schedule")
	private Collection<Attendance> attendances;
	
	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getScheduleId() {
		return scheduleId;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public StudyCrew getStudyCrew() {
		return studyCrew;
	}

	public Collection<Attendance> getAttendances() {
		return attendances;
	}
	
}
