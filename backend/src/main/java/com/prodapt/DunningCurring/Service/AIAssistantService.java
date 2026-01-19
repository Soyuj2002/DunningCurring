package com.prodapt.DunningCurring.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.prodapt.DunningCurring.DAO.CustomerRepository;
import com.prodapt.DunningCurring.DAO.UserRepository;
import com.prodapt.DunningCurring.Entity.Customer;
import com.prodapt.DunningCurring.Entity.TelecomService;
import com.prodapt.DunningCurring.Entity.User;

@Service
public class AIAssistantService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.model}")
    private String geminiModel;

    public AIAssistantService(UserRepository userRepository,
                              CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public String getResponse(String username, String userMessage) {

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return "Sorry, I couldn't find your user profile.";
        }

        Customer customer = customerRepository.findByUserId(user.getId()).orElse(null);
        if (customer == null) {
            return "Sorry, I couldn't find your customer account.";
        }

        // 1️⃣ Rule-based fast responses (BEST PRACTICE)
        String ruleReply = simpleRuleResponse(userMessage, customer);
        if (ruleReply != null) {
            return ruleReply;
        }

        // 2️⃣ AI fallback (Gemini)
        return callGeminiAI(customer, userMessage);
    }

    private String simpleRuleResponse(String msg, Customer customer) {
        String low = msg.toLowerCase();
        List<TelecomService> services = customer.getServices();

        if (low.contains("bill")) {
            if (services == null || services.isEmpty()) {
                return "You currently have no active services.";
            }
            return "Your current overdue amount is ₹" +
                    services.get(0).getCurrentOverdueAmount();
        }

        if (low.contains("plan")) {
            if (services == null || services.isEmpty()) {
                return "No active plans found.";
            }
            return "You are currently using a " +
                    services.get(0).getServiceType() + " service.";
        }

        if (low.contains("blocked") || low.contains("status")) {
            if (services != null && !services.isEmpty()) {
                return "Your service status is: " +
                        services.get(0).getStatus();
            }
        }

        return null;
    }

    // ✅ Gemini Integration
    private String callGeminiAI(Customer customer, String userMessage) {
        try {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                    + geminiModel + ":generateContent?key=" + geminiApiKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = Map.of(
                "contents", List.of(
                    Map.of(
                        "parts", List.of(
                            Map.of(
                                "text", """
You are a helpful telecom customer support assistant.

Customer name: %s
User question: %s

Answer clearly in simple words.
""".formatted(customer.getName(), userMessage)
                            )
                        )
                    )
                )
            );

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            if (response.getBody() != null) {
                Map firstCandidate =
                        (Map) ((List<?>) response.getBody().get("candidates")).get(0);
                Map content = (Map) firstCandidate.get("content");
                Map part = (Map) ((List<?>) content.get("parts")).get(0);
                return part.get("text").toString();
            }

        } catch (Exception e) {
            System.err.println("Gemini AI Error: " + e.getMessage());
        }

        return "I'm having trouble answering that right now. Please try again later.";
    }
}
