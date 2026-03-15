package com.abacus.franchise.viewModels;

import java.util.List;
import java.util.UUID;

import com.abacus.franchise.model.KitOrderItem;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class KitRequest {
	
	private UUID franchiseId;
		
    private List<KitOrderItem> kitOrderItems;

	private UUID addressId;
	
	private String line1;
	
	private String landmark;
	
	private String city;
	
	private UUID districtId;
	
	private UUID stateId;
	
	private String pincode;
}
