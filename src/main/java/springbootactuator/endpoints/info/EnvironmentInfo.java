package springbootactuator.endpoints.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("environmentInfo")
public class EnvironmentInfo implements InfoContributor {
    private static final Bindable<Map<String, Object>> STRING_OBJECT_MAP = Bindable.mapOf(String.class, Object.class);
    private final ConfigurableEnvironment environment;

    public EnvironmentInfo(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    public void contribute(Info.Builder builder) {
        List<PropertySource<?>> class_path_resource = environment.getPropertySources().stream().filter(propertySource -> propertySource.getName().startsWith("class path resource")).collect(Collectors.toList());
        class_path_resource.forEach( value -> {
            Map<String,String> source = (Map<String, String>) value.getSource();
            System.out.println(source);
        });
        Binder binder = Binder.get(this.environment);
        binder.bind("info", STRING_OBJECT_MAP).ifBound(builder::withDetails);
    }
}
