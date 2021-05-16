package springbootactuator.endpoints.heartbeat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Endpoint(id="heartbeat")
class HeartBeatResource {

    @Value("${application.name}")
    private String projectName;

    @Value("${build.version}")
    private String projectVersion;

    //@RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    @ReadOperation
    ResponseEntity<Map> getHeartBeat() {
        Map<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(projectName) && !StringUtils.isEmpty(projectVersion)) {
            map.put("artifactId", projectName);
            map.put("version", projectVersion);
            map.put("status", "UP");
            return ResponseEntity.status(200).body(map);
        } else {
            map.put("error", "ProjectName and Version not found");
            return ResponseEntity.status(400).body(map);
        }
    }

}
