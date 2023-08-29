package com.example.security.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello fromAPi");
    }

    @GetMapping("/saygoodbye")
    public ResponseEntity<String> sayGodBye(){
        return ResponseEntity.ok("By APi");
    }
}
