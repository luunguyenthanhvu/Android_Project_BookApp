package nlu.hcmuaf.android_bookapp.controller;

import nlu.hcmuaf.android_bookapp.dto.response.APIKeyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/key")
public class APIKeyController {

  @Value("${google.maps.api.key}")
  private String googleMapsApiKey;

  @GetMapping("get/google-map")
  public ResponseEntity<APIKeyResponse> getGoogleMapAPIKey() {
    return ResponseEntity.ok(APIKeyResponse.builder().apiKey(googleMapsApiKey).build());
  }
}
