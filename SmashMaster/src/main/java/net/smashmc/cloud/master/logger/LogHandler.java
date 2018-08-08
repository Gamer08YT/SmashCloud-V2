package net.smashmc.cloud.master.logger;


import lombok.Getter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Getter
public class LogHandler {

    private Logger logger = Logger.getLogger(LogHandler.class.getName());
    FileHandler fileHandler;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") {{
        this.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
    }};

    public void initialize() {
        try {
            Date date = new Date(System.currentTimeMillis());
            fileHandler = new FileHandler(System.getProperty("user.dir") + "/logs/" + this.getSimpleDateFormat().format(date) + ".log", true);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String loggingmessage) {
        logger.log(Level.INFO, loggingmessage);
    }
}
