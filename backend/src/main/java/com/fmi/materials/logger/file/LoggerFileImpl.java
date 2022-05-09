package com.fmi.materials.logger.file;

import com.fmi.materials.config.AppConfig;
import com.fmi.materials.logger.Logger;
import com.fmi.materials.vo.LoggerLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Profile("dev")
@Component("fileLogger")
public class LoggerFileImpl implements Logger {

    @Autowired
    private AppConfig appConfig;

    private void log(LoggerLevel logLevel, Object toLog) {
        String level = appConfig.getLogger().getLevel();

        if(LoggerLevel.valueOf(level).getCode() >= logLevel.getCode()) {
            File log = new File("log.txt");
            try (PrintWriter out = new PrintWriter(new FileWriter(log, true))) {
                out.println(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS) + " [" + logLevel.name() + "] " + toLog);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
