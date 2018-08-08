package net.smashmc.module.logger;

import lombok.Getter;
import org.apache.log4j.*;
import org.apache.log4j.spi.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LogHandler {

    private final org.apache.log4j.Logger logger;
    @Getter
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") {{
        this.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
    }};

    public LogHandler() {
        logger = Logger.getRootLogger();
        try {
            PatternLayout layout = new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss}] [%p]: %m%n");
            ConsoleAppender consoleAppender = new ConsoleAppender(layout);
            logger.addAppender(consoleAppender);
            Date date = new Date(System.currentTimeMillis());
            DailyRollingFileAppender fileAppender =
                    new DailyRollingFileAppender(layout, "logs/" + getSimpleDateFormat().format(date) + ".log", "'.'yyyy-MM-dd_HH-mm");
            this.logger.addAppender(fileAppender);
            this.logger.setLevel(Level.INFO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void log(String logging) {
        logger.info(logging);
    }
}

