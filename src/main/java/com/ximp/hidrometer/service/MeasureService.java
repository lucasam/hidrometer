package com.ximp.hidrometer.service;

import com.ximp.hidrometer.model.Measure;
import com.ximp.hidrometer.repository.MeasureRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log
public class MeasureService {

    public static final DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static int lastMeasureCounter;
    public static String lastMeasureTime;

    private final MeasureRepository measureRepository;

    public MeasureService(MeasureRepository measureRepository) {
        this.measureRepository = measureRepository;
    }

    public void measureReceived() {
        log.fine("Measure received");
        saveAndIncrement();
    }

    /**
     * Rate in l/hour
     */
    public long currentRate() {
        saveIfNeeded();
        String currentMin = LocalDateTime.now().format(minuteFormatter);
        if (LocalDateTime.now().getSecond() == 0 || !currentMin.equals(lastMeasureTime)) {
            return 0;
        }
        return lastMeasureCounter * TimeUnit.HOURS.toSeconds(1) / LocalDateTime.now().getSecond();
    }

    private synchronized void saveAndIncrement() {
        saveIfNeeded();
        lastMeasureCounter++;
        lastMeasureTime = LocalDateTime.now().format(minuteFormatter);
    }

    public synchronized void saveIfNeeded() {
        String now = LocalDateTime.now().format(minuteFormatter);
        if (lastMeasureTime == null) {
            return;
        }

        if (!lastMeasureTime.equals(now) && lastMeasureCounter > 0) {
            Measure measure = Measure.builder().pointInTime(
                    LocalDateTime.parse(lastMeasureTime, minuteFormatter))
                    .value(lastMeasureCounter).build();
            //saveMeasure
            measureRepository.save(measure);
            lastMeasureTime = now;
            lastMeasureCounter = 0;
        }
    }
}
