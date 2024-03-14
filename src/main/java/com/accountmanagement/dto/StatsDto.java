package com.accountmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StatsDto {

    String[] accountCategory;

    float[] accountValue;

    String[] costCategory;

    float[] costValue;

}
