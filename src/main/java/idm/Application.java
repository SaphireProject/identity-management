package idm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Application {

   private static Logger LOGGER = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args){
        LOGGER.info("start application");
        LOGGER.trace("Hello World");
        LOGGER.debug("Hello World");
        LOGGER.info("Hello World");
        LOGGER.warn("Hello World");
        LOGGER.error("Hello World");
        SpringApplication.run(Application.class, args);
    }
}
/*# Root logger option
log4j.rootLogger=INFO, file, stdout
log4j.appender.stdout.filter.a.LevelMin=DEBUG
log4j.appender.stdout.filter.a.LevelMax=INFO

# Direct log messages to stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p:: %m%n

# Direct log messages to a log file
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=E:\\project\\identity-management\\logs.log
log4j.appender.file.MaxFileSize=10000KB
log4j.appender.file.MaxBackupIndex=10
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p:: %m%n


 <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <scope>test</scope>
        </dependency>
*/