package com.abacus.franchise.dto;

import java.time.LocalDateTime;
import java.util.List;

public class KitRequestAddressDTO {

	private String kitRequestId;
    private LocalDateTime placedDate;
    private LocalDateTime dispatchedDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime localDateTime;

    private String line1;
    private String landmark;
    private String city;
    private String districtName;
    private String stateName;
    private String pincode;
    private String countryName;

    private String requestStatus;

    private List<CourseKitDTO> courses;
    
    public String getKitRequestId() {
		return kitRequestId;
	}

	public void setKitRequestId(String kitRequestId) {
		this.kitRequestId = kitRequestId;
	}

	public LocalDateTime getPlacedDate() {
		return placedDate;
	}

	public void setPlacedDate(LocalDateTime placedDate) {
		this.placedDate = placedDate;
	}

	public LocalDateTime getDispatchedDate() {
		return dispatchedDate;
	}

	public void setDispatchedDate(LocalDateTime dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}

	public LocalDateTime getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(LocalDateTime deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public List<CourseKitDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseKitDTO> courses) {
		this.courses = courses;
	}
    
    
}
