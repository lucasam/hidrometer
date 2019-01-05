package com.ximp.hidrometer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasureSearchDTO {

    private String applicationId;
    private Integer lastMeasureId;
    
}
