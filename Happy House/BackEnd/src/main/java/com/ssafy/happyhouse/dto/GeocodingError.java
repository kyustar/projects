package com.ssafy.happyhouse.dto;

import java.util.Date;

public class GeocodingError {

	private Integer errorId;
	private Date reportTime;
	private String status;
	private Integer aptId;

	public Integer getErrorId() {
		return errorId;
	}

	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAptId() {
		return aptId;
	}

	public void setAptId(Integer aptId) {
		this.aptId = aptId;
	}

}
