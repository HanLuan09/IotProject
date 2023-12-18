package vn.edu.ptit.iot.iotproject.entity;

import java.sql.Time;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "studycrew")
public class StudyCrew {
	
	@Id
    @Column(name = "studycrewid")
	private String studyCrewId;
	
	@Column(name = "starttime")
    private Time startTime;
	
	@Column(name = "endtime")
    private Time endTime;
    
    @OneToMany(mappedBy = "studyCrew")
    private Collection<Schedule> schedules;
	public StudyCrew() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StudyCrew(String studyCrewId, Time startTime, Time endTime) {
		super();
		this.studyCrewId = studyCrewId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getStudyCrewId() {
		return studyCrewId;
	}
	public Time getStartTime() {
		return startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
    
}
