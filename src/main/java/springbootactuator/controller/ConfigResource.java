package springbootactuator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class ConfigResource {

    @Autowired
    private ConfigurableEnvironment environment;

    @GetMapping("/config")
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
