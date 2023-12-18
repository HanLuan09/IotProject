package vn.edu.ptit.iot.iotproject.payload.attendance;

import java.util.List;

public class AttendanceStudent implements Comparable<AttendanceStudent> {
	private String name;
	private String code;
	private String img;
	private String rfid;
	private Long absent;
	private List<AttendanceSchedule> attendanceSchedules;
	
	private String ho,ten,tendem;
	
	public AttendanceStudent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AttendanceStudent(String name, String code, String img, String rfid, Long absent, List<AttendanceSchedule> attendanceSchedules) {
		super();
		this.name = name;
		this.code = code;
		this.img = img;
		this.rfid = rfid;
		this.absent = absent;
		this.attendanceSchedules = attendanceSchedules;
		
		String[] s = this.name.split("\\s");
        this.ho = s[0];
        for (int i = 1; i < s.length-1; i++) {
            this.tendem += s[i] + " ";
        }
        this.ten = s[s.length-1];
	}
	@Override
    public int compareTo(AttendanceStudent b){
        if( this.ten.equals(b.ten)){
            if(this.ho.equals(b.ho)){
                return this.tendem.compareTo(b.tendem);
            }
            return this.ho.compareTo(b.ho);
        }
        return this.ten.compareTo(b.ten);
    }
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public String getRfid() {
		return rfid;
	}
	
	public String getImg() {
		return img;
	}
	
	public Long getAbsent() {
		return absent;
	}
	public List<AttendanceSchedule> getAttendanceSchedules() {
		return attendanceSchedules;
	}
}
