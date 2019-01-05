package com.ximp.hidrometer.listener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.ximp.hidrometer.service.MeasureService;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log
public class HidrometerListener implements GpioPinListenerDigital {

    @Autowired
    private MeasureService service;

    @PostConstruct
    public void register() {
        log.info("Registering Gpio Listener");
        try {
            /// create gpio controller
            final GpioController gpio = GpioFactory.getInstance();

            // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
            final GpioPinDigitalInput myButton = gpio
                    .provisionDigitalInputPin(RaspiPin.GPIO_17, PinPullResistance.PULL_DOWN);

            // set shutdown state for this input pin
            myButton.setShutdownOptions(true);
            // create and register gpio pin listener
            myButton.addListener(this);
        } catch (Error e) {
            log.log(Level.WARNING, "Error registering GPIO Listener", e);
        }
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        log.info(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        if (PinState.HIGH.equals(event.getState())) {
            service.measureReceived();
        }
    }
}
