package com.accountmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistogramDto {
    String[] dates;
    float[] floatAccountBuy;
    float[] floatAccountSell;
    float[] floatCostSalary;
    float[] floatCostMarketing;
    float[] floatCostOther;
}
