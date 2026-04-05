package com.abacus.franchise.viewModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseRequest {
    private String franchiseId;
    private String mobile;
    private String fromDate;
    private String toDate;
    private int page;
    private int size;
}
