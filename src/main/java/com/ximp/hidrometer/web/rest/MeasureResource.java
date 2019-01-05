package com.ximp.hidrometer.web.rest;

import com.ximp.hidrometer.dto.MeasureDTO;
import com.ximp.hidrometer.dto.MeasureSearchDTO;
import com.ximp.hidrometer.dto.MeasureSearchParamsDTO;
import com.ximp.hidrometer.dto.MeasureSearchResultDTO;
import com.ximp.hidrometer.model.ApplicationInfo;
import com.ximp.hidrometer.model.Measure;
import com.ximp.hidrometer.repository.ApplicationInfoRepository;
import com.ximp.hidrometer.repository.MeasureRepository;
import com.ximp.hidrometer.service.MeasureService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/measure")
public class MeasureResource {

    @Autowired
    MeasureService service;

    @Autowired
    MeasureRepository measureRepository;

    @Autowired
    ApplicationInfoRepository applicationInfoRepository;

    @RequestMapping("received")
    public void measureReceived() {
        service.measureReceived();
    }

    @RequestMapping("find")
    public MeasureSearchResultDTO findAll() {
        return findAllSince(null);
    }

    @PostMapping("find")
    public MeasureSearchResultDTO findAllSince(MeasureSearchParamsDTO searchParamsDTO) {
        service.saveIfNeeded();

        ApplicationInfo info = applicationInfoRepository.findAll().get(0);
        if (searchParamsDTO != null && searchParamsDTO.getLastPulledData() != null) {
            for (MeasureSearchDTO searchDTO : searchParamsDTO.getLastPulledData()) {
                if (searchDTO.getApplicationId().equals(info.getId())) {
                    return MeasureSearchResultDTO.builder().applicationId(info.getId())
                            .measures(measureRepository.findAllByIdGreaterThan(searchDTO.getLastMeasureId())).build();
                }
            }
        }
        return MeasureSearchResultDTO.builder().applicationId(info.getId()).measures(measureRepository.findAll())
                .build();
    }

    @RequestMapping("currentRate")
    public long currentRate() {
        return service.currentRate();
    }
}
