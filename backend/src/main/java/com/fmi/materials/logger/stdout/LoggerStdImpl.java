package com.fmi.materials.logger.stdout;

import com.fmi.materials.config.AppConfig;
import com.fmi.materials.logger.Logger;
import com.fmi.materials.vo.LoggerLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Profile("local")
@Component("stdLogger")
public class LoggerStdImpl implements Logger {

    @Autowired
    private AppConfig appConfig;

    private void log(LoggerLevel logLevel, Object toLog) {
        String level = appConfig.getLogger().getLevel();

        if(LoggerLevel.valueOf(level).getCode() >= logLevel.getCode()) {
            System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS) + " [" + logLevel.name() + "] " + toLog);
        }
    }

    @Override
    public void info(Object toLog) {
        this.log(LoggerLevel.INFO, toLog);
    }

    @Override
    public void debug(Object toLog) {
        this.log(LoggerLevel.DEBUG, toLog);
    }

    @Override
    public void trace(Object toLog) {
        this.log(LoggerLevel.TRACE, toLog);
    }

    @Override
    public void error(Object toLog) {
        this.log(LoggerLevel.ERROR, toLog);
    }
}
