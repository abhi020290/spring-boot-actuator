package springbootactuator.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class WelcomeResource {

    @Value("${message.thanks}")
    String messageThanks;
    @Value("${message.welcome}")
    String messageWelcome;

    @GetMapping("/thanks")
    ResponseEntity<String> getThanks() {
        return ResponseEntity.status(200).body(messageThanks);
    }

    @GetMapping("/welcome")
    ResponseEntity<String> getWelcome() {
        return ResponseEntity.status(200).body(messageWelcome);
    }

}
