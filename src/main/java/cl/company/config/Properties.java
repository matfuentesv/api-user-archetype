package cl.company.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "my.app")
public class Properties {

    private final Map<String, String> myProperties = new HashMap<>();

    public Map<String, String> getProperties() {
        return myProperties;
    }

}
