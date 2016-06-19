package poe.reborn.com;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Exslims
 * 19.06.2016
 */
@Configuration
@EnableWebMvc
@ComponentScan("poe.reborn.com")
public class BaseConfig extends WebMvcAutoConfiguration {
}
