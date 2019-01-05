package com.ximp.hidrometer.dto;

import com.ximp.hidrometer.model.Measure;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasureSearchResultDTO {

    private List<Measure> measures;
    private String applicationId;
    
}
