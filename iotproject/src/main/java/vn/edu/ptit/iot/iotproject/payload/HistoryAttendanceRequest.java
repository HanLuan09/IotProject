package vn.edu.ptit.iot.iotproject.payload;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HistoryAttendanceRequest {
	private String code;
    private String startDate;
    private String endDate;
  
	public HistoryAttendanceRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HistoryAttendanceRequest(String code, String startDate, String endDate) {
		super();
		this.code = code;
		this.startDate = startDate;
		this.endDate = endDate;
		
	}
	public String getCode() {
		return code;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	
	
	public Date getSqlStartDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date utilDate = dateFormat.parse(startDate);
        return new Date(utilDate.getTime());
    }

    public Date getSqlEndDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date utilDate = dateFormat.parse(endDate);
        return new Date(utilDate.getTime());
    }
}
