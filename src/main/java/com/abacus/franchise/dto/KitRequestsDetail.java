package com.abacus.franchise.dto;

import java.time.LocalDateTime;

public interface KitRequestsDetail {
	  public String getKitRequestId();
	  public  String getRequestStatus();
	  public LocalDateTime getPlacedDate();
	  public LocalDateTime getLocalDateTime();

	  public LocalDateTime getDispatchedDate();
	  public LocalDateTime getDeliveredDate();

	  public String getLine1();
	  public String getLandmark();
	  public String getCity();
	  public String getPincode();
	  public String getCountryName();

	  public String getStateName();
	  public String getDistrictName();

	  public Integer getKitCount();
	  public String getCourseName();
}
