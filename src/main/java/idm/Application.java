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