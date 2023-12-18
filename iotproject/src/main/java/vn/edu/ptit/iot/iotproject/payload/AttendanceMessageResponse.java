package vn.edu.ptit.iot.iotproject.payload;

import java.io.Serializable;
import java.sql.Date;

public class AttendanceMessageResponse implements Serializable {
	
	private static final long serialVersionUID = -297553281792804396L;
	
	private String code;
	private String title;
	private String message;
	private Date date;
	
	public AttendanceMessageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttendanceMessageResponse(String code, String title, String message, Date date) {
		super();
		this.code = code;
		this.title = title;
		this.message = message;
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}
	
}
