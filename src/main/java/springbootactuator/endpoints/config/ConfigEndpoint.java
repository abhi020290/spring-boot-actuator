package springbootactuator.endpoints.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Endpoint(id="config")
public class ConfigEndpoint {

    @Autowired
    private ConfigurableEnvironment environment;

    @ReadOperation
    ResponseEntity<Map> getConfigValue() {
        HashMap<String, String> configValues = new HashMap<>();
        List<PropertySource<?>> class_path_resource = environment.getPropertySources().stream().
                filter(propertySource -> propertySource.getName().startsWith("class path resource")).
                collect(Collectors.toList());
        final Map<String, String>[] envPropertyValues = new Map[]{new HashMap<>()};
        class_path_resource.forEach(value -> {
            if (value.getName().contains("dev")) {
                envPropertyValues[0] = (Map<String, String>) value.getSource();
            } else {
                Map<String, String> source = (Map<String, String>) value.getSource();
                configValues.putAll(source);
            }
        });
        configValues.putAll(envPropertyValues[0]);
        return ResponseEntity.status(200).body(configValues);
    }
}
