package com.prodapt.DunningCurring.Controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.prodapt.DunningCurring.Service.AIAssistantService;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIAssistantController {

    @Autowired
    private AIAssistantService aiService;

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody Map<String, String> request,
                                  Authentication authentication) {

        String userMessage = request.get("message");
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Message is required"));
        }

        if (authentication == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Unauthorized"));
        }

        String username = authentication.getName();
        String reply = aiService.getResponse(username, userMessage);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", reply
        ));
    }

}
