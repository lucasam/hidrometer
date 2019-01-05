package com.ximp.hidrometer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import com.ximp.hidrometer.HidrometerApplication;
import com.ximp.hidrometer.model.Measure;
import com.ximp.hidrometer.repository.MeasureRepository;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HidrometerApplication.class)
@Transactional
public class MeasureSeviceTest {

    @Autowired
    private MeasureService service;

    @Autowired
    private MeasureRepository repository;

    @Test
    public void givenMeasureCrossMinuteShouldSave() throws Exception {
        LocalTime time = LocalTime.now();
        int secondsLeftToRolloutMinute = 59 - time.getSecond();
        if (secondsLeftToRolloutMinute < 1) {
            //If is left less than 1 sec to rollout the minute lets wait
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        }

        //Act
        service.measureReceived();
        service.measureReceived();
        service.measureReceived();

        Thread.sleep(TimeUnit.SECONDS.toMillis(60 - LocalTime.now().getSecond()));

        service.measureReceived();

        List<Measure> measures = repository.findAll(Sort.by(Order.desc("pointInTime")));

        assertThat(measures.size()).isGreaterThan(0);
        assertThat(measures.get(0).getValue()).isEqualTo(3);
    }
}
