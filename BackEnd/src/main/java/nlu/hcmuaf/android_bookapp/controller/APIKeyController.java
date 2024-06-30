package nlu.hcmuaf.android_bookapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/key")
public class APIKeyController {

  @GetMapping("get/google/map")
  public ResponseEntity<String> getGoogleMapAPIKey() {
    return ResponseEntity.ok("cc");
  }
}
