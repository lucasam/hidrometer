package com.ximp.hidrometer.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MeasureDTO {
    private String applicationId;
    private Integer id;
    private LocalDateTime pointInTime;
    private Integer value;
}
