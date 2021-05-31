package com.ssafy.happyhouse.dto;

public class AptInfo {

	private Integer aptId;
	private Long districtCode;
	private String district;
	private String bunji;
	private String bonbun;
	private String bubun;
	private String aptName;
	private Integer buildYear;
	private String roadNameAddress;

	public Integer getAptId() {
		return aptId;
	}

	public void setAptId(Integer aptId) {
		this.aptId = aptId;
	}

	public Long getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(Long districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getBunji() {
		return bunji;
	}

	public void setBunji(String bunji) {
		this.bunji = bunji;
	}

	public String getBonbun() {
		return bonbun;
	}

	public void setBonbun(String bonbun) {
		this.bonbun = bonbun;
	}

	public String getBubun() {
		return bubun;
	}

	public void setBubun(String bubun) {
		this.bubun = bubun;
	}

	public String getAptName() {
		return aptName;
	}

	public void setAptName(String aptName) {
		this.aptName = aptName;
	}

	public Integer getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(Integer buildYear) {
		this.buildYear = buildYear;
	}

	public String getRoadNameAddress() {
		return roadNameAddress;
	}

	public void setRoadNameAddress(String roadNameAddress) {
		this.roadNameAddress = roadNameAddress;
	}

}
