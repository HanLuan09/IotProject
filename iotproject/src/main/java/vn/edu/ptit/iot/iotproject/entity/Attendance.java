package vn.edu.ptit.iot.iotproject.entity;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance")
public class Attendance {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendanceid")
	private Long attendanceId;
    
	@Column(name = "attendancedate")
    private Date attendanceDate;
	
	@Column(name = "attendancetime")
	private Time attendanceTime;
	
	@Column(name = "attendanceimg")
	private String attendanceImg;
	
	@Column(name = "attendancestatus")
    private int attendanceStatus;
	
	@Column(name = "attendancedesc")
    private String attendanceDesc;
	
	@Column(name = "attendanceroom")
    private String attendanceRoom;

	public Long getAttendanceId() {
		return attendanceId;
	}
	@ManyToOne
	@JoinColumn(name = "rfid")
	private Student studentAttendance;
	
	@ManyToOne
	@JoinColumn(name = "scheduleid")
    private Schedule schedule;
	
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Attendance(Date attendanceDate, Time attendanceTime, String attendanceImg,  int attendanceStatus, String attendanceDesc,
			String attendanceRoom, Student studentAttendance, Schedule schedule) {
		super();
		this.attendanceDate = attendanceDate;
		this.attendanceTime = attendanceTime;
		this.attendanceImg = attendanceImg;
		this.attendanceStatus = attendanceStatus;
		this.attendanceDesc = attendanceDesc;
		this.attendanceRoom = attendanceRoom;
		this.studentAttendance = studentAttendance;
		this.schedule = schedule;
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

	public int getAttendanceStatus() {
		return attendanceStatus;
	}

	public Student getStudent() {
		return studentAttendance;
	}

	public String getAttendanceDesc() {
		return attendanceDesc;
	}
	
	public String getAttendanceRoom() {
		return attendanceRoom;
	}
	
}
