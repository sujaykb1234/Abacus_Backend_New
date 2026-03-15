package com.abacus.franchise.viewModels;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewProductRequest {

	private UUID productId;
	
	private int quantity;

}
