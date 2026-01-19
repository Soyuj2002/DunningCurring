package com.prodapt.DunningCurring.Controller;

import com.prodapt.DunningCurring.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public Map<String, String> login(@RequestBody AuthRequest authRequest) {

		// 1. Authenticate the user credentials
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		// 2. Load User Details
		UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

		// 3. Extract Role (Must be done BEFORE generating token)
		String role = "CUSTOMER"; // Default fallback
		if (!userDetails.getAuthorities().isEmpty()) {
			role = userDetails.getAuthorities().iterator().next().getAuthority();
		}

		// 4. Generate Token (Now passing both username and role)
		String token = jwtUtil.generateToken(userDetails.getUsername(), role);

		// 5. Return JSON Response
		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		response.put("role", role);

		return response;
	}
}

// Simple DTO class for the request
class AuthRequest {
	private String username;
	private String password;
	
	// Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}