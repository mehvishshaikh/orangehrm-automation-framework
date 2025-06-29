package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Log {
    private static final Logger logger = LogManager.getLogger(Log.class);

    static {
        createLogsDirectory();
    }

    private static void createLogsDirectory() {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            boolean created = logDir.mkdirs();
            if (created) {
                System.out.println("'logs' folder created successfully.");
            } else {
                System.out.println("Failed to create 'logs' folder.");
            }
        }
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }
}
