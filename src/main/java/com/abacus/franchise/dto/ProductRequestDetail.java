package com.abacus.franchise.dto;

import java.time.LocalDateTime;

public interface ProductRequestDetail {
	 public int getQuantity();
	 public LocalDateTime  getRequestDate();
	 public String getRequestStatus();
	 public String  getProductName();
}
