package com.ximp.hidrometer.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasureSearchParamsDTO {

    private List<MeasureSearchDTO> lastPulledData;

}
