package com.ximp.hidrometer.listener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.ximp.hidrometer.model.ApplicationInfo;
import com.ximp.hidrometer.repository.ApplicationInfoRepository;
import com.ximp.hidrometer.service.MeasureService;
import java.util.UUID;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log
public class ApplicationInfoGenerator {

    @Autowired
    private ApplicationInfoRepository infoRepository;

    @PostConstruct
    @Transactional
    public void register() {
        log.fine("Checking AppInfo information");
        if (infoRepository.count() == 0) {
            log.info("New app found. Will generate Application Info");
            ApplicationInfo info = infoRepository.save(ApplicationInfo.builder().build());
            log.info("Application Info generated. UUID: " + info.getId());
        }
    }
}
